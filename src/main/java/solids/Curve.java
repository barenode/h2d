package solids;

import java.util.List;

import transforms.Point3D;

public class Curve extends SolidBase {
	
	public Curve(List<Point3D> points) {
		super();
		getVerticies().addAll(points);		
		for (int i = 1; i<points.size(); i++) {
			getIndicies().add(i-1); 
			getIndicies().add(i);
		}
	}

	@Override
	public Point3D getCentroid() {
		throw new UnsupportedOperationException();
	}
}
