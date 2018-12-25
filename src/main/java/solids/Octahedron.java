package solids;

import transforms.Point3D;

public class Octahedron extends SolidBase {
	
	public Octahedron() {
		super();
		
    	getVerticies().add(new Point3D(-0.5, -0.5, 0.0));    
        getVerticies().add(new Point3D(0.5, -0.5, 0.0));    
        getVerticies().add(new Point3D(0.5, 0.5, 0.0));    
        getVerticies().add(new Point3D(-0.5, 0.5, 0.0));   
        
        getVerticies().add(new Point3D(0, 0, 0.5));
        getVerticies().add(new Point3D(0, 0, -0.5));
        
        getIndicies().add(0); getIndicies().add(1);
        getIndicies().add(1); getIndicies().add(2);
        getIndicies().add(2); getIndicies().add(3);
        getIndicies().add(3); getIndicies().add(0);
        
        connectWithPoint(0, 3, 4);
        connectWithPoint(0, 3, 5);
        
        getVerticies().add(new Point3D());
	}

	@Override
	public Point3D getCentroid() {
		return getVerticies().get(getVerticies().size()-1);
	}
}
