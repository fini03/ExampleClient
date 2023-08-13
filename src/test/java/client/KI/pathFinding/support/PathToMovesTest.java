package client.KI.pathFinding.support;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import client.KI.KIEnumeration.EClientMove;
import client.mapData.ClientMap;
import client.mapData.ClientTile;
import client.mapData.Coordinate;
import client.mapData.mapGeneration.MapGeneratorHelperForTests;

public class PathToMovesTest {
	@ParameterizedTest
	@MethodSource("provideDataForTransformPathToMoves")
	public void GivenMapAndAListOfCoordinate_TransformPathToMoves_ExceptedMovesMatch(final Map<Coordinate, ClientTile> map,
			final List<Coordinate> path, final List<EClientMove> expected) {
		// arrange

		// act
		final List<EClientMove> listOfMoves = PathToMoves.transformPathToMoves(path);
		// assert
		assertThat(listOfMoves, is(equalTo(expected)));
		Assertions.assertEquals(expected, listOfMoves);
	}

	@MethodSource("provideDataForTransformPathToMoves")
	public static Stream<Arguments> provideDataForTransformPathToMoves() {
		final ClientMap fullMap = generateMap();
		final List<Coordinate> path = generatePath();
		final List<EClientMove> expected = transformPathtoMoves1(path);
		
		final ClientMap secondFullMap = generateMap();
		final List<Coordinate> secondPath = generateSecondPath();
		final List<EClientMove> secondExpected = transformSecondPathtoMoves(path);
		
		return Stream.of(Arguments.of(fullMap.getMap(), path, expected),
				Arguments.of(secondFullMap.getMap(), secondPath, secondExpected));
	}

	// helper methods

	private static List<EClientMove> transformPathtoMoves1(final List<Coordinate> path) {
		final List<EClientMove> expected = new ArrayList<>();
		
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.DOWN);
		expected.add(EClientMove.DOWN);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.DOWN);
		expected.add(EClientMove.DOWN);
		
		return expected;
	}

	private static List<EClientMove> transformSecondPathtoMoves(final List<Coordinate> path) {
		final List<EClientMove> expected = new ArrayList<>();
		
		expected.add(EClientMove.DOWN);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.DOWN);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.DOWN);
		expected.add(EClientMove.DOWN);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.RIGHT);
		expected.add(EClientMove.UP);
		expected.add(EClientMove.UP);
		expected.add(EClientMove.LEFT);
		expected.add(EClientMove.LEFT);
		
		return expected;
	}

	private static List<Coordinate> generatePath() {
		final List<Coordinate> path = new ArrayList<>();
		
		path.add(new Coordinate(1, 0));
		path.add(new Coordinate(2, 0));
		path.add(new Coordinate(3, 0));
		path.add(new Coordinate(4, 0));
		path.add(new Coordinate(4, 1));
		path.add(new Coordinate(4, 2));
		path.add(new Coordinate(5, 2));
		path.add(new Coordinate(6, 2));
		path.add(new Coordinate(7, 2));
		path.add(new Coordinate(8, 2));
		path.add(new Coordinate(9, 2));
		path.add(new Coordinate(9, 3));
		path.add(new Coordinate(9, 4));
		
		return path;
	}

	private static List<Coordinate> generateSecondPath() {
		final List<Coordinate> path = new ArrayList<>();
		
		path.add(new Coordinate(1, 0));
		path.add(new Coordinate(1, 1));
		path.add(new Coordinate(2, 1));
		path.add(new Coordinate(2, 2));
		path.add(new Coordinate(3, 2));
		path.add(new Coordinate(3, 3));
		path.add(new Coordinate(3, 4));
		path.add(new Coordinate(4, 4));
		path.add(new Coordinate(5, 4));
		path.add(new Coordinate(5, 3));
		path.add(new Coordinate(5, 2));
		path.add(new Coordinate(4, 2));
		path.add(new Coordinate(3, 2));
		
		return path;
	}

	private static ClientMap generateMap() {
		final Map<Coordinate, ClientTile> map = MapGeneratorHelperForTests.generateFullMap().getMap();
		map.get(new Coordinate(0, 0)).placeOwnFort();
		
		return new ClientMap(map);
	}
}
