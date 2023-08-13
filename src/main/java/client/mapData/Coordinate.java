package client.mapData;

import java.util.Objects;

/**
 * Class for storing x and y Coordinates
 * 
 * @author
 */
public class Coordinate {
	private final int x;
	private final int y;
	
	/**
	 * Constructs a new Coordinate object with the given x and y coordinates.
	 *
	 * @param x the x coordinate of the new Coordinate
	 * @param y the y coordinate of the new Coordinate
	 */
	public Coordinate(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "Coordinates [x=" + x + ", y=" + y + "]";
	}
	
	/**
	 * Returns a new Coordinate object that represents the result of subtracting
	 * the given x and y values from this Coordinate's coordinates.
	 *
	 * @param x the x value to subtract from this Coordinate's x coordinate
	 * @param y the y value to subtract from this Coordinate's y coordinate
	 * @return a new Coordinate object that represents the result of the subtraction
	 */
	public Coordinate sub(final int x, final int y) {
		final Coordinate subtractedCoord = new Coordinate(this.x - x, this.y - y);
		return subtractedCoord;
	}
}
