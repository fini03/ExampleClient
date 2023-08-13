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

public class WaterCountAtBorderRuleTest {	
	@Test
	public void GameMapWithToMuchWaterOnUpperBorder_ExpectedFalse() {
		final WaterCountAtBorderRule rule = new WaterCountAtBorderRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		
		halfMap.replace(new Coordinate(1, 0), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(2, 0), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(3, 0), new ClientTile(EClientTerrain.WATER));

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}

	@Test
	public void GameMapWithToMuchWaterOnLowerBorder_ExpectedFalse() {
		final WaterCountAtBorderRule rule = new WaterCountAtBorderRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		
		halfMap.replace(new Coordinate(0, 4), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(1, 4), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(2, 4), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(3, 4), new ClientTile(EClientTerrain.WATER));

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}

	@Test
	public void GameMapWithToMuchWaterOnLeftBorder_ExpectedFalse() {
		final WaterCountAtBorderRule rule = new WaterCountAtBorderRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		
		halfMap.replace(new Coordinate(0, 1), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(0, 2), new ClientTile(EClientTerrain.WATER));

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}

	@Test
	public void GameMapWithToMuchWaterOnRightBorder_ExpectedFalse() {
		final WaterCountAtBorderRule rule = new WaterCountAtBorderRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		
		halfMap.replace(new Coordinate(9, 0), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(9, 1), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(9, 2), new ClientTile(EClientTerrain.WATER));

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}
}
