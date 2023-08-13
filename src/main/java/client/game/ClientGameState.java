package client.game;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.KI.KIEnumeration.EClientState;
import client.KI.pathFinding.support.TilesUncoverer;
import client.mapData.ClientMap;
import client.mapData.Coordinate;

public class ClientGameState {
	private final Logger logger = LoggerFactory.getLogger(ClientGameState.class);
	private final String id;
	private final ClientMap map;
	private final boolean hasCollectedTreasure;
	private final EClientState clientState;
	private Optional<Coordinate> enemyPosition = Optional.empty();

	/**
	 * Creating a default gameState for the start of the game
	 */
	public ClientGameState() {
		this.id = "";
		this.map = new ClientMap();
		this.hasCollectedTreasure = false;
		this.clientState = EClientState.MUSTWAIT;
	}
	
	/**
	 * This ctor is used for updating the gameState
	 * @param id The new id of the gameState
	 * @param map The new map of the gameState
	 * @param hasCollectedTreasure The new value if the player
	 * collected the treasure or not
	 * @param clientState The new state of the player
	 */
	public ClientGameState(final String id, final ClientMap map, final boolean hasCollectedTreasure,
			final EClientState clientState) {
		this.id = id;
		this.map = map;
		this.hasCollectedTreasure = hasCollectedTreasure;
		this.clientState = clientState;
	}

	public ClientMap getMap() {
		return map;
	}

	public boolean isHasCollectedTreasure() {
		return hasCollectedTreasure;
	}

	public EClientState getClientState() {
		return clientState;
	}
	
	public String getId() {
		return id;
	}

	public Optional<Coordinate> getEnemyPosition() {
		return enemyPosition;
	}

	public void setEnemyPosition(final Optional<Coordinate> enemyPosition) {
		this.enemyPosition = enemyPosition;
	}

	/**
	 * This method updates the gameState by setting the covered tiles
	 * and the new gameState data
	 * @param newClientGameState The new gameState which hold the updated values
	 * @return The new updating gameState
	 */
	public ClientGameState update(final ClientGameState newClientGameState) {
		logger.trace("ClientGameState is getting updated!");
		final ClientGameState uncoveredUpdatedGameState  = TilesUncoverer.uncoverTiles(map, newClientGameState);
		uncoveredUpdatedGameState.setEnemyPosition(this.enemyPosition);
		
		return uncoveredUpdatedGameState;
	}
	
	/**
	 * We use this to check if the running game has finished or not
	 * @return true if the game is over and false otherwise
	 */
	public boolean hasGameEnded() {
		boolean gameOver = false;
		
		if (clientState.equals(EClientState.WON)) {
			logger.info("The game has ended and you have won"); 
			gameOver = true;
		}
		
		if (clientState.equals(EClientState.LOST)) {
			logger.info("The game has ended and you have lost"); 
			gameOver = true;
		}
		
		return gameOver;
	}
}
