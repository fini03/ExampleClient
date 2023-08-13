package client.mapData.mapGeneration.mapRules;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;

/**
 * This class implements the IMapRule interface and validates that there are no islands in the map
 * 
 * @author
 */
public class NoIslandRule implements IMapRule {
	private final Logger logger = LoggerFactory.getLogger(NoIslandRule.class);
	/*
	 * TAKEN FROM <1> Implementation of flood fill algorithm using different
	 * languages and methods like recursive and BFS Source:
	 * https://www.geeksforgeeks.org/flood-fill-algorithm-implement-fill-paint“
	 */
	
	@Override
	/**
	 * Checks if there are any islands in the map
	 * @param map The map to validate
	 * @return true if there are no islands in the map, false otherwise
    */
	public boolean validate(final Map<Coordinate, ClientTile> map) {
		logger.trace("Starting to check if the map has any islands...");
		return checkIfThereIsNotAnIsland(map);
	}
	
	/**
	 * Checks if there are any islands in the map
	 * @param map The map to validate
	 * @return true if there are no islands in the map, false otherwise
    */
	private boolean checkIfThereIsNotAnIsland(final Map<Coordinate, ClientTile> map) {
		final Set<Coordinate> visitedPlaces = new HashSet<>();
		int counter = 0;
		for (final Coordinate coordinates : map.keySet()) {
			if (visitedPlaces.contains(coordinates)) {
				continue;
			}

			if (map.get(coordinates).getTerrain().equals(EClientTerrain.GRASS)
					|| map.get(coordinates).getTerrain().equals(EClientTerrain.MOUNTAIN)) {
				isAllAccessible(coordinates.getX(), coordinates.getY(), map, visitedPlaces);
				counter++;
			}
		}
		return counter == 1;
	}
	
	/**
	 * Checks if there is an island at the given coordinates.
	 * 
	 * @param x The x coordinate of the starting position
	 * @param y The y coordinate of the starting position
	 * @param map The map to validate
	 * @param visitedPlaces A set of visited coordinates
	 */
	private void isAllAccessible(final int x, final int y, final Map<Coordinate, ClientTile> map, final Set<Coordinate> visitedPlaces) {
		/*
		 * changes to the source code were needed for implementation but the main
		 * algorithm was taken from source
		 * TAKEN FROM START <1>
		 */
		if (x < 0 || y < 0 || x > 9 || y > 4) {
			return;
		}
		final Coordinate coordinates = new Coordinate(x, y);
		if (visitedPlaces.contains(coordinates)) {
			return;
		}
		if (map.get(coordinates).getTerrain().equals(EClientTerrain.WATER)) {
			return;
		}
		visitedPlaces.add(coordinates);
		isAllAccessible(x + 1, y, map, visitedPlaces);
		isAllAccessible(x - 1, y, map, visitedPlaces);
		isAllAccessible(x, y + 1, map, visitedPlaces);
		isAllAccessible(x, y - 1, map, visitedPlaces);

		/*
		 * TAKEN FROM END <1>
		 */
	}
}
