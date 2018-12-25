package solids;

import transforms.Point3D;

public class Sphere extends SolidBase {	
			
	public Sphere() {
		super();
		
		//0-11
		addCircle(0.5, 0.0);
		//12-23
		addCircle(0.5*SIN_60, 0.25);
		//24-35
		addCircle(0.5*SIN_60, -0.25);
		//36-47
		addCircle(0.25, 0.5*SIN_60);
		//48-59
		addCircle(0.25, -0.5*SIN_60);
		
		getVerticies().add(new Point3D(0.0, 0.0, 0.5)); 
		getVerticies().add(new Point3D(0.0, 0.0, -0.5)); 
		
		connectPoints(0, 11, 12);
		connectPoints(0, 11, 24);
		
		connectPoints(12, 23, 24);
		connectPoints(24, 35, 24);
		
		connectWithPoint(36, 47, 60);
		connectWithPoint(48, 59, 61);
		
        getVerticies().add(new Point3D());		
	}	
	
	@Override
	public Point3D getCentroid() {
		return getVerticies().get(getVerticies().size()-1);
	}

}
