package client.mapData.mapGeneration.mapRules;

import java.util.Map;

import client.mapData.ClientTile;
import client.mapData.Coordinate;

/**
 * The IMapRule interface represents a validation rule for a map consisting of
 * coordinates and tiles.
 * 
 * @author
 */
public interface IMapRule {
	/**
     * Validates the given map according to the specific rule
     * 
     * @param map The map to validate
     * @return true if the map passes the validation rule, false otherwise
     */
	boolean validate(final Map<Coordinate, ClientTile> map);
}
