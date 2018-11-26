package h2d.common;

import junit.framework.TestCase;
import transforms.Vec2D;

public class VectorTestCase extends TestCase {

	public void test1() throws Exception {
		System.out.println("test1");
		Vec2D origin = new Vec2D(1, 1);
		Vec2D end = new Vec2D(5, 5);
		
		Vec2D t = end.sub(origin);
		System.out.println("t: " + t);
		Vec2D per1 = new Vec2D(t.getY(), -t.getX());
		System.out.println("per1: " + per1);
		Vec2D per2 = new Vec2D(-t.getY(), t.getX());
		System.out.println("per2: " + per2);
		
		Vec2D p = new Vec2D(2, 4);
		Vec2D v = p.sub(origin);
		System.out.println("v: " + v);
		
		double cosper1 = v.normalized().get().dot(per1.normalized().get());
		System.out.println("cosper1: " + cosper1);
		System.out.println("acos cosper1 : " + Math.acos(cosper1));
		
		double cosper2 = v.normalized().get().dot(per2.normalized().get());
		System.out.println("cosper2: " + cosper2);
		System.out.println("acos cosper1 : " + Math.acos(cosper2));
	}
	
	public void test2() throws Exception {
		System.out.println("test1");
		Vec2D end = new Vec2D(1, 1);
		Vec2D origin = new Vec2D(5, 5);
		
		Vec2D t = end.sub(origin);
		System.out.println("t: " + t);
		Vec2D per1 = new Vec2D(t.getY(), -t.getX());
		System.out.println("per1: " + per1);
		Vec2D per2 = new Vec2D(-t.getY(), t.getX());
		System.out.println("per2: " + per2);
		
		Vec2D p = new Vec2D(2, 4);
		Vec2D v = p.sub(origin);
		System.out.println("v: " + v);
		
		double cosper1 = v.normalized().get().dot(per1.normalized().get());
		System.out.println("cosper1: " + cosper1);
		System.out.println("acos cosper1 : " + Math.acos(cosper1));
		
		double cosper2 = v.normalized().get().dot(per2.normalized().get());
		System.out.println("cosper2: " + cosper2);
		System.out.println("acos cosper1 : " + Math.acos(cosper2));
	}
}
