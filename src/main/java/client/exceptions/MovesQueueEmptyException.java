package client.exceptions;

/**
 * This Exception is thrown in case there is no move queue
 * to perform any moves on the map
 * 
 * @author
 */
public class MovesQueueEmptyException extends Exception {
	private static final long serialVersionUID = 1L;

	public MovesQueueEmptyException () {
		super();
	}

	/**
	 * Thrown if our move queue is empty
	 * @param message Error-Message
	 */
    public MovesQueueEmptyException (final String message) {
        super(message);
    }
}
