package client.game;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import client.KI.KIEnumeration.EClientMove;
import client.KI.KIEnumeration.EClientState;
import client.KI.pathFinding.PathFinder;
import client.mapData.ClientMap;
import client.mapData.mapGeneration.MapGeneratorHelperForTests;
import client.network.NetworkHandler;

public class GameControllerTest {
	final NetworkHandler networkHandler = mock(NetworkHandler.class);
	final GameController gameController = new GameController(networkHandler);
	
	@Test
	void GivenASimulatedCorrectGame_CheckHowManyMethodsCallsFromNetworkHandler_ExpectedCallsFromNetworkHandlerMatch() {
		// arrange
		final ClientMap map = MapGeneratorHelperForTests.generateFullMap();
		final ClientGameState clientGameState = new ClientGameState("12345", map, true, EClientState.MUSTACT);
		final ClientGameState newClientGameState = new ClientGameState("54321", map, true, EClientState.LOST);
		final PathFinder pathFinding = mock(PathFinder.class);
		final GameController gameController = new GameController(networkHandler, pathFinding);
		
		when(pathFinding.getNextMove(isNotNull())).thenReturn(EClientMove.DOWN);		
		when(networkHandler.checkIfItsMyTurn()).thenReturn(true);
		when(networkHandler.getClientGameState()).thenReturn(clientGameState, newClientGameState); 
		
		gameController.run();
		
		verify(networkHandler, times(2)).getClientGameState();
		verify(networkHandler, times(1)).sendClientMap(isNotNull());
		verify(networkHandler, times(2)).getClientGameState();
	}
}