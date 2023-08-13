package client.mapData;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.mapData.mapGeneration.MapGenerator;

/**
 * This class represents the client-side game map.
 * It contains a map of coordinates and client tiles.
 * 
 * @author
*/
public class ClientMap {
	private final static Logger logger = LoggerFactory.getLogger(ClientMap.class);
	private final Map<Coordinate, ClientTile> map;
	
	/**
	 * Constructs a new half client-side map using MapGenerator.
    */
	public ClientMap() {
		this.map = MapGenerator.createHalfMap();
	}
	
	/**
	 * Constructs a new client-side map with the given map of coordinates and client tiles.
	 * @param map - The map of coordinates and client tiles that make up the game map.
    */
	public ClientMap(final Map<Coordinate, ClientTile> map) {
		this.map = map;
	}

	public Map<Coordinate, ClientTile> getMap(){
		return map;
	}
	
	/**
	 * Calculates the maximum coordinates of the game map.
	 * @return The maximum coordinates of the game map.
    */
	public Coordinate getMaxCoord() {
		final Coordinate start = new Coordinate(0, 0);
	    final Coordinate maxCoord = map.keySet().stream()
	            .reduce(start, (currentMax, currentCoord) -> {
	            	final int maxX = Math.max(currentMax.getX(), currentCoord.getX());
	            	final int maxY = Math.max(currentMax.getY(), currentCoord.getY());
	                return new Coordinate(maxX, maxY);
	            });
	    
		logger.debug("The max coordinate is {}", maxCoord.toString());
		return maxCoord;
	}
}
