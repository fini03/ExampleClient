package client.mapData.mapGeneration.mapRules;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;

public class WaterCountAtBorderRule implements IMapRule {
	private static Logger logger = LoggerFactory.getLogger(WaterCountAtBorderRule.class);
	private final static int X_AXIS_WATERFIELDS_LIMIT = 4;
	private final static int Y_AXIS_WATERFIELDS_LIMIT = 2;
	private final static int X_AXIS_RIGHT = 9;
	private final static int X_AXIS_LEFT = 0;
	private final static int Y_AXIS_UP = 4;
	private final static int Y_AXIS_DOWN = 0;

	@Override
	/**
	 * Validates if the map has the minimum amount of water tiles at its border.
	 * 
	 * @param map The map to be validated.
	 * @return true if the map has the minimum amount of water tiles at its border, false otherwise.
	 */
	public boolean validate(final Map<Coordinate, ClientTile> map) {
		logger.trace("Starting to check if there the min amount of water tiles at border...");
		return checkWaterCountAtBorder(map);
	}
	
	/**
	 * Checks if there is at least the minimum amount of water tiles on each border of the map.
	 * 
	 * @param map The map to be checked.
	 * @return true if there is at least the minimum amount of water tiles on each border of the map, false otherwise.
	 */
	private boolean checkWaterCountAtBorder(final Map<Coordinate, ClientTile> map) {
		final List<Entry<Coordinate, ClientTile>> waterBorderMap = map.entrySet().stream()
				.filter(tile -> tile.getValue().getTerrain() == EClientTerrain.WATER).toList();

		return checkRightBorder(waterBorderMap) && checkLeftBorder(waterBorderMap) && checkUpBorder(waterBorderMap)
				&& checkDownBorder(waterBorderMap);
	}
	
	/**
	 * Checks if there is at most the maximum number of water tiles allowed on the left border of the map.
	 * 
	 * @param waterMap The list of water tiles at the border.
	 * @return true if there is at most the maximum number of water tiles allowed on the left border of the map, false otherwise.
	 */
	private boolean checkLeftBorder(final List<Entry<Coordinate, ClientTile>> waterMap) {
		return waterMap.stream().filter(tile -> tile.getKey().getX() == X_AXIS_LEFT)
				.count() <= Y_AXIS_WATERFIELDS_LIMIT;
	}
	
	/**
	 * Checks if there is at most the maximum number of water tiles allowed on the right border of the map.
	 * 
	 * @param waterMap The list of water tiles at the border.
	 * @return true if there is at most the maximum number of water tiles allowed on the right border of the map, false otherwise.
	 */
	private boolean checkRightBorder(final List<Entry<Coordinate, ClientTile>> waterMap) {
		return waterMap.stream().filter(tile -> tile.getKey().getX() == X_AXIS_RIGHT)
				.count() <= Y_AXIS_WATERFIELDS_LIMIT;
	}
	
	/**
	 * Checks if there is at most the maximum number of water tiles allowed on the top border of the map.
	 * 
	 * @param waterMap The list of water tiles at the border.
	 * @return true if there is at most the maximum number of water tiles allowed on the top border of the map, false otherwise.
	 */
	private boolean checkUpBorder(final List<Entry<Coordinate, ClientTile>> waterMap) {
		return waterMap.stream().filter(tile -> tile.getKey().getY() == Y_AXIS_UP)
				.count() <= X_AXIS_WATERFIELDS_LIMIT;
	}
	
	/**
	 * Checks if there is at most the maximum number of water tiles allowed on the bottom border of the map.
	 * 
	 * @param waterMap The list of water tiles at the border.
	 * @return true if there is at most the maximum number of water tiles allowed on the bottom border of the map, false otherwise.
	 */
	private boolean checkDownBorder(final List<Entry<Coordinate, ClientTile>> waterMap) {
		return waterMap.stream().filter(tile -> tile.getKey().getY() == Y_AXIS_DOWN)
				.count() <= X_AXIS_WATERFIELDS_LIMIT;
	}
}
