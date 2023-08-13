package client.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.exceptions.NotEnoughArgumentsException;
import client.game.GameController;
import client.game.GameModel;
import client.game.views.ViewSelection;
import client.game.views.gui.JavaFXApplication;
import javafx.application.Application;

/**
 * This class starts our client.
 * @author
 *
 */
public class MainClient {
	private static final Logger logger = LoggerFactory.getLogger(MainClient.class);
	
	/**
	 * We initialize our GUI if we select it and otherwise our ViewSelection class
	 * decides depending on the input args which view to have and registers the following
	 * view and starts the game.
	 * @param args The input args from the command line
	 */
	public static void main(final String[] args) {
		try {
			CheckArgs.checkArgs(args);
		} catch (NotEnoughArgumentsException notEnoughArgsExc) {
			logger.error("Please select one of the 4 views provided: <EMOJI>, <COLOREDCLI>, <GUI> and <TR>. "
					+ "Correct use: [view, serverURL, gameID]");
			System.exit(0);
		}
		
		if (args[0].equalsIgnoreCase("GUI")) {
			Application.launch(JavaFXApplication.class, args);
			return;
		}

		final GameModel gameModel = new GameModel();
		gameModel.addPropertyChangeListener(ViewSelection.chooseView(args));
		
		final GameController gameController = new GameController(args[1], args[2], gameModel);
		gameController.run();
	}
}
