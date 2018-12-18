package solids;

import transforms.Point3D;

public class Point extends SolidBase {

	public Point() {
		super();
		getVerticies().add(new Point3D(0, 0, 0));
		getIndicies().add(0); getIndicies().add(0);
	}

	@Override
	public Point3D getCentroid() {
		return getVerticies().get(0);
	}
}
