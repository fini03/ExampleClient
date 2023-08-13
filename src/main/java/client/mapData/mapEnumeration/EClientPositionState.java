package client.mapData.mapEnumeration;

/**
 * Enumeration representing the different states of a client position,
 * which can be both player position, enemy player position,
 * player with treasure position or no player present.
*/
public enum EClientPositionState {
	BothPlayerPosition,
	EnemyPlayerPosition,
	MyPlayerPosition,
	NoPlayerPresent;
}
