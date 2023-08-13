package client.mapData.mapGeneration.mapRules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.junit.Test;

import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapGeneration.MapGeneratorHelperForTests;

public class TilesLimitRuleTest {
	@Test
	public void GameMapWithEnoughMapTiles_ExpectedTrue() {
		final TilesLimitRule rule = new TilesLimitRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		Map<Coordinate, ClientTile> halfMap = map.getMap();

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(true)));
	}
	
	@Test
	public void GameMapWithNotEnoughMapTiles_ExpectedFalse() {
		final TilesLimitRule rule = new TilesLimitRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		halfMap.remove(new Coordinate(0, 0));

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}
}
