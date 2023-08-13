package client.KI.pathFinding.support;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.KI.KIEnumeration.EClientMove;
import client.mapData.Coordinate;

/**
 * This class is used to translate our constructed path
 * to moves the client can send to the server
 * @author
 *
 */
public class PathToMoves {
	private final static Logger logger = LoggerFactory.getLogger(PathToMoves.class);
	/**
	 * We use this to construct our moves to bring us to our target
	 * @param map The current map we want to move on
	 * @param path The path we want to translate
	 * @return The path translated to moves
	 */
	public static List<EClientMove> transformPathToMoves(final List<Coordinate> path) {
		final List<EClientMove> coordinatesDirectionToMoves = new ArrayList<>();
		for (int coordinate = 0; coordinate < path.size() - 1; coordinate++) {
			final Coordinate start = path.get(coordinate);
			final Coordinate target = path.get(coordinate + 1);
			final EClientMove translatedMove = CoordinateDirectionsToMove.transformCoordinateDirectionToMove(start, target);
			
			coordinatesDirectionToMoves.add(translatedMove);
		}
		logger.debug("Our moves list is the following: {}", coordinatesDirectionToMoves);
		return coordinatesDirectionToMoves;
	}
}
