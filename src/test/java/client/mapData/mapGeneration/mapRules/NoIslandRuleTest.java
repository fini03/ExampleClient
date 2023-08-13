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

public class NoIslandRuleTest {
	@Test
	public void GameMapWithAnIsland_ExpectedFalse() {
		final NoIslandRule rule = new NoIslandRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		
		halfMap.replace(new Coordinate(4, 1), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(3, 2), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(4, 3), new ClientTile(EClientTerrain.WATER));
		halfMap.replace(new Coordinate(5, 2), new ClientTile(EClientTerrain.WATER));

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}
	
	@Test
	public void GameMapWithOutAnIsland_ExpectedTrue() {
		final NoIslandRule rule = new NoIslandRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		
		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(true)));
	}
}
