package h3d;

import junit.framework.TestCase;
import transforms.Mat4;
import transforms.Mat4ViewRH;
import transforms.Point3D;
import transforms.Vec3D;

public class ViewTransformTestCase extends TestCase {

	public void test() throws Exception {
		
		Vec3D a = new Vec3D(0, 0, 1);
		Vec3D b = new Vec3D(0, 1, 0);
		Vec3D c = new Vec3D(10, 0, 0);
		
		Vec3D e = new Vec3D(-1, 0, 0);
		Vec3D v = new Vec3D(1, 0, 0);
		Vec3D u = new Vec3D(0, 1, 0);
		
		Vec3D x, y, z;
		z = v.mul(-1.0).normalized().orElse(new Vec3D(1, 0, 0));
		x = u.cross(z).normalized().orElse(new Vec3D(1, 0, 0));
		y = z.cross(x);
		
		System.out.println("z: " + z);
		System.out.println("x: " + x);
		System.out.println("y: " + y);
		
		Mat4ViewRH viewT = new Mat4ViewRH(e, v, u);

		Mat4 m = new Mat4(
			new Point3D(a),
			new Point3D(b),
			new Point3D(c),
			new Point3D(new Vec3D())
		);
		
		System.out.println("m:\n " + m);
		
		Mat4 t1 = m.mul(viewT);
		System.out.println("t1:\n " + t1);
	}
}
