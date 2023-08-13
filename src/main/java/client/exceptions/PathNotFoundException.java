package client.exceptions;

/**
 * This Exception is thrown in case there is no path to move on the map
 * 
 * @author
 */
public class PathNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

	public PathNotFoundException () {
		super();
	}

	/**
	 * Thrown if there is no path we can to calculate
	 * @param message Error-Message
	 */
    public PathNotFoundException (final String message) {
        super(message);
    }
}
