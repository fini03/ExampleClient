package client.mapData.mapGeneration;

import java.util.HashMap;
import java.util.Map;

import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;

public class MapGeneratorHelperForTests {
	public static ClientMap generateFullMap() {
		final Map<Coordinate, ClientTile> fullMap = new HashMap<>();
		for (int yCoordinate = 0; yCoordinate < 10; yCoordinate++) {
			for (int xCoordinate = 0; xCoordinate < 10; xCoordinate++) {
				final Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);
				final ClientTile tile = new ClientTile(EClientTerrain.GRASS);
				fullMap.put(coordinate, tile);
			}
		}

		addCorrectAmountOfMountainTiles(fullMap);
		addCorrectAmountOfWaterTiles(fullMap);

		return new ClientMap(fullMap);
	}
	
	public static ClientMap generateHalfMap() {
		final Map<Coordinate, ClientTile> fullMap = new HashMap<>();
		for (int yCoordinate = 0; yCoordinate < 5; yCoordinate++) {
			for (int xCoordinate = 0; xCoordinate < 10; xCoordinate++) {
				final Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);
				final ClientTile tile = new ClientTile(EClientTerrain.GRASS);
				fullMap.put(coordinate, tile);
			}
		}

		addCorrectAmountOfMountainTilesForHalfMap(fullMap);
		addCorrectAmountOfWaterTilesForHalfMap(fullMap);

		return new ClientMap(fullMap);
	}
	
	public static ClientMap generateMapWithNoGrass() {
		final Map<Coordinate, ClientTile> fullMap = new HashMap<>();
		for (int yCoordinate = 0; yCoordinate < 5; yCoordinate++) {
			for (int xCoordinate = 0; xCoordinate < 10; xCoordinate++) {
				final Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);
				final ClientTile tile = new ClientTile(EClientTerrain.MOUNTAIN);
				fullMap.put(coordinate, tile);
			}
		}

		addCorrectAmountOfWaterTiles(fullMap);

		return new ClientMap(fullMap);
	}

	private static void addCorrectAmountOfMountainTiles(final Map<Coordinate, ClientTile> map) {
		addCorrectAmountOfMountainTilesForHalfMap(map);

		map.replace(new Coordinate(0, 5), new ClientTile(EClientTerrain.MOUNTAIN));
		map.replace(new Coordinate(2, 4), new ClientTile(EClientTerrain.MOUNTAIN));
		map.replace(new Coordinate(3, 5), new ClientTile(EClientTerrain.MOUNTAIN));
		map.replace(new Coordinate(2, 5), new ClientTile(EClientTerrain.MOUNTAIN));
		map.replace(new Coordinate(0, 6), new ClientTile(EClientTerrain.MOUNTAIN));
		map.replace(new Coordinate(7, 8), new ClientTile(EClientTerrain.MOUNTAIN));
	}
	
	private static void addCorrectAmountOfMountainTilesForHalfMap(final Map<Coordinate, ClientTile> map) {
		map.replace(new Coordinate(0, 1), new ClientTile(EClientTerrain.MOUNTAIN));
		map.replace(new Coordinate(2, 0), new ClientTile(EClientTerrain.MOUNTAIN));
		map.replace(new Coordinate(3, 1), new ClientTile(EClientTerrain.MOUNTAIN));
		map.replace(new Coordinate(2, 1), new ClientTile(EClientTerrain.MOUNTAIN));
		map.replace(new Coordinate(0, 2), new ClientTile(EClientTerrain.MOUNTAIN));
		map.replace(new Coordinate(7, 4), new ClientTile(EClientTerrain.MOUNTAIN));
	}

	private static void addCorrectAmountOfWaterTiles(final Map<Coordinate, ClientTile> map) {
		addCorrectAmountOfWaterTilesForHalfMap(map);

		map.replace(new Coordinate(0, 4), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(5, 4), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(8, 5), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(3, 8), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(8, 7), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(1, 8), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(6, 8), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(9, 5), new ClientTile(EClientTerrain.WATER));
	}

	private static void addCorrectAmountOfWaterTilesForHalfMap(final Map<Coordinate, ClientTile> map) {
		map.replace(new Coordinate(0, 0), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(5, 0), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(8, 1), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(3, 4), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(8, 3), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(1, 4), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(6, 4), new ClientTile(EClientTerrain.WATER));
		map.replace(new Coordinate(9, 1), new ClientTile(EClientTerrain.WATER));
	}

}
