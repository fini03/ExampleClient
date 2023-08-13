package client.exceptions;

/**
 * This class throws an exception if something unexpected happened
 * while trying to register a player to the server
 *  
 * @author	
 */
public class CantRegisterPlayerException extends Exception {
	private static final long serialVersionUID = 1L;

	public CantRegisterPlayerException() {
		super();
	}
	
	/**
	 * Thrown if something unexpected happens while registering
	 * @param message Error-Message
	*/
	public CantRegisterPlayerException(final String message) {
		super(message);
	}
}