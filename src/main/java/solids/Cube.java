package solids;

import transforms.Point3D;

public class Cube extends SolidBase {

	private final double halfSize;
	

		/*
           7______6
		   /|    /|
		 4/_|___/5|
		 | 3|__|_ |2
		 | /   | /
		 |/____|/
		 0     1
		 */

    public Cube(double size) {
    	super();
    	halfSize = size/2.0;
    	
    	getVerticies().add(new Point3D(0, 0, 0));     // 0.
        getVerticies().add(new Point3D(0, size, 0));    // 1.
        getVerticies().add(new Point3D(size, 0, 0));    //2
        getVerticies().add(new Point3D(size, size, 0));    //3
        getVerticies().add(new Point3D(0, 0, size));     //4
        getVerticies().add(new Point3D(0, size, size));    //5
        getVerticies().add(new Point3D(size, 0, size));    //6
        getVerticies().add(new Point3D(size, size, size));   //7


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
    }

	@Override
	public Point3D getCentroid() {
		Point3D origin = getVerticies().get(0);
		return new Point3D(origin.getX() + halfSize, origin.getY() + halfSize, origin.getZ() + halfSize, origin.getW());
	}
}
