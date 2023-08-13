package client.game.views.gui;

import client.game.GameController;
import client.game.GameModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The JavaFXApplication class is the entry point for the GUI of the game.
 * It initializes the MVC architecture, creates the GUI view, sets up the scene,
 * and starts the controller thread in the background for updates.
 * 
 * @author
*/
public class JavaFXApplication extends Application {
	@Override
	/**
	 * Starts the JavaFX application by initializing the MVC architecture.
	 * @param stage The primary stage for the JavaFX application.
    */
	public void start(final Stage stage) {
		// Initialize MVC
		final GameModel model = new GameModel();
		final GameController controller = new GameController(getParameters().getUnnamed().get(1),
				getParameters().getUnnamed().get(2), model);
		final GuiView view = new GuiView(model);

		final Scene scene = new Scene(view.asParent(), 900, 500);
		stage.setScene(scene);
		stage.show();

		// Start controller thread in background for updates
		new Thread(controller).start();
	}
	
	/**
	 * Launches the JavaFX view with the given command line arguments.
	 * @param args The command line arguments for the JavaFX application.
    */
	public void launchView(final String[] args) {
		launch(args);
	}
}
