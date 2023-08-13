package client.KI.pathFinding.support;

import client.KI.KIEnumeration.EClientMove;
import client.exceptions.NotAdjacentCoordinatesException;
import client.mapData.Coordinate;

/**
 * This class is used to translate the value of the move to the enumeration 
 * @author
 *
 */
public class CoordinateDirectionsToMove {
	/**
	 * We use this method to transform the the distance from start to target to a
	 * move
	 * @param currentPosition The start coordinate
	 * @param targetPosition The coordinate we want to be on
	 * @return Movement to target
	 */
	public static EClientMove transformCoordinateDirectionToMove(final Coordinate currentPosition,
			final Coordinate targetPosition) {
		EClientMove move;
		final Coordinate displacement = targetPosition.sub(currentPosition.getX(), currentPosition.getY());
		final Coordinate downCoords = new Coordinate(0, 1);
		final Coordinate leftCoords = new Coordinate(-1, 0);
		final Coordinate rightCoords = new Coordinate(1, 0);
		final Coordinate upCoords = new Coordinate(0, -1);

		if (displacement.equals(downCoords)) {
			move = EClientMove.DOWN;
		} else if (displacement.equals(leftCoords)) {
			move = EClientMove.LEFT;
		} else if (displacement.equals(rightCoords)) {
			move = EClientMove.RIGHT;
		} else if (displacement.equals(upCoords)) {
			move = EClientMove.UP;
		} else {
			throw new NotAdjacentCoordinatesException("Coordinate are not adjacent!");
		}

		return move;
	}
}
