package h3df;

import junit.framework.TestCase;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4OrthoRH;
import transforms.Mat4ViewRH;
import transforms.Point3D;
import transforms.Vec3D;

public class OrthoTestCase extends TestCase  {

	private static final Vec3D WT_1 = new Vec3D(1, 1, 1);
	private static final Vec3D WT_2 = new Vec3D(1, 1, 0);
	
	public void test() throws Exception {
		Mat4OrthoRH mat = new Mat4OrthoRH(100.0, 100.0, 0, -50.0);		
		Point3D r = new Point3D(10.0, 10.0, 50.0);
		r = r.mul(mat);
		System.out.println(r);
		
		Mat4 view = new Mat4ViewRH(
			new Vec3D(0, 0, 0), 
			new Vec3D(-1, 0, 0), 
			new Vec3D(0, 0, 1));
		
		
		//Mat4 view = new Camera().getViewMatrix();
		
		System.out.println(view);
		
		r = r.mul(view);
		System.out.println(r);			

	}
}
