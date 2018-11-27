package h2d.common;

import junit.framework.TestCase;

public class PolygonIntersectionTestCase extends TestCase {

	public void test() throws Exception {
		Polygon clipping = new Polygon(new Point(40, 20), new Point(30, 50), new Point(10, 10));
		Polygon clipped = new Polygon(new Point(30, 10), new Point(50, 50), new Point(20, 40));
		new PolygonIntersection().apply(clipping, clipped);
	}
}
