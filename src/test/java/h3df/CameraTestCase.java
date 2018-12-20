package h3df;

import org.apache.log4j.Logger;

import junit.framework.TestCase;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4ViewRH;
import transforms.Vec3D;

public class CameraTestCase extends TestCase {
	private static final Logger logger = Logger.getLogger(CameraTestCase.class);
	
	public void test() throws Exception {		
		Camera camera = new Camera(new Vec3D(30, 0, 0), Math.PI, 0.0, 0, true);
		logger.info("ViewMatrix:\n " + camera.getViewMatrix());
		logger.info("ViewVector: " + camera.getViewVector());
		logger.info("UpVector: " + camera.getUpVector());
		logger.info("---------------------------------");
		Mat4 view2 = new Mat4ViewRH(
			new Vec3D(30, 0, 0), 
			new Vec3D(-1, 0, 0), 
			new Vec3D(0, 1, -0));
		logger.info("view2:\n " + view2);
	}
}
