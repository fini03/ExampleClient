package client.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.exceptions.NotEnoughArgumentsException;

/**
 * This class validates the input arguments
 * 
 * @author
 *
 */
public class CheckArgs {
	private final static Logger logger = LoggerFactory.getLogger(CheckArgs.class);
	private final static int argsLength = 3;
	private final static int ViewArg = 0;

	/**
	 * This method check the input args
	 * 
	 * @param args Array which has the input args
	 * @throws NotEnoughArgumentsException If something unexpected happens while
	 *                                     reading the args
	 */
	public static void checkArgs(final String[] args) throws NotEnoughArgumentsException {
		checkArgsLength(args);

		if (args.length == argsLength) {
			checkViewArgs(args[ViewArg]);
		}
	}

	/**
	 * Checks if there were too many or too less arguments
	 * 
	 * @param arguments List of the Arguments
	 * @throws UnexpectedArgumentException thrown if there are too many or too less
	 *                                     arguments.
	 */
	private static void checkArgsLength(final String[] args) throws NotEnoughArgumentsException {
		if (args.length < argsLength) {
			logger.error("There wasn't enough arguments! Size of arguments provided was: {}", args.length);
			throw new NotEnoughArgumentsException("Not enough arguments provided! "
					+ "Please select one of the 4 views provided: <EMOJI>, <COLOREDCLI>, <GUI> and <TR>. "
					+ "Correct use: [view, serverURL, gameID]");
		}

		if (args.length > argsLength) {
			logger.error("There were more arguments then allowed! Size of arguments provided was: {}", args.length);
			throw new NotEnoughArgumentsException("More arguments provided then allowed! "
					+ "Please select one of the 4 views provided: <EMOJI>, <COLOREDCLI>, <GUI> and <TR>. "
					+ "Correct use: [view, serverURL, gameID]");
		}
	}

	/**
	 * This method validates the view args. It checks if the input string is one of
	 * the known views we have specified
	 * 
	 * @param view Selected view as a String
	 * @throws NotEnoughArgumentsException If user selects unknown view
	 */
	private static void checkViewArgs(final String view) throws NotEnoughArgumentsException {
		if (!(view.equalsIgnoreCase("EMOJI") || view.equalsIgnoreCase("COLOREDCLI") || view.equalsIgnoreCase("GUI") || view.equalsIgnoreCase("TR"))) {
			logger.error("The chosen view is not specified!");
			throw new NotEnoughArgumentsException(
					"Please select one of the 4 views provided: <EMOJI>, <COLOREDCLI>, <GUI> and <TR>");
		}
	}
}
