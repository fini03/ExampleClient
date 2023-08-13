package client.mapData.mapGeneration;

import client.mapData.mapEnumeration.EClientTerrain;

/**
 * The RandomTerrainGenerator class is responsible for generating random terrains for tiles.
 * 
 * @author
*/
public class RandomTerrainGenerator {
	private final static double chanceForWater = 0.29;
	private final static double chanceForMountain = 0.25;
	
	/**
	 * Chooses and returns a random terrain from the available terrain types.
	 * @return The randomly generated terrain type
    */
	public static EClientTerrain chooseRandomTerrain() {
		EClientTerrain terrain = EClientTerrain.GRASS;
		final double chance = Math.random();
		
		if (chance < chanceForMountain) {
			terrain = EClientTerrain.MOUNTAIN;
		} else if (chance < chanceForMountain + chanceForWater) {
			terrain = EClientTerrain.WATER;
		} else {
			terrain = EClientTerrain.GRASS;
		}
		return terrain;
	}
}
