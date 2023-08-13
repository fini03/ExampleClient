package client.mapData.mapGeneration.mapRules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.junit.Test;

import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientTerrain;
import client.mapData.mapGeneration.MapGeneratorHelperForTests;

public class TerrainLimitRuleTest {	
	@Test
	public void GameMapWithNotEnoughGrassTiles_ExpectedFalse() {
		final TerrainLimitRule rule = new TerrainLimitRule();
		final ClientMap map = MapGeneratorHelperForTests.generateMapWithNoGrass();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}
	
	@Test
	public void GameMapWithNotEnoughMoutainTiles_ExpectedFalse() {
		final TerrainLimitRule rule = new TerrainLimitRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		Map<Coordinate, ClientTile> halfMap = map.getMap();
		halfMap.replace(new Coordinate(0, 1), new ClientTile(EClientTerrain.GRASS));
		halfMap.replace(new Coordinate(2, 0), new ClientTile(EClientTerrain.GRASS));

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}
	
	@Test
	public void GameMapWithNotEnoughWaterTiles_ExpectedFalse() {
		final TerrainLimitRule rule = new TerrainLimitRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		halfMap.replace(new Coordinate(0, 0), new ClientTile(EClientTerrain.GRASS));
		halfMap.replace(new Coordinate(5, 0), new ClientTile(EClientTerrain.GRASS));

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}
}
