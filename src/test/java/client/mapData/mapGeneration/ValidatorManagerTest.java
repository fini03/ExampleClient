package client.mapData.mapGeneration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.junit.Test;

import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapEnumeration.EClientFortState;
import client.mapData.mapEnumeration.EClientPositionState;
import client.mapData.mapEnumeration.EClientTerrain;
import client.mapData.mapEnumeration.EClientTreasureState;

public class ValidatorManagerTest {
	@Test
	public void GameMapWithAllCorrectRulesImplemented_ExpectedTrue() {
		final ClientMap map = MapGeneratorHelperForTests.generateHalfMap();
		final Map<Coordinate, ClientTile> halfMap = map.getMap();

		halfMap.replace(new Coordinate(1, 2),
				new ClientTile(EClientTerrain.GRASS, EClientTreasureState.NoOrUnknownTreasureState,
						EClientPositionState.MyPlayerPosition, EClientFortState.MyFortPresent));

		boolean result = ValidatorManager.validateMap(halfMap);

		assertThat(result, is(equalTo(true)));
	}
}
