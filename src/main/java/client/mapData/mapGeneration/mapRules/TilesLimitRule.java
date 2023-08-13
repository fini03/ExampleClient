package client.mapData.mapGeneration.mapRules;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.mapData.ClientTile;
import client.mapData.Coordinate;

/**
 * This rule checks if the map has exactly 50 tiles.
 * 
 * @author
*/
public class TilesLimitRule implements IMapRule {
	private final Logger logger = LoggerFactory.getLogger(TilesLimitRule.class);
	private final static int TILES_COUNT_LIMIT = 50;

	@Override
	/**
	 * Checks if the map has exactly 50 tiles.
	 * @param map The map to validate
	 * @return true if the map has exactly 50 tiles, false otherwise
    */
	public boolean validate(final Map<Coordinate, ClientTile> map) {
		logger.trace("Starting to check if there is 50 tiles...");
		return checkIf50TilesArePresent(map);
	}
	
	/**
	 * Checks if the map has exactly 50 tiles.
	 * @param map The map to validate
	 * @return true if the map has exactly 50 tiles, false otherwise
    */
	public boolean checkIf50TilesArePresent(final Map<Coordinate, ClientTile> map) {
		final Collection<ClientTile> tiles = map.values();
		if (tiles.size() != TILES_COUNT_LIMIT) {
			return false;
		}

		return true;
	}
}
