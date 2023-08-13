package client.KI.pathFinding.support;

import java.util.HashMap;
import java.util.Map;

import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;

/**
 * This class is used to filter out the tiles that contain water
 * @author
 *
 */
public class WaterFilter {
	/**
	 * Filters out all tiles with water terrain from the given map of tiles
	 * @param mapTiles The map of tiles to filter
	 * @return A new map of tiles with only non-water tiles from the original map
	 */
	public static Map<Coordinate, ClientTile> getMapWithNoWaterTiles(final Map<Coordinate, ClientTile> mapTiles) {
		final Map<Coordinate, ClientTile> filteredTiles = new HashMap<>();
		
		mapTiles.entrySet().stream().filter(eachTile -> !eachTile.getValue().getTerrain().equals(EClientTerrain.WATER))
				.forEach(eachTile -> filteredTiles.put(eachTile.getKey(), eachTile.getValue()));

		return filteredTiles;
	}
}
