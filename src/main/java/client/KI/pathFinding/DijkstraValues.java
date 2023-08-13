package client.KI.pathFinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.mapData.ClientTile;
import client.mapData.Coordinate;

/**
 * This class represents the Dijkstra values associated with a single coordinate
 * in the Dijkstra algorithm
 * 
 * @author
 */
public class DijkstraValues {
	private Coordinate predecessor;
	private int cost = Integer.MAX_VALUE;
	private final List<Coordinate> adjacentCoordinates;
	private final List<Integer> adjacentCost;

	public DijkstraValues(final List<Coordinate> adjacentCoordinates) {
		this.adjacentCoordinates = adjacentCoordinates;
		this.adjacentCost = new ArrayList<>();
	}

	/**
	 * Evaluates the costs of moving from the current coordinate to its adjacent
	 * coordinates and stores the costs in the adjacentCost list
	 * 
	 * @param map The map containing the terrain information of each coordinate
	 * @param cost The cost of the current path to the current coordinate
	 */
	public void evaluateAdjacentCost(final Map<Coordinate, ClientTile> map, final int cost) {
		for (final Coordinate coordinate : adjacentCoordinates) {
			adjacentCost.add(map.get(coordinate).getTerrain().getMoveCost() + cost);
		}
	}

	public List<Coordinate> getAdjacentCoordinates() {
		return adjacentCoordinates;
	}

	public Coordinate getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(final Coordinate predecessor) {
		this.predecessor = predecessor;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(final int cost) {
		this.cost = cost;
	}

	public List<Integer> getAdjacentCost() {
		return adjacentCost;
	}
}
