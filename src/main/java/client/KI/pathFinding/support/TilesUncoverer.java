package client.KI.pathFinding.support;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import client.KI.KIEnumeration.EClientState;
import client.game.ClientGameState;
import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;

/**
 * This class uncovers the tiles that has been already visited
 * by our player.
 * 
 * @author
 *
 */
public class TilesUncoverer {
	/**
	 * This method uncovers the tiles already visited
	 * 
	 * @param oldMap The old game map
	 * @param newMap The new game map (after player moves)
	 */
	public static ClientGameState uncoverTiles(final ClientMap oldMap, final ClientGameState newClientGameState) {
		final Map<Coordinate, ClientTile> map = newClientGameState.getMap().getMap();
		
		if (oldMap != null && newClientGameState.getMap() != null) {
			for (final Entry<Coordinate, ClientTile> node : oldMap.getMap().entrySet()) {
				if (!node.getValue().isCovered())
					map.get(node.getKey()).uncoverTile();
			}
		} else {
			return new ClientGameState();
		}

		setNewPlayerPosition(map);

		final String id = newClientGameState.getId();
		final ClientMap finalMap = new ClientMap(map);
		final boolean hasTreasure = newClientGameState.isHasCollectedTreasure();
		final EClientState clientState = newClientGameState.getClientState();

		return new ClientGameState(id, finalMap, hasTreasure, clientState);
	}
	
	/**
	 * Updates the position of the player on the given game map and uncovers the tile they're on.
	 * If the player is on a mountain tile, the surrounding tiles will also be uncovered.
	 * 
	 * @param map The current gamemap
	 */
	private static void setNewPlayerPosition(final Map<Coordinate, ClientTile> map) {
		final Optional<ClientTile> playerPosition = PlayerFinder.findPlayerTile(map);
		
		if (playerPosition.isPresent()) {
			playerPosition.get().uncoverTile();
			if (playerPosition.get().getTerrain().equals(EClientTerrain.MOUNTAIN)) {
				final Coordinate playerCoord = PlayerFinder.findPlayerPosition(map).get();
				final Collection<Coordinate> surroundingCoords = SurroundingCoordsCalculator.getSurroundingCoordinates(map.keySet(), playerCoord);
				for (final Coordinate playerCoordinate : surroundingCoords) {
					if (map.containsKey(playerCoordinate)) {
						map.get(playerCoordinate).uncoverTile();
					}
				}
			}
		}
	}
}
