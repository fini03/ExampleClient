package client.mapData.mapGeneration;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.KI.pathFinding.support.SurroundingCoordsCalculator;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;

/**
 * The MapGenerator class is responsible for creating a half-sized map for the game.
 * 
 * @author
*/
public class MapGenerator {
	private final static Logger logger = LoggerFactory.getLogger(MapGenerator.class);
	private final static int maxXWidth = 10;
	private final static int maxYHeight = 5;
	private static int countFailedGenerations = 0;
	
	/**
	 * This method creates a half-sized map by calling the generateMap method. It then validates the generated map
	 * using the ValidatorManager. If the map is invalid, it generates a new map until a valid map is generated.
	 * 
	 * @return A half-sized map for the game
    */
	public static Map<Coordinate, ClientTile> createHalfMap() {
		final Map<Coordinate, ClientTile> map = generateMap();
		
		if (!ValidatorManager.validateMap(map)) {
			countFailedGenerations++;
			logger.trace("False Map was generated!");
			return createHalfMap();
		}
		
		logger.info("Correct map has been generated after {} tries...", countFailedGenerations);
		return map;
	}
	
	/**
	 * This method generates a half-sized map with random terrain and returns it.
	 * @return A randomly generated half-sized map.
    */
	private static Map<Coordinate, ClientTile> generateMap() {
		logger.trace("Starting to generate map...");
		final Map<Coordinate, ClientTile> map = new HashMap<>();
		
		for (int xCoord = 0; xCoord < maxXWidth; xCoord++) {
			for (int yCoord = 0; yCoord < maxYHeight; yCoord++) {
				final Coordinate coordinate = new Coordinate(xCoord, yCoord);
				final ClientTile tile = new ClientTile(RandomTerrainGenerator.chooseRandomTerrain());
				map.put(coordinate, tile);
			}
		}
		
		placeFort(map);
		
		return map;
	}
	
	/**
	 * This method decides what is the best placement for the fort
	 * @param map The generated map to place the fort on
	 */
	private static void placeFort(final Map<Coordinate, ClientTile> map) {
		if(placeFortNearGrassTiles(map)) {
			return;
		}
		setRandomFortPosition(map);	
	}
	
	/**
	 * This method places the fort on grassfields which is surrounded by grass
	 * @param map The map to place the fort on
	 * @return true, if the fort was placed and false otherwise
	 */
	private static boolean placeFortNearGrassTiles(final Map<Coordinate, ClientTile> map) {
		logger.trace("Setting fort position near a grass tile...");
		for(int yCoord = 0; yCoord < maxYHeight; yCoord++) {
			for(int xCoord = 0; xCoord < maxXWidth; xCoord++) {
				final Coordinate coordinate = new Coordinate(xCoord, yCoord);
				final EClientTerrain terrain = map.get(coordinate).getTerrain();
				if(terrain.equals(EClientTerrain.GRASS) && noSurroundingMountains(map, coordinate)) {
					map.get(coordinate).placeOwnFort();
					logger.info("Fort position is set to {}", coordinate.toString());
					return true;
				}			
			}
		}
		return false;
	}
	
	/**
	 * This method checks if there is no mountains surrounding the tile we chose
	 * @param map The map to be checked
	 * @param start The coordinate of our starting tile
	 * @return true, if there is no mountains and false otherwise
	 */
	private static boolean noSurroundingMountains(final Map<Coordinate, ClientTile> map, final Coordinate start) {
		final Collection<Coordinate> surroundingCoords = SurroundingCoordsCalculator.getSurroundingCoordinates(map.keySet(), start);
		
		for(final Coordinate coordinate : surroundingCoords) {
			final EClientTerrain terrain = map.get(coordinate).getTerrain();
			if(terrain.equals(EClientTerrain.MOUNTAIN)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method sets the fort position on the generated map. It gets a list of grass fields on the map
	 * and randomly selects one of them as the fort position. It then places the fort on the selected position.
	 * 
	 * @param map The map on which the fort is to be placed.
    */
	private static void setRandomFortPosition(final Map<Coordinate, ClientTile> map) {
		logger.trace("Setting random fort position...");
		final List<Entry<Coordinate, ClientTile>> grassFieldsOnMap = map.entrySet().stream()
				.filter(tile -> tile.getValue().getTerrain() == EClientTerrain.GRASS).toList();

		final int randomIndex = new Random().nextInt(grassFieldsOnMap.size());
		final Coordinate castlePosition = grassFieldsOnMap.get(randomIndex).getKey();
		logger.info("Fort position is set to {}", castlePosition.toString());
		
		map.get(castlePosition).placeOwnFort();
	}
}
