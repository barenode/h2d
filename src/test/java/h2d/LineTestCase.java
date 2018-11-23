package h2d;

import h2d.Line;
import h2d.Point;
import junit.framework.TestCase;

public class LineTestCase extends TestCase {	

	public void test() throws Exception {
		Line line = new Line(2, 2, 4, 4);
		assertEquals(new Point(3, 3), line.getIntersection(new Line(2, 4, 4, 2)));
	
		Point intersection = line.getIntersection(new Line(2, 2, 4, 4));
		System.out.println(intersection);
	} 
}
