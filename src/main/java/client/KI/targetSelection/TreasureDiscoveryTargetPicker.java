package client.KI.targetSelection;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.KI.pathFinding.support.MapCutter;
import client.KI.pathFinding.support.PlayerFinder;
import client.KI.pathFinding.support.WaterFilter;
import client.game.ClientGameState;
import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;

/**
 * Implementation of the {@link ITargetSelectionPicker} interface that finds the
 * next tile to discover when searching for treasure.
 * 
 * @author
 */
public class TreasureDiscoveryTargetPicker implements ITargetSelectionPicker {
	private final Logger logger = LoggerFactory.getLogger(TreasureDiscoveryTargetPicker.class);
	@Override
	/**
	 * Searches for the player's own treasure on the given game state map, if the
	 * treasure has not been collected yet.
	 * @param clientGameState The current game state of the client
	 * @return TargetInformation (coordinate, isPriorityTarget) of the next target.
	 */
	public Optional<TargetInformation> findTarget(final ClientGameState clientGameState) {
	    // Only try to do treasure discovery if we have not collected the treasure yet
	    if (clientGameState.isHasCollectedTreasure()) {
	        return Optional.empty();
	    }
	    
	    logger.info("Search for treasure has started...");
	    
	    final boolean hasTreasureCollected = false;
	    final int isPriorityTarget = 1; // In case the treasure is found we need to move directly to the treasure
	    final ClientMap map = MapCutter.divideMap(clientGameState.getMap(), hasTreasureCollected);

	    final Map<Coordinate, ClientTile> cuttedMap = map.getMap();
	    final Map<Coordinate, ClientTile> discoveryTiles = WaterFilter.getMapWithNoWaterTiles(cuttedMap);

	    Stream<Coordinate> candidates = discoveryTiles.entrySet().stream()
                .filter(eachTile -> (cuttedMap.get(eachTile.getKey()).getTerrain() == EClientTerrain.GRASS)
                        && eachTile.getValue().isCovered())
                .map(Map.Entry::getKey);

        final Optional<Coordinate> playerPosition = PlayerFinder.findPlayerPosition(map.getMap());
        if (playerPosition.isPresent()) {
            final Coordinate playerCoordinate = playerPosition.get();
            candidates = candidates.sorted((coordinate1, coordinate2) -> {
                final int dist1 = Math.abs(coordinate1.getX() - playerCoordinate.getX()) +
                        Math.abs(coordinate1.getY() - playerCoordinate.getY());
                final int dist2 = Math.abs(coordinate2.getX() - playerCoordinate.getX()) +
                        Math.abs(coordinate2.getY() - playerCoordinate.getY());
                return Integer.compare(dist1, dist2);
            });
        }

        return candidates.map(coordinate -> new TargetInformation(coordinate, isPriorityTarget)).findFirst();
	}
}
