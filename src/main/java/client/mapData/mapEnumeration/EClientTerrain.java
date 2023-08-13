package client.mapData.mapEnumeration;

/**
 * An enum that represents the different types of terrain in the client-side game.
 * Each terrain has a movement cost associated with it.
 * 
 * @author
*/
public enum EClientTerrain {
	GRASS(1),
	WATER(100000), // So that we make sure that we never select a water tile since the cost is so high
	MOUNTAIN(2);
	
	private final int moveCost;
	
	private EClientTerrain(int moveCost) {
		this.moveCost = moveCost;
	}
	
	public int getMoveCost() {
		return moveCost;
	}
}
