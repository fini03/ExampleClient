package client.mapData.mapGeneration.mapRules;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.mapData.ClientTile;
import client.mapData.Coordinate;

/**
 * A rule that checks whether all coordinates in the map are within a certain range and unique.
 * 
 * @author
 */
public class CorrectCoordinatesRule implements IMapRule {
	private final Logger logger = LoggerFactory.getLogger(CorrectCoordinatesRule.class);
	private final int MIN_HALF_MAP_X = 0;
	private final int MAX_HALF_MAP_X = 9;
	private final int MIN_HALF_MAP_Y = 0;
	private final int MAX_HALF_MAP_Y = 4;

	@Override
	/**
     * Validates if all coordinates in the map are within the specified range and are unique
     * @param map The map to validate
     * @return true if all coordinates are valid and unique, false otherwise
     */
	public boolean validate(final Map<Coordinate, ClientTile> map) {
		logger.trace("Starting to check if all coordinates are correct...");
		return allXCoordinatesValid(map) && allYCoordinatesValid(map) && allUniqueCoordinates(map);
	}
	
	/**
     * Validates if all x-coordinates in the map are within the specified range
     *
     * @param map The map to validate
     * @return true if all x-coordinates are valid, false otherwise
     */
	private boolean allXCoordinatesValid(final Map<Coordinate, ClientTile> map) {
		return map.keySet().stream()
				.allMatch(coordinate -> coordinate.getX() >= MIN_HALF_MAP_X && coordinate.getX() <= MAX_HALF_MAP_X);
	}
	
	/**
     * Validates if all y-coordinates in the map are within the specified range
     *
     * @param map The map to validate
     * @return true if all y-coordinates are valid, false otherwise
     */
	private boolean allYCoordinatesValid(final Map<Coordinate, ClientTile> map) {
		return map.keySet().stream()
				.allMatch(coordinate -> coordinate.getY() >= MIN_HALF_MAP_Y && coordinate.getY() <= MAX_HALF_MAP_Y);
	}
	
	 /**
     * Validates if all coordinates in the map are unique
     *
     * @param map The map to validate
     * @return true if all coordinates are unique, false otherwise
     */
	private boolean allUniqueCoordinates(final Map<Coordinate, ClientTile> map) {
		return map.keySet().stream().map(coordinates -> new Coordinate(coordinates.getX(), coordinates.getY()))
				.distinct().count() == map.size();
	}
}
