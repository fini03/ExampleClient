package client.mapData.mapGeneration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapGeneration.mapRules.CorrectCoordinatesRule;
import client.mapData.mapGeneration.mapRules.IMapRule;
import client.mapData.mapGeneration.mapRules.NoIslandRule;
import client.mapData.mapGeneration.mapRules.OneFortOnGrassRule;
import client.mapData.mapGeneration.mapRules.TerrainLimitRule;
import client.mapData.mapGeneration.mapRules.TilesLimitRule;
import client.mapData.mapGeneration.mapRules.WaterCountAtBorderRule;

/**
 * The ValidatorManager class is responsible for validating a generated map against a set of rules.
 * 
 * @author
 */
public class ValidatorManager {
	private final static Logger logger = LoggerFactory.getLogger(ValidatorManager.class);
	private final static List<IMapRule> rules = Arrays.asList(new OneFortOnGrassRule(),
			new TerrainLimitRule(), new TilesLimitRule(), new NoIslandRule(), new WaterCountAtBorderRule(),
			new CorrectCoordinatesRule());
	
	/**
	 * Validates the given map against the set of rules.
	 * 
	 * @param map The map to validate
	 * @return true if all map rules pass, false if any rule fails
	 */
	public static boolean validateMap(final Map<Coordinate, ClientTile> map) {
		logger.trace("Starting to validate map...");
		// Returns true if all map rules pass, and false if any rule fails
		final boolean isMapValid = rules.stream().allMatch(rule -> rule.validate(map));
		return isMapValid;
	}
}
