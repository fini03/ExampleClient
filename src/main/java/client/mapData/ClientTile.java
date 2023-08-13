package client.mapData;

import client.mapData.mapEnumeration.EClientFortState;
import client.mapData.mapEnumeration.EClientPositionState;
import client.mapData.mapEnumeration.EClientTerrain;
import client.mapData.mapEnumeration.EClientTreasureState;

/**
 * Represents a tile on the client's map, containing information about the
 * terrain, the state of the treasure, player position, fort state, and whether
 * it is covered or not.
 * 
 * @author
 */
public class ClientTile {
	private final EClientTerrain terrain;
	private EClientTreasureState clientTreasureState;
	private final EClientPositionState clientPositionState;
	private EClientFortState clientFortState;
	private boolean covered = true;

	/**
	 * Creates a new client tile with the specified terrain and default treasure,
	 * player position, and fort states.
	 * 
	 * @param terrain The terrain of the tile
	 */
	public ClientTile(final EClientTerrain terrain) {
		this.terrain = terrain;
		this.clientTreasureState = EClientTreasureState.NoOrUnknownTreasureState;
		this.clientPositionState = EClientPositionState.NoPlayerPresent;
		this.clientFortState = EClientFortState.NoOrUnknownFortState;
	}

	/**
	 * Creates a new client tile with the specified terrain, treasure state, player
	 * position state, and fort state.
	 * 
	 * @param terrain The terrain of the tile
	 * @param treasureState The state of the treasure on the tile
	 * @param positionState The state of the player position on the tile
	 * @param fortState The state of the fort on the tile
	 */
	public ClientTile(final EClientTerrain terrain, final EClientTreasureState treasureState,
			final EClientPositionState positionState, final EClientFortState fortState) {
		this.terrain = terrain;
		this.clientTreasureState = treasureState;
		this.clientPositionState = positionState;
		this.clientFortState = fortState;
	}

	public EClientTerrain getTerrain() {
		return terrain;
	}

	public EClientFortState getFortState() {
		return clientFortState;
	}
	
	/**
	 * Places the player's own fort on the tile.
    */
	public void placeOwnFort() {
		clientFortState = EClientFortState.MyFortPresent;
	}
	
	/**
	 * Returns whether the tile has a fort present or not.
	 * @return true if the tile has a fort present, false otherwise
    */
	public boolean isFortPresent() {
		return clientFortState == EClientFortState.MyFortPresent;
	}
	
	/**
	 * Returns whether the tile has a treasure present or not.
	 * @return true if the tile has a treasure present, false otherwise
    */
	public boolean isTreasurePresent() {
		return clientTreasureState == EClientTreasureState.MyTreasureIsPresent;
	}
	
	/**
	 * Returns whether the tile has a player present or not.
	 * @return true if the tile has a player present, false otherwise
    */
	public boolean isPlayerPresent() {
		return (clientPositionState == EClientPositionState.MyPlayerPosition
				|| clientPositionState == EClientPositionState.BothPlayerPosition);
	}
	
	public boolean isEnemyPresent() {
		return (clientPositionState == EClientPositionState.EnemyPlayerPosition
				|| clientPositionState == EClientPositionState.BothPlayerPosition);
	}

	public EClientTreasureState getClientTreasureState() {
		return clientTreasureState;
	}

	public EClientPositionState getClientPositionState() {
		return clientPositionState;
	}

	public EClientFortState getClientFortState() {
		return clientFortState;
	}
	
	/**
	 * Uncover tile if player has already visited it
	 */
	public void uncoverTile() {
		this.covered = false;
	}

	public boolean isCovered() {
		return covered;
	}
}
