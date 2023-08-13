package client.game;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.KI.KIEnumeration.EClientMove;
import client.KI.KIEnumeration.EClientState;
import client.KI.pathFinding.PathFinder;
import client.KI.pathFinding.support.PlayerFinder;
import client.exceptions.CantRegisterPlayerException;
import client.mapData.Coordinate;
import client.network.NetworkHandler;

/**
 * We use the GameController to control the game flow and communication between
 * the game model, view, and network handler.
 * 
 * @author
 */
public class GameController implements Runnable {
	private final Logger logger = LoggerFactory.getLogger(GameController.class);
	private final static int turnsNeededToCalculateEnemyFort = 12;
	private final NetworkHandler networkHandler;
	private ClientGameState clientGameState;
	private ClientGameState lastPlayerGameState;
	private final GameModel gameModel;
	private final PathFinder pathFinder;
	private Optional<EClientMove> lastMove;
	private int turn = 0;

	/**
	 * Constructor for GameController class that initializes the NetworkHandler and
	 * the other MVC classes
	 * 
	 * @param serverUrl The server URL used for network communication
	 * @param gameID The game ID used for network communication
	 */
	public GameController(final String serverUrl, final String gameID, final GameModel gameModel) {
		this.networkHandler = new NetworkHandler(serverUrl, gameID);
		this.gameModel = gameModel;
		this.clientGameState = new ClientGameState();
		this.lastPlayerGameState = new ClientGameState();
		this.pathFinder = new PathFinder();
		this.lastMove = Optional.empty();
	}
	
	/**
	 * Constructor for GameController class that initializes the NetworkHandler which
	 * is mainly used for Mocking
	 * 
	 * @param networkHandler The mocked networkHandler
	 */
	public GameController(final NetworkHandler networkHandler) {
		this.networkHandler = networkHandler;
		this.gameModel = new GameModel();
		this.clientGameState = new ClientGameState();
		this.lastPlayerGameState = new ClientGameState();
		this.pathFinder = new PathFinder();
		this.lastMove = Optional.empty();
	}
	
	/**
	 * Constructor for GameController class that initializes the NetworkHandler and
	 * PathFinder which is mainly used for mocking
	 * 
	 * @param networkHandler The mocked networkHandler
	 * @param pathFinder The mocked pathFinder
	 */
	public GameController(final NetworkHandler networkHandler, final PathFinder pathFinder) {
		this.networkHandler = networkHandler;
		this.gameModel = new GameModel();
		this.clientGameState = new ClientGameState();
		this.lastPlayerGameState = new ClientGameState();
		this.pathFinder = pathFinder;
		this.lastMove = Optional.empty();
	}
	
	@Override
	/**
	 * Starts and controls the game flow by updating the gameState 
	 */
	public void run() {
		logger.info("Starting game...");

		setupGame();

		sendMap();

		ClientGameState newClientGameState = new ClientGameState();

		while (!newClientGameState.hasGameEnded()) {
			sleep();

			final ClientGameState updatedClientGameState = networkHandler.getClientGameState();
			newClientGameState = clientGameState.update(updatedClientGameState);

			if (!newClientGameState.getId().equals(clientGameState.getId())) {
				gameModel.setClientGameState(newClientGameState);
				turn++;
			}
			
			if(turn == turnsNeededToCalculateEnemyFort) {
				final Optional<Coordinate> enemyPosition = PlayerFinder.findEnemyPosition(newClientGameState.getMap().getMap());
				
				logger.info("Enemy position after 12 turns is {}", enemyPosition.get().toString());
				
				if(enemyPosition.isPresent()) {
					newClientGameState.setEnemyPosition(enemyPosition);
				}
			}

			if (newClientGameState.getClientState().equals(EClientState.MUSTACT)) {
				lastPlayerGameState = tryPerformPlayerAction(lastPlayerGameState, newClientGameState);
			}
			
			clientGameState = newClientGameState;
			
			logger.info("We are currently on turn {}", turn);
		}

		logger.info("Game finished!");
	}

	/**
	 * Sets up the game by adding the view and registering the players 
	 */
	private void setupGame() {		
		try {
			networkHandler.registerPlayer();
		} catch (CantRegisterPlayerException registerExc) {
			logger.error("Could not register player {}", registerExc.getMessage());
			System.exit(0);
		}
		
		logger.info("Player registered!");
		
		turn++;
		
		logger.info("We are currently on turn {}", turn);
	}
	
	/**
	 * Sends the map to the server if its the player's turn
	 */
	private void sendMap() {
		boolean isMyTurn = false;

		while (!isMyTurn) {
			sleep();
			isMyTurn = networkHandler.checkIfItsMyTurn();
		}

		gameModel.setClientGameState(clientGameState);
		networkHandler.sendClientMap(clientGameState.getMap());
		logger.info("Map sent successfully!");
		
		turn++;
		
		logger.info("We are currently on turn {}", turn);
	}

	/**
	 * Attempts to perform a player action by finding the player's current
	 * position and sending a move to the server.
	 * @param lastPlayerGameState The last gameState of the player
	 * @param newClientGameState The updated gameState of the game
	 * @return The updated gameState after performing a move
	 */
	private ClientGameState tryPerformPlayerAction(final ClientGameState lastPlayerGameState, final ClientGameState newClientGameState) {
		final Optional<Coordinate> oldPlayerCoordinate = PlayerFinder
				.findPlayerPosition(lastPlayerGameState.getMap().getMap());
		final Optional<Coordinate> newPlayerCoordinate = PlayerFinder
				.findPlayerPosition(newClientGameState.getMap().getMap());
		
		if (!oldPlayerCoordinate.equals(newPlayerCoordinate) || lastMove.isEmpty()) {
			this.lastMove = Optional.of(pathFinder.getNextMove(newClientGameState));
		}

		logger.debug("Last move: {}", lastMove.get().name());
		networkHandler.sendMove(lastMove.get());
		
		return newClientGameState;
	}
	
	/**
	 * Let's the client sleep for 400ms
	 */
	private void sleep() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
