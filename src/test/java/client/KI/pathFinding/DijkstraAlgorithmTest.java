package client.KI.pathFinding;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import client.KI.KIEnumeration.EClientState;
import client.KI.pathFinding.support.PlayerFinder;
import client.game.ClientGameState;
import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientFortState;
import client.mapData.mapEnumeration.EClientPositionState;
import client.mapData.mapEnumeration.EClientTerrain;
import client.mapData.mapEnumeration.EClientTreasureState;
import client.mapData.mapGeneration.MapGeneratorHelperForTests;

class DijkstraAlgorithmTest {
	private static ClientGameState generateClientGameState() {
		return new ClientGameState("", MapGeneratorHelperForTests.generateFullMap(), false, EClientState.MUSTACT);
	}

	@ParameterizedTest
	@MethodSource("provideDataForGetCheapestPath")
	public void GivenMapStartAndTargetCoordinateCheckIfFoundPathIsTheCheapest_GetCheapestPath_ExpectedCheapestPathMatch(
			final Map<Coordinate, ClientTile> map, final Coordinate start, final Coordinate target, final List<Coordinate> expected) {
		// arrange

		// act
		final List<Coordinate> cheapestPath = DijkstraAlgorithm.findPath(map, start, target);
		// assert
		assertThat(cheapestPath, is(equalTo(expected)));
		Assertions.assertEquals(expected, cheapestPath);
	}

	@MethodSource("provideDataForGetCheapestPath")
	public static Stream<Arguments> provideDataForGetCheapestPath() {
		final ClientGameState clientGameState = generateClientGameState();
		final ClientGameState secondClientGameState = generateClientGameState();
		final ClientMap clientMap = clientGameState.getMap();
		final Map<Coordinate, ClientTile> map = clientMap.getMap();
		final Map<Coordinate, ClientTile> secondMap = secondClientGameState.getMap().getMap();

		map.replace(new Coordinate(1, 0),
				new ClientTile(EClientTerrain.GRASS, EClientTreasureState.NoOrUnknownTreasureState,
						EClientPositionState.MyPlayerPosition, EClientFortState.MyFortPresent));
		map.replace(new Coordinate(9, 4), new ClientTile(EClientTerrain.GRASS, EClientTreasureState.MyTreasureIsPresent,
				EClientPositionState.NoPlayerPresent, EClientFortState.NoOrUnknownFortState));

		secondMap.replace(new Coordinate(3, 0),
				new ClientTile(EClientTerrain.GRASS, EClientTreasureState.NoOrUnknownTreasureState,
						EClientPositionState.MyPlayerPosition, EClientFortState.MyFortPresent));
		secondMap.replace(new Coordinate(2, 3),
				new ClientTile(EClientTerrain.GRASS, EClientTreasureState.MyTreasureIsPresent,
						EClientPositionState.NoPlayerPresent, EClientFortState.NoOrUnknownFortState));

		final Optional<Coordinate> start = PlayerFinder.findPlayerPosition(map);
		final Optional<Coordinate> secondStart = PlayerFinder.findPlayerPosition(secondMap);

		final Coordinate target = new Coordinate(9, 4);
		final Coordinate secondTarget = new Coordinate(2, 3);

		final List<Coordinate> cheapestPath = generateCheapestPath(clientGameState.getMap());
		final List<Coordinate> secondCheapestPath = generateSecondCheapestPath(secondClientGameState.getMap());
		
		return Stream.of(Arguments.of(map, start.get(), target, cheapestPath),
				Arguments.of(secondMap, secondStart.get(), secondTarget, secondCheapestPath));
	}

	private static List<Coordinate> generateCheapestPath(final ClientMap fullMap) {
		final List<Coordinate> cheapestPath = new ArrayList<>();
		
		cheapestPath.add(new Coordinate(1, 0));
		cheapestPath.add(new Coordinate(1, 1));
		cheapestPath.add(new Coordinate(1, 2));
		cheapestPath.add(new Coordinate(2, 2));
		cheapestPath.add(new Coordinate(3, 2));
		cheapestPath.add(new Coordinate(4, 2));
		cheapestPath.add(new Coordinate(5, 2));
		cheapestPath.add(new Coordinate(6, 2));
		cheapestPath.add(new Coordinate(7, 2));
		cheapestPath.add(new Coordinate(8, 2));
		cheapestPath.add(new Coordinate(9, 2));
		cheapestPath.add(new Coordinate(9, 3));
		cheapestPath.add(new Coordinate(9, 4));

		return cheapestPath;
	}

	private static List<Coordinate> generateSecondCheapestPath(final ClientMap secondFullMap) {
		final List<Coordinate> cheapestPath = new ArrayList<>();
		
		cheapestPath.add(new Coordinate(3, 0));
		cheapestPath.add(new Coordinate(3, 1));
		cheapestPath.add(new Coordinate(3, 2));
		cheapestPath.add(new Coordinate(2, 2));
		cheapestPath.add(new Coordinate(2, 3));

		return cheapestPath;
	}
}
