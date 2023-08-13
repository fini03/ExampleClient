package client.exceptions;

/**
 * This class throws an exception if the coordinates
 * we want to move to is not adjacent to our start
 * coordinate
 *  
 * @author	
 */
public class NotAdjacentCoordinatesException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotAdjacentCoordinatesException() {
		super();
	}
	
	/**
	 * Thrown if something unexpected happens while calculating adjacent coords
	 * @param message Error-Message
	 */
	public NotAdjacentCoordinatesException(final String message) {
		super(message);
	}
}