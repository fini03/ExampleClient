package client.KI.pathFinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import client.KI.pathFinding.support.SurroundingCoordsCalculator;
import client.KI.pathFinding.support.WaterFilter;
import client.mapData.ClientTile;
import client.mapData.Coordinate;

/**
 * This class implements the dijkstra algorithm to calculate the
 * shortest path giving the map, start and target coordiante
 * 
 * @author
 */
public class DijkstraAlgorithm {
	/*
	 * TAKEN FROM <4>
	 * Source[1]: https://www.baeldung.com/java-dijkstra
	 * Implementation of dijkstra algorithm in java
	 * Source[2]: https://www.youtube.com/watch?v=KiOso3VE-vI
	 * Explanation of how the dijkstra algorithm works 
	 * Please note: both resources were needed and taken as an inspiration for the implementation of dijkstra
	 */

	/*
	 * TAKEN FROM START <4>
	 */
	
	/**
	 * Finds the shortest path from a starting coordinate to a target coordinate using Dijkstra's algorithm
	 *
	 * @param graph The graph to search for the path
	 * @param start The starting coordinate
	 * @param target The target coordinate
	 * @return A list of coordinates representing the shortest path from start to target
	 */
	public static List<Coordinate> findPath(final Map<Coordinate, ClientTile> graph, final Coordinate start, final Coordinate target) {
		final Map<Coordinate, DijkstraValues> tableValues = new HashMap<>();
		final Set<Coordinate> visitedPlaces = new HashSet<>();
		final Set<Coordinate> evalutionPlaces = new HashSet<>();
		final Map<Coordinate, ClientTile> graphWithoutWaterTiles = WaterFilter.getMapWithNoWaterTiles(graph);

		for (final Coordinate coordinate : graphWithoutWaterTiles.keySet()) {
			final Set<Coordinate> adjacent = getAdjacentCoordinates(graphWithoutWaterTiles, coordinate);
			final DijkstraValues nodeInfo = new DijkstraValues(new ArrayList<>(adjacent));
			final ClientTile node = graphWithoutWaterTiles.get(coordinate);
			
			//if (node.getTerrain().equals(EClientTerrain.WATER)) { //filtering water tiles
				//continue;
			//}
			
			//tile cost of uncovered tile is higher by one
			nodeInfo.evaluateAdjacentCost(graphWithoutWaterTiles, node.getTerrain().getMoveCost() + (node.isCovered() ? 0 : 1));
			tableValues.put(coordinate, nodeInfo);
		}
		
		tableValues.get(start).setCost(0); // cost of start position is 0
		evalutionPlaces.add(start);
		
		while (evalutionPlaces.size() != 0) {
			final Coordinate current = getClosestNode(evalutionPlaces, tableValues);
			evalutionPlaces.remove(current);
			
			for (int adjacentIndex = 0; adjacentIndex < tableValues.get(current).getAdjacentCoordinates().size(); adjacentIndex++) {
				final Coordinate adjacentCoordinate = tableValues.get(current).getAdjacentCoordinates().get(adjacentIndex);
				final int adjacentCost = tableValues.get(current).getAdjacentCost().get(adjacentIndex);
				
				if (!visitedPlaces.contains(adjacentCoordinate)) {
					final int cost = tableValues.get(current).getCost();
					
					if (cost + adjacentCost < tableValues.get(adjacentCoordinate).getCost()) {
						tableValues.get(adjacentCoordinate).setCost(cost + adjacentCost);
						tableValues.get(adjacentCoordinate).setPredecessor(current);
						evalutionPlaces.add(adjacentCoordinate);
					}
				}
			}
			
			visitedPlaces.add(current);
		}

		return getShortestPath(tableValues, start, target);
	}
	
	/*
	 * TAKEN FROM END <4>
	 */
	
	/**
	 * Gets the shortest path from start to target using the results from the Dijkstra's algorithm
	 *
	 * @param tableValues The Dijkstra's algorithm result table
	 * @param start The starting coordinate
	 * @param target The target coordinate
	 * @return A list of coordinates representing the shortest path from start to target
	 */
	private static List<Coordinate> getShortestPath(final Map<Coordinate, DijkstraValues> tableValues, final Coordinate start,
			Coordinate target) {
		final List<Coordinate> path = new ArrayList<>();
		tableValues.get(start).setPredecessor(null);
		
		while (target != null) {
			path.add(target);
			target = tableValues.get(target).getPredecessor();
		}
		
		Collections.reverse(path);
		return path;
	}

	/**
	 * Gets the closest and cheapest node to a set of coordinates
	 *
	 * @param coordinates The set of coordinates to search for the closest and cheapest node
	 * @param tableValues The Dijkstra's algorithm result table
	 * @return The coordinate that is closest and cheapest to the set of coordinates
	 */
	private static Coordinate getClosestNode(final Set<Coordinate> coordinates, final Map<Coordinate, DijkstraValues> tableValues) {
	    return coordinates.stream()
	            .min(Comparator.comparingInt(coordinate -> tableValues.get(coordinate).getCost()))
	            .orElseThrow(); // or throw an exception if the set is empty
	}

	/**
	 * Gets the adjacent coordinates of a given coordinate from a graph
	 *
	 * @param graph The graph to search for the adjacent coordinates
	 * @param start The starting coordinate to find the adjacent coordinates for
	 * @return A set of coordinates that are adjacent to the starting coordinate in the graph
	 */
	public static Set<Coordinate> getAdjacentCoordinates(final Map<Coordinate, ClientTile> graph, final Coordinate start) {
		final Map<Coordinate, ClientTile> graphWithoutWaterTiles = WaterFilter.getMapWithNoWaterTiles(graph);
		final Set<Coordinate> adjacentCoordinates = new HashSet<>();
		final Collection<Coordinate> adjacentCoords = SurroundingCoordsCalculator.getAdjacentCoordinates(graphWithoutWaterTiles.keySet(), start);
		
		for (final Coordinate coordinate : adjacentCoords) {
			if (graphWithoutWaterTiles.containsKey(coordinate)) {
				adjacentCoordinates.add(coordinate);
			}
		}
		return adjacentCoordinates;
	}
}
