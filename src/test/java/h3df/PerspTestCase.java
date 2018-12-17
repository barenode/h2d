package h3df;

import junit.framework.TestCase;
import transforms.Mat4PerspRH;
import transforms.Point3D;

public class PerspTestCase extends TestCase  {

	//new Mat4PerspRH(1, 1, 1, 100)
	
	public void test() throws Exception {
		double h = (1.0 / Math.tan(1.0 / 2.0));
		System.out.println(h);
		
		Mat4PerspRH mat = new Mat4PerspRH(Math.PI/4, 1, 1, 100);
		System.out.println(mat);
		Point3D r = new Point3D(10.0, 10.0, 50.0);
		r = r.mul(mat);
		System.out.println(r);
	}
}
