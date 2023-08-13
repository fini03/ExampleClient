package client.network.converter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import client.KI.KIEnumeration.EClientMove;
import client.KI.KIEnumeration.EClientState;
import client.game.ClientGameState;
import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientFortState;
import client.mapData.mapEnumeration.EClientPositionState;
import client.mapData.mapEnumeration.EClientTerrain;
import client.mapData.mapEnumeration.EClientTreasureState;
import messagesbase.messagesfromclient.EMove;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromclient.PlayerMove;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.FullMapNode;
import messagesbase.messagesfromserver.GameState;
import messagesbase.messagesfromserver.PlayerState;
/**
 * This class is a helper class to communicate with the server.
 * It converts the classes between the client and the server.
 * 
 * @author
 */
public class NetworkConverter {
	/**
	 * Returns the {@link PlayerState} object for the player with the given ID from the given set of player states
	 * @param player The she set of {@link PlayerState} objects to search through
	 * @param playerID The ID of the player whose state to retrieve
	 * @return The {@link PlayerState} object for the specified player ID
	 */
	public static PlayerState getClientState(final Set<PlayerState> players, final String playerID) {
		PlayerState playerState = new PlayerState();
		for (final PlayerState state : players) {
			if (state.getUniquePlayerID().toString().equals(playerID)) {
				playerState = state;
			}
		}
		return playerState;
	}

	/**
	 * Converts a half map of client tiles to a {@link PlayerHalfMap} object for the server
	 * @param playerID The ID of the player whose half map is being converted
	 * @param halfMap The map of client tiles to convert
	 * @return A {@link PlayerHalfMap} object representing the converted half map
	 */
	public static PlayerHalfMap toServerPlayerHalfMap(final String playerID, final Map<Coordinate, ClientTile> halfMap) {
		final List<PlayerHalfMapNode> halfMapNode = halfMap.entrySet().stream().map(tile -> {
			final Coordinate coordinate = tile.getKey();
			final int xCoord = coordinate.getX();
			final int yCoord = coordinate.getY();
			final boolean fortPresent = tile.getValue().isFortPresent();
			final ETerrain terrain = DataConverter.toServerETerrain(tile.getValue().getTerrain());
			return new PlayerHalfMapNode(xCoord, yCoord, fortPresent, terrain);
		}).collect(Collectors.toList());

		return new PlayerHalfMap(playerID, halfMapNode);
	}

	/**
	 * Converts a {@link GameState} object to a {@link ClientGameState} object for the client
	 * @param gameState The {@link GameState} object to convert
	 * @param playerID the ID of the client player
	 * @return A {@link ClientGameState} object representing the converted game state
	 */
	public static ClientGameState toClientGameState(final Optional<GameState> gameState, final String playerID) {
		final String id = gameState.get().getGameStateId();
		final PlayerState clientState = getClientState(gameState.get().getPlayers(), playerID);
		final EClientState playerState = DataConverter.toEClientState(clientState.getState());
		final boolean collectedTreasure = clientState.hasCollectedTreasure();
		final ClientMap map = toClientMap(gameState.get().getMap());

		return new ClientGameState(id, map, collectedTreasure, playerState);
	}
	
	/**
	 * Converts a client move to a {@link PlayerMove} object for the server
	 * @param playerID The ID of the player making the move
	 * @param move The client move to convert
	 * @return A {@link PlayerMove} object representing the converted move
	 */
	public static PlayerMove toServerPlayerMove(final String playerID, final EClientMove move) {
		final EMove movement = DataConverter.toServerEMove(move);
		return PlayerMove.of(playerID, movement);
	}
	
	/**
	 * Converts a {@link FullMap} object to a {@link ClientMap} object for the client
	 * @param fullMap The {@link FullMap} object to convert
	 * @return A {@link ClientMap} object representing the converted map
	 */
	private static ClientMap toClientMap(final FullMap fullMap) {
		final Collection<FullMapNode> mapNodes = fullMap.getMapNodes();
		final Map<Coordinate, ClientTile> map = new HashMap<>();

		for (final FullMapNode node : mapNodes) {
			final ClientTile tile = toClientTile(node);
			final int xCoord = node.getX();
			final int yCoord = node.getY();
			final Coordinate coordinate = new Coordinate(xCoord, yCoord);
			map.put(coordinate, tile);
		}

		return new ClientMap(map);
	}

	/**
	 * Converts a {@link FullMapNode} object to a {@link ClientTile} object for the client
	 * @param node The {@link FullMapNode} object to convert
	 * @return A {@link ClientTile} object representing the converted tile
	 */
	private static ClientTile toClientTile(final FullMapNode node) {
		final EClientTerrain terrain = DataConverter.toEClientTerrain(node.getTerrain());
		final EClientTreasureState treasureState = DataConverter.toEClientTreasureState(node.getTreasureState());
		final EClientPositionState positionState = DataConverter.toEClientPositionState(node.getPlayerPositionState());
		final EClientFortState fortState = DataConverter.toEClientFortState(node.getFortState());

		return new ClientTile(terrain, treasureState, positionState, fortState);
	}
}
