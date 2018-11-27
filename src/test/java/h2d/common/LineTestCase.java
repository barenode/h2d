package h2d.common;


import h2d.common.Line;
import h2d.common.Point;
import junit.framework.TestCase;
import transforms.Vec2D;

public class LineTestCase extends TestCase {	

	public void test() throws Exception {
		Line line = new Line(2, 2, 4, 4);
		assertEquals(new Point(3, 3), line.getIntersection(new Line(2, 4, 4, 2)));
	
		Point intersection = line.getIntersection(new Line(2, 2, 4, 4));
		System.out.println(intersection);
	} 
	
//	public void testAngle() throws Exception {
//		double sine;
//		
//		sine =  new Line(1, 1, 2, 2).sine(new Point(3, 3));
//		System.out.println(sine);
//		
//		sine =  new Line(1, 1, 2, 2).sine(new Point(1, 3));
//		System.out.println(sine);
//		
//		sine =  new Line(1, 1, 3, 3).sine(new Point(2, 1));
//		System.out.println(sine);
//
//	}
	
	public void testInside() {
		System.out.println(Math.PI/2);
		assertTrue(new Line(1, 1, 5, 5).isInside(new Point(3 , 3), Polygon.Orientation.CounterClockwise));
		assertTrue(new Line(1, 1, 5, 5).isInside(new Point(3 , 4), Polygon.Orientation.CounterClockwise));
		assertFalse(new Line(1, 1, 5, 5).isInside(new Point(3 , 0), Polygon.Orientation.CounterClockwise));		
		assertTrue(new Line(4, 1, 1, 4).isInside(new Point(0 , 0), Polygon.Orientation.CounterClockwise));
		
		//assertTrue(new Line(4, 4, 1, 1).isInside(new Point(2 , 3)));
		
	}
	
	
	public void testVectors() {
		System.out.println(getCos(new Vec2D(1, 0), new Vec2D(1, 1)));
		System.out.println(getCos(new Vec2D(1, 0), new Vec2D(1, 0)));
		System.out.println(getCos(new Vec2D(1, 0), new Vec2D(-1, 1)));
		System.out.println(getCos(new Vec2D(1, 0), new Vec2D(-1, 0)));
		System.out.println(getCos(new Vec2D(1, 0), new Vec2D(-1, -1)));
		System.out.println(getCos(new Vec2D(1, 0), new Vec2D(0, -1)));
		System.out.println(getCos(new Vec2D(1, 0), new Vec2D(1, -1)));
		
		System.out.println("-------------------------------------------");
		
		System.out.println(getSine(new Vec2D(1, 0), new Vec2D(1, 1)));
		System.out.println(getSine(new Vec2D(1, 0), new Vec2D(1, 0)));
		System.out.println(getSine(new Vec2D(1, 0), new Vec2D(-1, 1)));
		System.out.println(getSine(new Vec2D(1, 0), new Vec2D(-1, 0)));
		System.out.println(getSine(new Vec2D(1, 0), new Vec2D(-1, -1)));
		System.out.println(getSine(new Vec2D(1, 0), new Vec2D(0, -1)));
		System.out.println(getSine(new Vec2D(1, 0), new Vec2D(1, -1)));
	}
	
	
	
	double getCos(Vec2D  u, Vec2D v) {
		u = u.normalized().get();
		v = v.normalized().get();		
		return u.dot(v)/(u.length()*v.length());		
	}
	
	double getSine(Vec2D  u, Vec2D v) {
		u = u.normalized().get();
		v = v.normalized().get();		
		return (
			(u.getX()*v.getY()-u.getY()*v.getX())
			/
			(u.length()*v.length())
		);		
	}
}
