package solids;

import transforms.Point3D;

public class Cube extends SolidBase {
	
    public Cube() {
    	super();	
    		
    	getVerticies().add(new Point3D(-0.5, -0.5, -0.5));    
        getVerticies().add(new Point3D(-0.5, 0.5, -0.5));    
        getVerticies().add(new Point3D(0.5, -0.5, -0.5));    
        getVerticies().add(new Point3D(0.5, 0.5, -0.5));   
        getVerticies().add(new Point3D(-0.5, -0.5, 0.5));  
        getVerticies().add(new Point3D(-0.5, 0.5, 0.5)); 
        getVerticies().add(new Point3D(0.5, -0.5, 0.5));    
        getVerticies().add(new Point3D(0.5, 0.5, 0.5)); 

        getIndicies().add(0); getIndicies().add(1);
        getIndicies().add(1); getIndicies().add(3);
        getIndicies().add(2); getIndicies().add(3);
        getIndicies().add(2); getIndicies().add(0);

        getIndicies().add(4); getIndicies().add(5);
        getIndicies().add(5); getIndicies().add(7);
        getIndicies().add(6); getIndicies().add(7);
        getIndicies().add(6); getIndicies().add(4);

        getIndicies().add(0); getIndicies().add(4);
        getIndicies().add(1); getIndicies().add(5);
        getIndicies().add(2); getIndicies().add(6);
        getIndicies().add(3); getIndicies().add(7);
        
        getVerticies().add(new Point3D());
    }

	@Override
	public Point3D getCentroid() {
		return getVerticies().get(8);
	}
}
