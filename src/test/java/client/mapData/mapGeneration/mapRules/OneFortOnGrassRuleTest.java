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

public class OneFortOnGrassRuleTest {	
	@Test
	public void GameMapWithNoFortPlaced_ExpectedFalse() {
		final OneFortOnGrassRule rule = new OneFortOnGrassRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}	
	
	@Test
	public void GameMapWithFortPlacedOnWaterTile_ExpectedFalse() {
		final OneFortOnGrassRule rule = new OneFortOnGrassRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		halfMap.get(new Coordinate(0, 0)).placeOwnFort();

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}
	
	@Test
	public void GameMapWithCorrectFortPlacedOnGrassTile_ExpectedTrue() {
		final OneFortOnGrassRule rule = new OneFortOnGrassRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		halfMap.get(new Coordinate(1, 2)).placeOwnFort();

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(true)));
	}	
}
