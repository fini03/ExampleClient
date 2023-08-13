package client.network.converter;

import client.KI.KIEnumeration.EClientMove;
import client.KI.KIEnumeration.EClientState;
import client.exceptions.CannotConvertEnumException;
import client.mapData.mapEnumeration.EClientFortState;
import client.mapData.mapEnumeration.EClientPositionState;
import client.mapData.mapEnumeration.EClientTerrain;
import client.mapData.mapEnumeration.EClientTreasureState;
import messagesbase.messagesfromclient.EMove;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromserver.EFortState;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.EPlayerPositionState;
import messagesbase.messagesfromserver.ETreasureState;

/**
 * This Class is a helper communcation class with the Server.
 * It converts the local data to server data.
 * @author
 *
 */
public class DataConverter {
	/**
	 * Converts the server playerState to the local clientState
	 * @param playerState The server playerState
	 * @return Local clientState
	 */
	public static EClientState toEClientState(final EPlayerGameState playerState) {
		switch (playerState) {
			case Lost:
				return EClientState.LOST;
			case Won:
				return EClientState.WON;
			case MustAct:
				return EClientState.MUSTACT;
			case MustWait:
				return EClientState.MUSTWAIT;
			default:
				throw new CannotConvertEnumException("Unexpected value during convertion: " + playerState);
		}
	}
	
	/**
	 * Converts the server positionState to the local positionState
	 * @param positionState The server positionState
	 * @return Local positionState
	 */
	public static EClientPositionState toEClientPositionState(final EPlayerPositionState positionState) {
		switch (positionState) {
			case MyPlayerPosition:
				return EClientPositionState.MyPlayerPosition;
			case EnemyPlayerPosition:
				return EClientPositionState.EnemyPlayerPosition;
			case BothPlayerPosition:
				return EClientPositionState.BothPlayerPosition;
			case NoPlayerPresent:
				return EClientPositionState.NoPlayerPresent;
			default:
				throw new CannotConvertEnumException("Unexpected value during convertion: " + positionState);
		}
	}
	
	/**
	 * Converts the server treasureState to the local treasureState
	 * @param treasureState The server treasureState
	 * @return Local treasureState
	 */
	public static EClientTreasureState toEClientTreasureState(final ETreasureState treasureState) {
		switch (treasureState) {
			case MyTreasureIsPresent:
				return EClientTreasureState.MyTreasureIsPresent;
			case NoOrUnknownTreasureState:
				return EClientTreasureState.NoOrUnknownTreasureState;
			default:
				throw new CannotConvertEnumException("Unexpected value during convertion: " + treasureState);
			}
	}
	
	/**
	 * Converts the server fortState to the local fortState
	 * @param fortState The server fortState
	 * @return Local fortState
	 */
	public static EClientFortState toEClientFortState(final EFortState fortState) {
		switch (fortState) {
			case EnemyFortPresent:
				return EClientFortState.EnemyFortPresent;
			case MyFortPresent:
				return EClientFortState.MyFortPresent;
			case NoOrUnknownFortState:
				return EClientFortState.NoOrUnknownFortState;
			default:
				throw new CannotConvertEnumException("Unexpected value during convertion: " + fortState);
			}
	}
	
	/**
	 * Converts the server terrain to the local terrain
	 * @param terrain The server terrain
	 * @return Local terrain
	 */
	public static EClientTerrain toEClientTerrain(final ETerrain terrain) {
		switch (terrain) {
			case Grass:
				return EClientTerrain.GRASS;
			case Water:
				return EClientTerrain.WATER;
			case Mountain:
				return EClientTerrain.MOUNTAIN;
			default:
				throw new CannotConvertEnumException("Unexpected value during convertion: " + terrain);
		}
	}
	
	/**
	 * Converts the local terrain to the server terrain
	 * @param terrain The local terrain
	 * @return Server terrain
	 */
	public static ETerrain toServerETerrain(final EClientTerrain terrain) {
		switch (terrain) {
			case GRASS:
				return ETerrain.Grass;
			case WATER:
				return ETerrain.Water;
			case MOUNTAIN:
				return ETerrain.Mountain;
			}
			throw new CannotConvertEnumException("Unexpected value during convertion: " + terrain);
	}
	
	/**
	 * Converts the local move to the server move
	 * @param move The local move
	 * @return Server move
	 */
	public static EMove toServerEMove(final EClientMove move) {
		switch (move) {
			case UP: 
				return EMove.Up;
			case DOWN:
				return EMove.Down;
			case LEFT:
				return EMove.Left;
			case RIGHT:
				return EMove.Right;
			default:
				throw new CannotConvertEnumException("Unexpected value during convertion: " + move);
		}
	}
}
