package h2d.common;

import java.util.ArrayList;
import java.util.List;

import transforms.Mat3Rot2D;
import transforms.Point2D;
import transforms.Vec2D;

public class NAngle {

	private final Point centre;
	private final Point origin;
	private final int size;
	
	public NAngle(
		Point centre,
		Point origin,
		int size) 
	{
		super();
		this.centre = centre;
		this.origin = origin;
		this.size = size;
	}

	public Point getCentre() {
		return centre;
	}	

	public int getSize() {
		return size;
	}	
	
	public Polygon asPolygon() {
		final Vec2D c =  centre.toVec();
		final Vec2D o =  origin.toVec();
		final Vec2D v =  o.sub(c);
		final Mat3Rot2D mat = new Mat3Rot2D(2d*Math.PI/Double.valueOf(size));
		final List<Point> points = new ArrayList<>();
		Point2D p = new Point2D(v);
		for (int i=0; i<size; i++) {
			p = p.mul(mat);
			points.add(Point.fromVec(p.dehomog().get().add(c)));
		}
		return new Polygon(points);
	}
}
