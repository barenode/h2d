package solids;

import transforms.Point3D;

public class Cylinder extends SolidBase {

	public Cylinder() {
		super();		
		//0-11
		addCircle(0.5, 0.5);
		//12-23
		addCircle(0.5, -0.5);
		
		connectPoints(0, 11, 12);
		
		//centroid
        getVerticies().add(new Point3D());		
	}	
	
	@Override
	public Point3D getCentroid() {
		return getVerticies().get(getVerticies().size()-1);
	}
}
