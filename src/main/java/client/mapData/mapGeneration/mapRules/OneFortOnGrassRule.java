package client.mapData.mapGeneration.mapRules;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;

/**
 * This rule requires that there should be one and only one
 * fort present in the map, and it should be on a tile that
 * has terrain type of "GRASS".
 * 
 * @author
*/
public class OneFortOnGrassRule implements IMapRule {
	private final Logger logger = LoggerFactory.getLogger(OneFortOnGrassRule.class);
	
	@Override
	/**
	 * Validates whether the map has one fort present, and it is
	 * placed on a tile that has terrain type of "GRASS".
	 * @param map The map to be validated
	 * @return true if the map is compliant with the "OneFortOnGrassRule", false otherwise
	*/
	public boolean validate(final Map<Coordinate, ClientTile> map) {
		logger.trace("Starting to check if there is only one fort placed correctly...");
		return checkFortAmount(map) && checkCorrectPlacesFort(map);
	}
	
	/**
	 * Checks whether there is one and only one fort present in the map.
	 * @param map The map to be checked
	 * @return true if there is one and only one fort present in the map, false otherwise
    */
	private boolean checkFortAmount(final Map<Coordinate, ClientTile> map) {
		final long moreThanOneCastle = map.values().stream().filter(tile -> tile.isFortPresent()).count();
		return moreThanOneCastle == 1;
	}
	
	/**
	 * Checks whether the fort is placed on a tile that has terrain type of "GRASS".
	 * @param map The map to be checked
	 * @return true if the fort is placed on a tile that has terrain type of "GRASS", false otherwise
    */
	private boolean checkCorrectPlacesFort(final Map<Coordinate, ClientTile> map) {
		return map.values().stream().anyMatch(tile -> tile.isFortPresent() && tile.getTerrain() == EClientTerrain.GRASS);
	}
}
