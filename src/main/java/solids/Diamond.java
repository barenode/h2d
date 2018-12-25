package solids;

import transforms.Point3D;

public class Diamond extends SolidBase {

	public Diamond() {
		super();
		
		getVerticies().add(new Point3D(-0.25, -0.5, 0.0));    
		getVerticies().add(new Point3D(0.25, -0.5, 0.0));    
		getVerticies().add(new Point3D(0.5, -0.25, 0.0));    
		getVerticies().add(new Point3D(0.5, 0.25, 0.0));		
		getVerticies().add(new Point3D(0.25, 0.5, 0.0));    
		getVerticies().add(new Point3D(-0.25, 0.5, 0.0));    
		getVerticies().add(new Point3D(-0.5, 0.25, 0.0));    
		getVerticies().add(new Point3D(-0.5, -0.25, 0.0));
		
		getVerticies().add(new Point3D(-0.125, -0.25, -0.25));    
		getVerticies().add(new Point3D(0.125, -0.25, -0.25));    
		getVerticies().add(new Point3D(0.25, -0.125, -0.25));    
		getVerticies().add(new Point3D(0.25, 0.125, -0.25));		
		getVerticies().add(new Point3D(0.125, 0.25, -0.25));    
		getVerticies().add(new Point3D(-0.125, 0.25, -0.25));    
		getVerticies().add(new Point3D(-0.25, 0.125, -0.25));    
		getVerticies().add(new Point3D(-0.25, -0.125, -0.25));    
		
		getVerticies().add(new Point3D(0.0, 0.0, 0.5));    
		
		getVerticies().add(new Point3D());
		
		getIndicies().add(0); getIndicies().add(1);
        getIndicies().add(1); getIndicies().add(2);
        getIndicies().add(2); getIndicies().add(3);
        getIndicies().add(3); getIndicies().add(4);
        getIndicies().add(4); getIndicies().add(5);
        getIndicies().add(5); getIndicies().add(6);
        getIndicies().add(6); getIndicies().add(7);
        getIndicies().add(7); getIndicies().add(0);
        
        getIndicies().add(0+8); getIndicies().add(1+8);
        getIndicies().add(1+8); getIndicies().add(2+8);
        getIndicies().add(2+8); getIndicies().add(3+8);
        getIndicies().add(3+8); getIndicies().add(4+8);
        getIndicies().add(4+8); getIndicies().add(5+8);
        getIndicies().add(5+8); getIndicies().add(6+8);
        getIndicies().add(6+8); getIndicies().add(7+8);
        getIndicies().add(7+8); getIndicies().add(0+8);
        
        getIndicies().add(0); getIndicies().add(0+8);
        getIndicies().add(1); getIndicies().add(1+8);
        getIndicies().add(2); getIndicies().add(2+8);
        getIndicies().add(3); getIndicies().add(3+8);
        getIndicies().add(4); getIndicies().add(4+8);
        getIndicies().add(5); getIndicies().add(5+8);
        getIndicies().add(6); getIndicies().add(6+8);
        getIndicies().add(7); getIndicies().add(7+8);
        
        getIndicies().add(0); getIndicies().add(16);
        getIndicies().add(1); getIndicies().add(16);
        getIndicies().add(2); getIndicies().add(16);
        getIndicies().add(3); getIndicies().add(16);
        getIndicies().add(4); getIndicies().add(16);
        getIndicies().add(5); getIndicies().add(16);
        getIndicies().add(6); getIndicies().add(16);
        getIndicies().add(7); getIndicies().add(16);
		
	}

	@Override
	public Point3D getCentroid() {
		return getVerticies().get(getVerticies().size()-1);
	}
}
