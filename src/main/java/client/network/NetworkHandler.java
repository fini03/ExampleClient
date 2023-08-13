package client.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import client.KI.KIEnumeration.EClientMove;
import client.exceptions.CantRegisterPlayerException;
import client.game.ClientGameState;
import client.mapData.ClientMap;
import client.network.converter.NetworkConverter;
import messagesbase.ResponseEnvelope;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.ERequestState;
import messagesbase.messagesfromclient.PlayerMove;
import messagesbase.messagesfromclient.PlayerRegistration;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.GameState;
import messagesbase.messagesfromserver.PlayerState;
import reactor.core.publisher.Mono;

/**
 * This class mainly communicates with the server
 * @author
 */
public class NetworkHandler {
	private final Logger logger = LoggerFactory.getLogger(NetworkHandler.class);
	private final WebClient baseWebClient;
	private String playerID;
	private final String gameID;

	public NetworkHandler(final String serverBaseUrl, final String gameID) {
		this.gameID = gameID;
		this.baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();
	}

	/**
	 * Registers a player for the game with the provided game ID. Throws a 
	 * CantRegisterPlayerException if there was an error during the registration process
	 * 
	 * @throws CantRegisterPlayerException If there was an error during the registration process
	 */
	public void registerPlayer() throws CantRegisterPlayerException {
		logger.debug("Registering for the game with the following GameID: {}", gameID);
		final PlayerRegistration playerReg = new PlayerRegistration("First name", "Last name", "u:account");

		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameID + "/players")
				.body(BodyInserters.fromValue(playerReg)).retrieve().bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

		if (resultReg.getState() == ERequestState.Error) {
			logger.error("Client error during registration, errormessage: {}", resultReg.getExceptionMessage());
			throw new CantRegisterPlayerException("Error during registration!");
		}
		this.playerID = resultReg.getData().get().getUniquePlayerID();
		logger.info("Registeration completed and my player ID is: {}", playerID);
	}
	
	/**
	 * Checks if it's currently the player's turn in the game
	 * 
	 * @return true if it's the player's turn and they must act, false otherwise
	 */
	public boolean checkIfItsMyTurn() {
		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccessState = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameID + "/states/" + playerID).retrieve().bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<GameState> resultReg = webAccessState.block();
		if (resultReg.getState() == ERequestState.Error) {
			logger.error("Client error during turn checkup, errormessage: {}", resultReg.getExceptionMessage());
		}

		PlayerState playerState = NetworkConverter.getClientState(resultReg.getData().get().getPlayers(), playerID);
		logger.debug("The following player with the ID {} is allowed to act!", playerID);
		
		return playerState.getState().equals(EPlayerGameState.MustAct);
	}
	
	/**
	 * Sends the provided ClientMap to the server as a HalfMap
	 * 
	 * @param map the ClientMap to send to the server
	 */
	public void sendClientMap(final ClientMap map) {
		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccessMap = baseWebClient.method(HttpMethod.POST).uri("/" + gameID + "/halfmaps")
				.body(BodyInserters.fromValue(NetworkConverter.toServerPlayerHalfMap(playerID, map.getMap())))
				.retrieve().bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<FullMap> resultReg = webAccessMap.block();

		if (resultReg.getState() == ERequestState.Error) {
			logger.error("Client error during sending map, errormessage: {}", resultReg.getExceptionMessage());
		}
		
		logger.info("Map was sent successfully!");
	}
	
	/**
	 * Retrieves the current ClientGameState for the player
	 * 
	 * @return the current ClientGameState for the player
	 */
	public ClientGameState getClientGameState() {

		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameID + "/states/" + playerID).retrieve().bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<GameState> resultReg = webAccess.block();

		if (resultReg.getState() == ERequestState.Error) {
			logger.warn("Client error during gameState request, errormessage: {}", resultReg.getExceptionMessage());
		}

		ClientGameState newClientGameState = NetworkConverter.toClientGameState(resultReg.getData(), playerID);
		
		logger.info("The GameState was recieved successfully!");
		return newClientGameState;
	}
	
	/**
	 * Sends the provided client move to the server
	 * 
	 * @param move the EClientMove to send to the server
	 */
	public void sendMove(final EClientMove move) {

		final PlayerMove playerMove = NetworkConverter.toServerPlayerMove(playerID, move);

		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameID + "/moves")
				.body(BodyInserters.fromValue(playerMove)).retrieve().bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope<?> resultReg = webAccess.block();

		if (resultReg.getState() == ERequestState.Error) {
			logger.warn("Client error during sending move, errormessage: {}", resultReg.getExceptionMessage());
		}
		
		logger.debug("Move was sent successfully!");
	}
}
