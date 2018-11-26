package h2d.common;

import junit.framework.TestCase;

public class PointTestCase extends TestCase {	

	public void test() throws Exception {
		assertEquals(5, new Point(1, 1).distance(new Point(5, 4)));
	}
}
