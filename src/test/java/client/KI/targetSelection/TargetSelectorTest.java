package client.KI.targetSelection;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import client.KI.KIEnumeration.EClientState;
import client.game.ClientGameState;
import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientFortState;
import client.mapData.mapEnumeration.EClientTerrain;
import client.mapData.mapEnumeration.EClientTreasureState;
import client.mapData.mapGeneration.MapGeneratorHelperForTests;

public class TargetSelectorTest {
	private static ClientGameState generateClientGameState() {
		return new ClientGameState("", MapGeneratorHelperForTests.generateFullMap(), false, EClientState.MUSTACT);
	}

	private static Coordinate getFreeGrassTile(final ClientGameState clientGameState) {
		final ClientMap map = clientGameState.getMap();
		final Map<Coordinate, ClientTile> mapTiles = map.getMap();
		final Optional<Coordinate> grassTile = mapTiles.entrySet().stream().filter(eachEntry -> {
			final ClientTile eachTile = eachEntry.getValue();
			return eachTile.getTerrain().equals(EClientTerrain.GRASS)
					&& eachTile.getClientTreasureState().equals(EClientTreasureState.NoOrUnknownTreasureState)
					&& eachTile.getFortState().equals(EClientFortState.NoOrUnknownFortState);
		}).findFirst().map(Map.Entry::getKey);
		assertThat("Could not find free grass tile", grassTile.isPresent());
		return grassTile.get();
	}

	private static Coordinate placeTreasure(final ClientGameState clientGameState) {
		final ClientMap map = clientGameState.getMap();
		final Map<Coordinate, ClientTile> mapTiles = map.getMap();
		final Coordinate treasureCoordinate = getFreeGrassTile(clientGameState);
		final ClientTile oldTreasureTile = mapTiles.get(treasureCoordinate);
		final EClientTreasureState treasureState = EClientTreasureState.MyTreasureIsPresent;
		mapTiles.replace(treasureCoordinate, new ClientTile(oldTreasureTile.getTerrain(), treasureState,
				oldTreasureTile.getClientPositionState(), oldTreasureTile.getFortState()));
		return treasureCoordinate;
	}

	private static Coordinate placeEnemyFort(final ClientGameState clientGameState) {
		final ClientMap map = clientGameState.getMap();
		final Map<Coordinate, ClientTile> mapTiles = map.getMap();
		final Coordinate fortCoordinate = new Coordinate(9, 9);
		final ClientTile oldFortTile = mapTiles.get(fortCoordinate);
		final EClientFortState fortState = EClientFortState.EnemyFortPresent;
		mapTiles.replace(fortCoordinate, new ClientTile(oldFortTile.getTerrain(), oldFortTile.getClientTreasureState(),
				oldFortTile.getClientPositionState(), fortState));
		return fortCoordinate;
	}

	private static Coordinate placePlayerFort(final ClientGameState clientGameState) {
		final ClientMap map = clientGameState.getMap();
		final Map<Coordinate, ClientTile> mapTiles = map.getMap();
		final Coordinate fortCoordinate = new Coordinate(0, 0);
		final ClientTile oldFortTile = mapTiles.get(fortCoordinate);
		final EClientFortState fortState = EClientFortState.MyFortPresent;
		mapTiles.replace(fortCoordinate, new ClientTile(oldFortTile.getTerrain(), oldFortTile.getClientTreasureState(),
				oldFortTile.getClientPositionState(), fortState));
		return fortCoordinate;
	}

	@Test
	public void SelectTargetOnFullyCoveredMap_ExpectedNone() {
		// Create a map where all tiles have been uncovered
		final ClientGameState clientGameState = generateClientGameState();
		final ClientMap map = clientGameState.getMap();
		final Map<Coordinate, ClientTile> mapTiles = map.getMap();
		placePlayerFort(clientGameState);
		mapTiles.forEach((eachCoordinate, eachTile) -> eachTile.uncoverTile());

		final Optional<TargetInformation> target = TargetSelector.findTarget(clientGameState);
		assertThat("Should not be able to select target", target.isEmpty());
	}

	@Test
	public void SelectTreasureIfTreasureIsVisible_EnemyCastleCovered() {
		final ClientGameState clientGameState = generateClientGameState();
		final Coordinate treasureCoordinate = placeTreasure(clientGameState);

		// Make sure the target selector returns us the treasure
		final Optional<TargetInformation> target = TargetSelector.findTarget(clientGameState);
		assertThat("TargetSelector should return a coordinate", target.isPresent());
		Assert.assertEquals("Selected target should be treasure coordinate", treasureCoordinate, target.get().getTargetCoordinate());
	}

	@Test
	public void SelectTreasureIfTreasureIsVisible_EnemyCastleUncovered() {
		final ClientGameState clientGameState = generateClientGameState();
		final Coordinate treasureCoordinate = placeTreasure(clientGameState);
		final Coordinate fortCoordinate = placeEnemyFort(clientGameState);
		Assert.assertNotEquals("Fort coordinate should not be treasure coordinate", treasureCoordinate, fortCoordinate);

		// Make sure the target selector returns us the treasure
		final Optional<TargetInformation> target = TargetSelector.findTarget(clientGameState);
		assertThat("TargetSelector should return a coordinate", target.isPresent());
		Assert.assertEquals("Selected target should be treasure coordinate", treasureCoordinate, target.get().getTargetCoordinate());
	}

	@Test
	public void SelectCurrentPlayerHalfIfTreasureHasNotBeenPickedUp() {
		final ClientGameState clientGameState = generateClientGameState();

		// Place the player fort to determine the map split
		// With our pre-generated map, the fort is placed at (0,0)
		final Coordinate fortCoordinate = placePlayerFort(clientGameState);

		// Make sure the target selector returns us a coordinate in our map half
		final Optional<TargetInformation> oTarget = TargetSelector.findTarget(clientGameState);
		assertThat("TargetSelector should return a coordinate", oTarget.isPresent());
		final Coordinate target = oTarget.get().getTargetCoordinate();
		Assert.assertNotEquals("Selected target should not fort coordinate", fortCoordinate, target);

		Assert.assertTrue("Target is not in our map half", target.getY() < 5);
	}

	@Test
	public void SelectEnemyPlayerHalfIfTreasureHasBeenPickedUp() {
		ClientGameState clientGameState = generateClientGameState();
		clientGameState = clientGameState
				.update(new ClientGameState("", clientGameState.getMap(), true, clientGameState.getClientState()));

		// Place the player fort to determine the map split
		placePlayerFort(clientGameState);

		// Make sure the target selector returns us a coordinate in our map half
		final Optional<TargetInformation> oTarget = TargetSelector.findTarget(clientGameState);
		assertThat("TargetSelector should return a coordinate", oTarget.isPresent());
		final Coordinate target = oTarget.get().getTargetCoordinate();
		Assert.assertTrue("Target is not in enemy map half", target.getY() >= 5);
	}

	@Test
	public void SelectEnemyCastleIfTreasureHasBeenPickedUp() {
		ClientGameState clientGameState = generateClientGameState();
		clientGameState = clientGameState
				.update(new ClientGameState("", clientGameState.getMap(), true, clientGameState.getClientState()));
		final Coordinate fortCoordinate = placeEnemyFort(clientGameState);

		// Make sure the target selector returns us the fort
		final Optional<TargetInformation> target = TargetSelector.findTarget(clientGameState);
		assertThat("TargetSelector should return a coordinate", target.isPresent());
		Assert.assertEquals("Selected target should be fort coordinate", fortCoordinate, target.get().getTargetCoordinate());
	}

	@Test
	public void DoNotSelectEnemyCastleIfTreasureHasNotBeenPickedUp() {
		final ClientGameState clientGameState = generateClientGameState();
		placePlayerFort(clientGameState);
		final Coordinate fortCoordinate = placeEnemyFort(clientGameState);

		// Make sure the target selector returns us the fort
		final Optional<TargetInformation> target = TargetSelector.findTarget(clientGameState);
		assertThat("TargetSelector should return a coordinate", target.isPresent());
		Assert.assertNotEquals("Selected target should not be fort coordinate", fortCoordinate, target.get());
	}

	@Test
	public void SelectValidUncoveredTargetTile_NoTreasureNoCastle() {
		final ClientGameState clientGameState = generateClientGameState();
		final ClientMap map = clientGameState.getMap();
		placePlayerFort(clientGameState);
		final Map<Coordinate, ClientTile> mapTiles = map.getMap();

		final Optional<TargetInformation> oTarget = TargetSelector.findTarget(clientGameState);
		assertThat("TargetSelector was not able to select a target", oTarget.isPresent());
		final Coordinate target = oTarget.get().getTargetCoordinate();
		assertThat("Selected target is already uncovered", mapTiles.get(target).isCovered());
	}
}
