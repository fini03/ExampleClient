package client.KI.pathFinding.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import client.mapData.Coordinate;

public class SurroundingCoordsCalculatorTest {

    private Collection<Coordinate> allCoords;
    private Coordinate center;
    private Collection<Coordinate> expectedAdjacentCoords;
    private Collection<Coordinate> expectedSurroundedCoords;

    @Before
    public void setUp() {
        allCoords = Arrays.asList(
            new Coordinate(1, 1),
            new Coordinate(1, 2),
            new Coordinate(1, 3),
            new Coordinate(2, 1),
            new Coordinate(2, 2),
            new Coordinate(2, 3),
            new Coordinate(3, 1),
            new Coordinate(3, 2),
            new Coordinate(3, 3)
        );

        center = new Coordinate(2, 2);

        expectedAdjacentCoords = Arrays.asList(
            new Coordinate(2, 1),
            new Coordinate(2, 3),
            new Coordinate(1, 2),
            new Coordinate(3, 2)
        );

        expectedSurroundedCoords = Arrays.asList(
            new Coordinate(1, 1),
            new Coordinate(1, 2),
            new Coordinate(1, 3),
            new Coordinate(2, 1),
            new Coordinate(2, 3),
            new Coordinate(3, 1),
            new Coordinate(3, 2),
            new Coordinate(3, 3)
        );
    }

    @Test
    public void GetAllAdjacentCoordinatesFromStartCoordinate_ExpectedTrue() {
        final Collection<Coordinate> actualAdjacentCoords = SurroundingCoordsCalculator.getAdjacentCoordinates(allCoords, center);
        
        assertEquals(expectedAdjacentCoords.size(), actualAdjacentCoords.size());
        assertTrue(expectedAdjacentCoords.containsAll(actualAdjacentCoords));
    }

    @Test
    public void GetAllSurroundingCoordinatesFromStartCoordinate_ExpectedTrue() {
        final Collection<Coordinate> actualSurroundedCoords = SurroundingCoordsCalculator.getSurroundingCoordinates(allCoords, center);
        
        assertEquals(expectedSurroundedCoords.size(), actualSurroundedCoords.size());
        assertTrue(expectedSurroundedCoords.containsAll(actualSurroundedCoords));
    }
}
