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

public class CorrectCoordinatesRuleTest {
	@Test
	public void GameMapWithInvalidXCoordinate_ExpectedFalse() {
		final CorrectCoordinatesRule rule = new CorrectCoordinatesRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		halfMap.put(new Coordinate(-1, 0), new ClientTile(EClientTerrain.WATER));

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}
	
	@Test
	public void GameMapWithInvalidYCoordinate_ExpectedFalse() {
		final CorrectCoordinatesRule rule = new CorrectCoordinatesRule();
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();
		halfMap.put(new Coordinate(5, -19), new ClientTile(EClientTerrain.WATER));

		boolean result = rule.validate(halfMap);
		
		assertThat(result, is(equalTo(false)));
	}
}
