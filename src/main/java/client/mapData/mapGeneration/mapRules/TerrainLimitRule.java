package client.mapData.mapGeneration.mapRules;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;

/**
 * A map rule that checks if the number of tiles of each terrain type is within
 * certain limits.
 * 
 * @author
 */
public class TerrainLimitRule implements IMapRule {
	private final Logger logger = LoggerFactory.getLogger(TerrainLimitRule.class);
	public static final int MIN_GRASS_TILES = 24;
	public static final int MIN_MOUNTAIN_TILES = 5;
	public static final int MIN_WATER_TILES = 7;

	@Override
	/**
     * Validates if the number of tiles of each terrain type is within the
     * required limits.
     *
     * @param map The map to validate
     * @return true if the map is valid, false otherwise
     */
	public boolean validate(final Map<Coordinate, ClientTile> map) {
		logger.trace("Starting to check if there is enough of each terrain...");
		return checkGrassCount(map) && checkMountainCount(map) && checkWaterCount(map);
	}
	
	/**
     * Checks if the number of grass tiles on the map is greater than or equal
     * to MIN_GRASS_TILES.
     *
     * @param map The map to check
     * @return true if the map contains at least MIN_GRASS_TILES grass
     * tiles, false otherwise
     */
	private boolean checkGrassCount(final Map<Coordinate, ClientTile> map) {
		return map.values().stream().filter(tile -> tile.getTerrain().equals(EClientTerrain.GRASS))
				.count() >= MIN_GRASS_TILES;
	}
	
	/**
     * Checks if the number of mountain tiles on the map is greater than or equal
     * to MIN_MOUNTAIN_TILES.
     *
     * @param map The map to check
     * @return true if the map contains at least MIN_MOUNTAIN_TILES mountains
     * tiles, false otherwise
     */
	private boolean checkMountainCount(final Map<Coordinate, ClientTile> map) {
		return map.values().stream().filter(tile -> tile.getTerrain().equals(EClientTerrain.MOUNTAIN))
				.count() >= MIN_MOUNTAIN_TILES; 
	}
	
	/**
     * Checks if the number of water tiles on the map is greater than or equal
     * to MIN_WATER_TILES.
     *
     * @param map The map to check
     * @return true if the map contains at least MIN_WATER_TILES water
     * tiles, false otherwise
     */
	private boolean checkWaterCount(final Map<Coordinate, ClientTile> map) {
		return map.values().stream().filter(tile -> tile.getTerrain().equals(EClientTerrain.WATER))
				.count() >= MIN_WATER_TILES;
	}
}
