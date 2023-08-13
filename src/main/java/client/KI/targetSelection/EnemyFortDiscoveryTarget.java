package client.KI.targetSelection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.KI.pathFinding.DijkstraAlgorithm;
import client.KI.pathFinding.support.MapCutter;
import client.KI.pathFinding.support.PathToMoves;
import client.KI.pathFinding.support.PlayerFinder;
import client.KI.pathFinding.support.WaterFilter;
import client.game.ClientGameState;
import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;

/**
 * Implementation of the {@link ITargetSelectionPicker} interface that finds the
 * next tile to discover when searching for enemy's fort.
 * 
 * @author
 */
public class EnemyFortDiscoveryTarget implements ITargetSelectionPicker {
	private final Logger logger = LoggerFactory.getLogger(EnemyFortDiscoveryTarget.class);
	private final static int maxCostToReachEnemyCastle = 12;

	@Override
	/**
	 * Searches for the enemy's fort on the given game state map, if the treasure
	 * has been collected.
	 * 
	 * @param clientGameState The current game state of the client
	 * @return TargetInformation (coordinate, isPriorityTarget) of the next target.
	 */
	public Optional<TargetInformation> findTarget(final ClientGameState clientGameState) {
		if (!clientGameState.isHasCollectedTreasure()) {
			return Optional.empty();
		}

		logger.info("Search for enemy's castle has started...");

		final boolean hasTreasureCollected = true;
		final int isPriorityTarget = 3;
		final ClientMap map = MapCutter.divideMap(clientGameState.getMap(), hasTreasureCollected);
		final Map<Coordinate, ClientTile> cuttedMap = map.getMap();
		final Optional<Coordinate> enemyPosition = clientGameState.getEnemyPosition();
		List<Coordinate> possibleFortLocations = new ArrayList<>();
		
		if(enemyPosition.isPresent()) {
			possibleFortLocations = calculateEnemyFortPosition(cuttedMap, clientGameState.getMap().getMap(), enemyPosition.get());
		}
		
		if (!possibleFortLocations.isEmpty()) {
            
            logger.info("Possible castle locations were found {}", possibleFortLocations);
            
            final Optional<Coordinate> playerPosition = PlayerFinder.findPlayerPosition(map.getMap());
            if (playerPosition.isPresent()) {
                final Coordinate playerCoordinate = playerPosition.get();
                return possibleFortLocations.stream().sorted((coordinate1, coordinate2) -> {
                    final int dist1 = Math.abs(coordinate1.getX() - playerCoordinate.getX())
                            + Math.abs(coordinate1.getY() - playerCoordinate.getY());
                    final int dist2 = Math.abs(coordinate2.getX() - playerCoordinate.getX())
                            + Math.abs(coordinate2.getY() - playerCoordinate.getY());
                    return Integer.compare(dist1, dist2);
                }).map(coordinate -> new TargetInformation(coordinate, isPriorityTarget))
                        .findFirst();
            }
        }
		
	    logger.info("Possible castle locations not found. Searching for covered grass tiles...");
		
		final Map<Coordinate, ClientTile> discoveryTiles = WaterFilter.getMapWithNoWaterTiles(cuttedMap);

		Stream<Coordinate> candidates = discoveryTiles.entrySet().stream()
				.filter(eachTile -> (cuttedMap.get(eachTile.getKey()).getTerrain() == EClientTerrain.GRASS)
						&& eachTile.getValue().isCovered())
				.map(Map.Entry::getKey);

		final Optional<Coordinate> playerPosition = PlayerFinder.findPlayerPosition(map.getMap());
		if (playerPosition.isPresent()) {
			final Coordinate playerCoordinate = playerPosition.get();
			candidates = candidates.sorted((coordinate1, coordinate2) -> {
				final int dist1 = Math.abs(coordinate1.getX() - playerCoordinate.getX())
						+ Math.abs(coordinate1.getY() - playerCoordinate.getY());
				final int dist2 = Math.abs(coordinate2.getX() - playerCoordinate.getX())
						+ Math.abs(coordinate2.getY() - playerCoordinate.getY());
				return Integer.compare(dist1, dist2);
			});
		}

		return candidates.map(coordinate -> new TargetInformation(coordinate, isPriorityTarget)).findFirst();
	}
	
	/**
	 * This method calculates the possible locations of the enemy fort based on the
	 * position of the enemy.
	 * @param cuttedMap The enemy side of the fullMap
	 * @param enemyPlayerPosition the position of the enemy after its revealed
	 * @param maxCost the maximum movement cost to reach the castle
	 * @return A list of possible enemy castle positions
	 */
	private static List<Coordinate> calculateEnemyFortPosition(final Map<Coordinate, ClientTile> cuttedMap, final Map<Coordinate, ClientTile> fullMap,
			Coordinate enemyPlayerPosition) {

		final List<Entry<Coordinate, ClientTile>> grassfields = cuttedMap.entrySet().stream().filter(
				tile -> (tile.getValue().getTerrain().equals(EClientTerrain.GRASS) && tile.getValue().isCovered()))
				.toList();

		final Map<Coordinate, Integer> grassFieldMovementCost = new HashMap<>();

		for (final Entry<Coordinate, ClientTile> node : grassfields) {
			final Coordinate target = node.getKey();
			 final List<Coordinate> path = DijkstraAlgorithm.findPath(fullMap, enemyPlayerPosition, target);
	         grassFieldMovementCost.put(target, PathToMoves.transformPathToMoves(path).size());
		}

		final Stream<Entry<Coordinate, Integer>> sorted = grassFieldMovementCost.entrySet().stream()
				.sorted(Entry.comparingByValue());

		final List<Coordinate> possiblePositions = sorted.filter(entry -> entry.getValue() < maxCostToReachEnemyCastle).map(Entry::getKey)
				.toList();

		return possiblePositions;
	}
}
