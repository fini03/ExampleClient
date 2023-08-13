package client.KI.KIEnumeration;

/**
 * This enum represents the possible moves that a client can make 
 * @author
 */
public enum EClientMove {
	UP(0, -1),
	DOWN(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0);

	private final int xCoordinate;
	private final int yCoordinate;

	private EClientMove(final int xCoordinate, final int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public int getXCoordinate() {
		return xCoordinate;
	}

	public int getYCoordinate() {
		return yCoordinate;
	}
}
