package client.exceptions;

/**
 * This exception is thrown if there is an unexpected
 * behavior while converting an enum
 * 
 * @author
 */
public class CannotConvertEnumException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CannotConvertEnumException() {
		super();
	}

	/**
	 * Thrown if something unexpected happens while converting Enumerations
	 * @param message Error-Message
	*/
	public CannotConvertEnumException(final String message) {
		super(message);
	}
}
