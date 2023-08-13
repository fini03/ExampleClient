package client.game.views;

import java.beans.PropertyChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.exceptions.NotEnoughArgumentsException;

/**
 * This class is responsible for selecting the appropriate view based on the
 * given command-line arguments. If no mode is specified, the base CLI view is
 * selected by default.
 * 
 * @author
 */
public class ViewSelection {
	private static final Logger logger = LoggerFactory.getLogger(ViewSelection.class);
	private final static int ViewArg = 0;

	/**
	 * Chooses the appropriate view based on the given command-line arguments.
	 * 
	 * @param args The command-line arguments.
	 * @return The selected view.
	 */
	public static PropertyChangeListener chooseView(final String[] args) {
		if(args[ViewArg].equalsIgnoreCase("EMOJI")) {
			logger.info("The Emoji CLI was selected!");
			return new CLIEmojiView();
		} else if(args[ViewArg].equalsIgnoreCase("COLOREDCLI")) {
			logger.info("The Colored CLI was selected!");
			return new CLIColoredView();
		} else if(args[ViewArg].equalsIgnoreCase("TR")) {
			logger.info("The default CLI was selected!");
			return new CLIView();
		}
		
		logger.error("There were more arguments then allowed! Size of arguments provided was: {}", args.length);
		throw new NotEnoughArgumentsException("Please select one of the 4 views provided: <EMOJI>, <COLOREDCLI>, <GUI> and <TR>. "
				+ "Correct use: [view, serverURL, gameID]");
	}
}
