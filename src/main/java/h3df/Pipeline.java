package h3df;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.apache.log4j.Logger;

import h2d.common.Line;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

public class Pipeline implements Function<Scene, List<Line>> {	
	private static final Logger logger = Logger.getLogger(Pipeline.class);
	
	public Pipeline() {
		super();
	}

	@Override
	public List<Line> apply(Scene scene) {	
		Mat4 projection = projection();
		logger.debug("\n" + projection.toString("%4.3f"));
		scene.getSolids().forEach(s -> {
			s.getEdges().forEach(e -> {
				
			});
		});
		
		return Collections.emptyList();
	}	
	
	private Function<Point3D, Point3D> projection() {
		return p -> {
			return p.mul(projection());
		};
	}
	
	protected Mat4 projection() {
		return new Mat4Projection(100, 100, 10, 110);
	} 
	
	private static class Mat4Projection extends Mat4Identity {
		public Mat4Projection(
			final double w, 
			final double h, 
			final double zn,
			final double zf) 
		{
			//super();
			mat[0][0] = 2.0 / w;
			mat[1][1] = 2.0 / h;
			mat[2][2] = 1.0 / (zn - zf);
			mat[3][2] = zn  / (zn - zf);
		}
	}
}
