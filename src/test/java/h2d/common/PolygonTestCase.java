package h2d.common;

import junit.framework.TestCase;

public class PolygonTestCase extends TestCase {	

	public void testOrientation() {
		assertTrue(new Polygon(new Point(5, 5), new Point(10, 10)).getOrientation()==Polygon.Orientation.CounterClockwise);		
		assertTrue(new Polygon(new Point(2, 1), new Point(5, 4), new Point(1, 10)).getOrientation()==Polygon.Orientation.CounterClockwise);
		assertTrue(new Polygon(new Point(5, 4), new Point(2, 1), new Point(1, 10)).getOrientation()==Polygon.Orientation.Clockwise);
	}
}
