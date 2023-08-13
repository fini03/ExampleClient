package client.KI.pathFinding.support;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import client.KI.KIEnumeration.EClientMove;
import client.exceptions.NotAdjacentCoordinatesException;
import client.mapData.Coordinate;

public class CoordinateDirectionsToMoveTest {
	@Test
	public void GivenTwoCoordinate_TransformPathCoordinateToMoves_ExceptedNotAdjacentCoordinateException() {
		//arrange
		Coordinate start = new Coordinate(0, 0);
		Coordinate target = new Coordinate(2, 0);
		//act
		Executable testCode = () -> {
			CoordinateDirectionsToMove.transformCoordinateDirectionToMove(start, target);
		};
		//assert
		Assertions.assertThrows(NotAdjacentCoordinatesException.class, testCode);
	}

	@ParameterizedTest
	@MethodSource("provideDataForTransformPathCoordinateToMoves")
	public void GivenTwoCoordinate_TransformPathCoordinateToMoves_ExceptedCorrectMoveMatch(final Coordinate start, final Coordinate target, final EClientMove expected) {
		//arrange
		
		//act
		final EClientMove move = CoordinateDirectionsToMove.transformCoordinateDirectionToMove(start, target);
		//assert
		assertThat(move, is(equalTo(expected)));
		Assertions.assertEquals(expected, move);
	}
	
	@MethodSource("provideDataForTransformPathCoordinateToMoves")
	public static Stream<Arguments> provideDataForTransformPathCoordinateToMoves() {
		return Stream.of(Arguments.of(new Coordinate(5,5), new Coordinate(5,6), EClientMove.DOWN),
				Arguments.of(new Coordinate(5,5), new Coordinate(5,4), EClientMove.UP),
				Arguments.of(new Coordinate(5,5), new Coordinate(4,5), EClientMove.LEFT),
				Arguments.of(new Coordinate(5,5), new Coordinate(6,5), EClientMove.RIGHT),
				Arguments.of(new Coordinate(6,5), new Coordinate(6,6), EClientMove.DOWN),
				Arguments.of(new Coordinate(6,5), new Coordinate(6,4), EClientMove.UP),
				Arguments.of(new Coordinate(6,5), new Coordinate(5,5), EClientMove.LEFT),
				Arguments.of(new Coordinate(6,5), new Coordinate(7,5), EClientMove.RIGHT),
				Arguments.of(new Coordinate(7,5), new Coordinate(7,6), EClientMove.DOWN),
				Arguments.of(new Coordinate(7,5), new Coordinate(7,4), EClientMove.UP),
				Arguments.of(new Coordinate(7,5), new Coordinate(6,5), EClientMove.LEFT),
				Arguments.of(new Coordinate(7,5), new Coordinate(8,5), EClientMove.RIGHT)
				);
	}
}
