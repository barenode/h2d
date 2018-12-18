package solids;

import transforms.Point3D;

public class Spire extends SolidBase {

	private final double halfSize;
	private final double halfHeight;
	
	public Spire(double width, double height) {
		super();
		halfSize = width/2.0;
		halfHeight = height/3.0;
		
    	getVerticies().add(new Point3D(0, 0, 0));    
        getVerticies().add(new Point3D(0, width, 0));    
        getVerticies().add(new Point3D(width, 0, 0));    
        getVerticies().add(new Point3D(width, width, 0));    
        final double halfWidth = width/2d;
        getVerticies().add(new Point3D(halfWidth, halfWidth, height));
        
        getIndicies().add(0); getIndicies().add(1);
        getIndicies().add(1); getIndicies().add(3);
        getIndicies().add(2); getIndicies().add(3);
        getIndicies().add(2); getIndicies().add(0);
        
        getIndicies().add(0); getIndicies().add(4);
        getIndicies().add(1); getIndicies().add(4);
        getIndicies().add(2); getIndicies().add(4);
        getIndicies().add(3); getIndicies().add(4);
	}

	@Override
	public Point3D getCentroid() {
		Point3D origin = getVerticies().get(0);
		return new Point3D(origin.getX() + halfSize, origin.getY() + halfSize, origin.getZ() + halfHeight, origin.getW());
	}
}
