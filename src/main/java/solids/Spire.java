package solids;

import transforms.Point3D;

public class Spire extends SolidBase {
	
	public Spire() {
		super();
		
    	getVerticies().add(new Point3D(-0.5, -0.5, -0.5));    
        getVerticies().add(new Point3D(-0.5, 0.5, -0.5));    
        getVerticies().add(new Point3D(0.5, -0.5, -0.5));    
        getVerticies().add(new Point3D(0.5, 0.5, -0.5));   
        getVerticies().add(new Point3D(0, 0, 0.5));
        
        getIndicies().add(0); getIndicies().add(1);
        getIndicies().add(1); getIndicies().add(3);
        getIndicies().add(2); getIndicies().add(3);
        getIndicies().add(2); getIndicies().add(0);
        
        getIndicies().add(0); getIndicies().add(4);
        getIndicies().add(1); getIndicies().add(4);
        getIndicies().add(2); getIndicies().add(4);
        getIndicies().add(3); getIndicies().add(4);
        
        getVerticies().add(new Point3D());
	}

	@Override
	public Point3D getCentroid() {
		return getVerticies().get(5);
	}
}
