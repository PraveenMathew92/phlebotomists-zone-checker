import org.junit.jupiter.api.Test;
import src.Point;
import src.PointInPolygonChecker;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PointInPolygonCheckerTest {
    PointInPolygonChecker pointInPolygonChecker = new PointInPolygonChecker();

    @Test
    void shouldReturnTrueIfPointInRectangle() {
        Point[] edges = {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1), new Point(0, 0)};
        Point testPoint = new Point(0.5, 0.5);

        assertTrue(pointInPolygonChecker.check(testPoint, edges));
    }

    @Test
    void shouldReturnFalseIfPointIsOutsideRectangle() {
        Point[] edges = {new Point(0, 0), new Point(1, 1), new Point(0, 1), new Point(1, 0), new Point(0, 0)};
        Point testPoint = new Point(2, 2);

        assertFalse(pointInPolygonChecker.check(testPoint, edges));
    }

    @Test
    void shouldReturnTrueIfPointIsInConcavePolygon() {
        Point[] edges = {new Point(-1, -5), new Point(0, 10), new Point(1, -5), new Point(0, -3), new Point(-1, -5)};
        Point testPoint = new Point(0, 0);

        assertTrue(pointInPolygonChecker.check(testPoint, edges));
    }

    @Test
    void shouldReturnFalseIfPointIsOutsideConcavePolygon() {
        Point[] edges = {new Point(-1, -5), new Point(1, -5), new Point(0, 10), new Point(0, -3), new Point(-1, -5)};
        Point testPoint = new Point(0, -10);

        assertFalse(pointInPolygonChecker.check(testPoint, edges));
    }

    @Test
    void shouldReturnTrueIfPointIsInConcavePolygonWithThreeCrossing() {
        Point[] edges = {new Point(-2, -5), new Point(0, 2), new Point(2, -5), new Point(0, -3), new Point(-2, -5)};
        Point testPoint = new Point(-1, -3.5);

        assertTrue(pointInPolygonChecker.check(testPoint, edges));
    }

    @Test
    void shouldReturnTrueIfPointIsInConcavePolygonWithThreeCrossingWithPointsNotInOrder() {
        Point[] edges = {new Point(-2, -5), new Point(0, 2), new Point(2, -5), new Point(0, -3), new Point(-2, -5)};
        Point testPoint = new Point(-1, -3.5);

        assertTrue(pointInPolygonChecker.check(testPoint, edges));
    }


}