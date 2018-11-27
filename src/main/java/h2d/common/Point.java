package h2d.common;

import java.util.Comparator;

import transforms.Mat3;
import transforms.Point2D;
import transforms.Vec2D;

/**
 * @author hylmar
 */
public class Point {	
	public static final Point ZERO = new Point(0, 0);

	private final int x;
	private final int y;	
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}	
	
	/**
	 * Return norm-2 distance between points;
	 */
	public double distance(Point p) {	
		return Math.sqrt(Math.pow(x-p.getX(), 2) + Math.pow(y-p.getY(), 2));
	}
	
	public Point transform(Mat3 mat) {
		Point2D p = new Point2D(x, y).mul(mat);
		Vec2D v = p.dehomog().get();
		return new Point((int)Math.round(v.getX()), (int)Math.round(v.getY()));
	}
	
	public Vec2D toVec() {
		return new Vec2D(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;		
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}


	/**
     * sorts point by x axis
     */
    public static Comparator<Point> XSORT = new Comparator<Point>() {
		@Override
		public int compare(Point o1, Point o2) {
			return Integer.valueOf(o1.getX()).compareTo(Integer.valueOf(o2.getX()));
		}    	
    };
    
    /**
     * sorts point by y axis
     */
    public static Comparator<Point> YSORT = new Comparator<Point>() {
		@Override
		public int compare(Point o1, Point o2) {
			return Integer.valueOf(o1.getY()).compareTo(Integer.valueOf(o2.getY()));
		}    	
    };
}
