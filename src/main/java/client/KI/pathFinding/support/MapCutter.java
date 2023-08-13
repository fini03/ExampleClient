package client.KI.pathFinding.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientFortState;

/**
 * This class is responsible for dividing a given map into two halves, either
 * vertically or horizontally.
 * 
 * @author
 */
public class MapCutter {
	private final static Logger logger = LoggerFactory.getLogger(MapCutter.class);
	private final static int HalfXWidth = 10;
	private final static int HalfYWidth = 5;

	/**
	 * This method decides which half of the fullMap should be divided
	 * @param fullMap The full map to be divided
	 * @param hasTreasure Value indicating whether the player has collected treasure or not
	 * @return A divided ClientMap with no water tiles
	 */
	public static ClientMap divideMap(final ClientMap fullMap, final boolean hasTreasure) {
	    logger.trace("Dividing map...");
	    final Map<Coordinate, ClientTile> mapToBeCut = fullMap.getMap();
	    final Coordinate biggestCoord = new Coordinate(19, 4);
	    final Coordinate maxCoord = fullMap.getMaxCoord();

	    final boolean divideX = maxCoord.equals(biggestCoord);

	    final Optional<Coordinate> fortPosition = mapToBeCut.entrySet().stream()
	            .filter(eachTile -> eachTile.getValue().getClientFortState().equals(EClientFortState.MyFortPresent))
	            .map(Map.Entry::getKey).findFirst();

	    final boolean isLeftOrUp = divideX ? fortPosition.get().getX() < HalfXWidth
	            : fortPosition.get().getY() < HalfYWidth;

	    final String partOfMap = hasTreasure ? (isLeftOrUp ? "enemy" : "own") : (isLeftOrUp ? "own" : "enemy");
	    logger.debug("Divided map into {} part", partOfMap);

	    final String direction = divideX ? (isLeftOrUp ? "left" : "right") : (isLeftOrUp ? "up" : "down");
	    logger.debug("Divided map in {} direction", direction);

	    final HashMap<Coordinate, ClientTile> halfMap = mapToBeCut.entrySet().stream().filter(coords -> {
	        final boolean innerIsLeftOrUp = hasTreasure ? !isLeftOrUp : isLeftOrUp;
	        if (divideX) {
	            return innerIsLeftOrUp ? coords.getKey().getX() < HalfXWidth : coords.getKey().getX() >= HalfXWidth;
	        } else {
	            return innerIsLeftOrUp ? coords.getKey().getY() < HalfYWidth : coords.getKey().getY() >= HalfYWidth;
	        }
	    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next, HashMap::new));

	    return new ClientMap(halfMap);
	}
}
