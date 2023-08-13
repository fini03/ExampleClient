package client.KI.pathFinding.support;

import java.util.ArrayList;
import java.util.Collection;

import client.mapData.Coordinate;

/**
 * A utility class for calculating the surrounding coordinates of a given coordinate
 * 
 * @author
*/
public class SurroundingCoordsCalculator {
	/**
	 * Calculates the adjacent coordinates of a given coordinate
	 * @param allCoords A collection of all coordinates
	 * @param start The coordinate whose adjacent coordinates are to be calculated
	 * @return A collection of adjacent coordinates to the given coordinate that are present in the collection of all coordinates
    */
	public static Collection<Coordinate> getAdjacentCoordinates(final Collection<Coordinate> allCoords, final Coordinate start) {
		final Collection<Coordinate> allAdjacentCoords = new ArrayList<Coordinate>();
		final Collection<Coordinate> actualAdjacentCoords = new ArrayList<Coordinate>();
		
		allAdjacentCoords.add(new Coordinate(start.getX() + 1, start.getY()));
		allAdjacentCoords.add(new Coordinate(start.getX() - 1, start.getY()));
		allAdjacentCoords.add(new Coordinate(start.getX(), start.getY() + 1));
		allAdjacentCoords.add(new Coordinate(start.getX(), start.getY() - 1));
		
		for(final Coordinate coordinate : allAdjacentCoords) {
			if(allCoords.contains(coordinate)) {
				actualAdjacentCoords.add(coordinate);
			}
		}
		return actualAdjacentCoords;
	}
	
	/**
	 * Calculates the surrounding coordinates of a given coordinate
	 * @param allCoords A collection of all coordinates
	 * @param start The coordinate whose surrounding coordinates are to be calculated
	 * @return A collection of surrounding coordinates to the given coordinate that are present in the collection of all coordinates
    */
	public static Collection<Coordinate> getSurroundingCoordinates(final Collection<Coordinate> allCoords, final Coordinate start) {
		final Collection<Coordinate> allAdjacentCoords = getAdjacentCoordinates(allCoords, start);
		final Collection<Coordinate> surroundedCoords = new ArrayList<>();
		
		allAdjacentCoords.add(new Coordinate(start.getX() - 1, start.getY() - 1));
		allAdjacentCoords.add(new Coordinate(start.getX() + 1, start.getY() + 1));
		allAdjacentCoords.add(new Coordinate(start.getX() - 1, start.getY() + 1));
		allAdjacentCoords.add(new Coordinate(start.getX() + 1, start.getY() - 1));
		
		for(final Coordinate adjacentCoord : allAdjacentCoords) {
			if(allCoords.contains(adjacentCoord)) {
				surroundedCoords.add(adjacentCoord);
			}
		}
		return surroundedCoords;
	}
}

