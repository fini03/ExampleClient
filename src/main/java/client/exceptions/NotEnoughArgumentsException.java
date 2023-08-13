package client.exceptions;

/**
 * This class throws an exception if there is a mistake
 * when starting our client from the command line via
 * the args
 * 
 * @author
 */
public class NotEnoughArgumentsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NotEnoughArgumentsException() {
		super();
	}
	
	/**
	 * Thrown if something unexpected happens when starting the client
	 * @param message Error-Message
	 */
	public NotEnoughArgumentsException(final String message) {
		super(message);
	}
}
