package client.KI.pathFinding.support;

import java.util.Map;
import java.util.Optional;

import client.mapData.ClientTile;
import client.mapData.Coordinate;
/**
 * This class is used to find the player on the current map
 * @author
 */
public class PlayerFinder {
	/**
	 * We use this to find the player coordinates on the map
	 * @param mapTiles The map to search for the player coordinates
	 * @return The coordinates of the player
	 */
	public static Optional<Coordinate> findPlayerPosition(final Map<Coordinate, ClientTile> mapTiles) {
		final Optional<Coordinate> playerPosition = mapTiles.entrySet().stream()
	            .filter(eachTile -> eachTile.getValue().isPlayerPresent())
	            .map(Map.Entry::getKey)
	            .findFirst();

		return playerPosition;
	}
	
	/**
	 * We use this to find the enemy coordinates on the map
	 * @param mapTiles The map to search for the enemy coordinates
	 * @return The coordinates of the enemy
	 */
	public static Optional<Coordinate> findEnemyPosition(final Map<Coordinate, ClientTile> mapTiles) {
		final Optional<Coordinate> enemyPosition = mapTiles.entrySet().stream()
	            .filter(eachTile -> eachTile.getValue().isEnemyPresent())
	            .map(Map.Entry::getKey)
	            .findFirst();

		return enemyPosition;
	}
	
	/**
	 * We use this to find the tile the player is currently on
	 * @param mapTiles The map to search for the tile
	 * @return The tile our player is on
	 */
	public static Optional<ClientTile> findPlayerTile(final Map<Coordinate, ClientTile> mapTiles) {
		final Optional<ClientTile> playerTile = mapTiles.entrySet().stream()
				.filter(eachTile -> eachTile.getValue().isPlayerPresent())
				.map(Map.Entry::getValue)
				.findFirst();

		return playerTile;
	}
}
