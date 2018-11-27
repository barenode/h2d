package h2d.common;

import org.apache.log4j.Logger;

import transforms.Mat3;
import transforms.Vec2D;

public final class Line {
	private static final Logger log = Logger.getLogger("Bresenham");
	private static final double HALF_PI = Math.PI/2d;
	
	private final Point origin;
	private final Point end;
	
	public Line(int originX, int originY, int endX, int endY) {
		this(new Point(originX, originY), new Point(endX, endY));
	}
	
	public Line(Point origin, Point end) {
		super();
		this.origin = origin;
		this.end = end;
	}

	public Point getOrigin() {
		return origin;
	}

	public Point getEnd() {
		return end;
	}

	public Line flip() {
		return new Line(end, origin);
	}
	
	public float getTangent() {
		final int x1 = origin.getX();
		final int y1 = origin.getY();
		final int x2 = end.getX();
		final int y2 = end.getY();
		final int dx = x2-x1;
		final int dy = y2-y1;
		//return Infinity for dx = 0
		return (float)dy/(float)dx;  
	}
	
	public float getYIntercept() {
		return origin.getY()-(origin.getX()*getTangent());
	}
	
	public boolean isHorizontal() {
		return origin.getY()==end.getY();
	}
	
	public boolean isVertical() {
		return origin.getY()==end.getY();
	}
	
	public Vec2D vector() {
		return new Vec2D(
			end.getX()-origin.getX(), 
			end.getY()-origin.getY());
	}
	
//	public double angle(Point point) {
//		System.out.println(cosine(point));
//		return Math.acos(cosine(point));
//	}
//	
//	public double sine(Point point) {
//		return Math.sqrt(1d-Math.pow(cosine(point), 2));
//	}
	
	public double cosine(Point point) {
		Vec2D u = vector();
		Vec2D v = new Line(end, point).vector();
		return u.dot(v)/(u.length()*v.length());
	}
	
	public boolean isInside(Point point, Polygon.Orientation orientation) {
		Vec2D o = origin.toVec();
		Vec2D e = end.toVec();
		Vec2D t = e.sub(o);
		Vec2D per;
		if (Polygon.Orientation.Clockwise==orientation) {
			per = new Vec2D(t.getY(), -t.getX());
		} else if (Polygon.Orientation.CounterClockwise==orientation) {
			per = new Vec2D(-t.getY(), t.getX());
		} else {
			throw new IllegalArgumentException();
		}
		//Vec2D per = new Vec2D(t.getY(), -t.getX());
		//Vec2D per = new Vec2D(-t.getY(), t.getX());
		Vec2D p = point.toVec();
		Vec2D v = p.sub(o);
		double cos = v.normalized().get().dot(per.normalized().get());
		double angle  = Math.acos(cos);
		//System.out.println(angle);
		return Math.abs(angle)<=HALF_PI;
	}
	
	public Line trim() {		
//		if (Math.abs(getTangent())>1) {
			//lead by y
			int yStep = 1;
			if (end.getY()<origin.getY()) {
				yStep = -1;
			}
			float newX = end.getX()-(1/getTangent());
			return new Line(origin, new Point((int)newX, end.getY()-yStep));
//		} else {
//			//lead by x
//			int xStep = 1;
//			if (end.getX()<origin.getX()) {
//				xStep = -1;
//			}
//			float newY = end.getY()-getTangent();
//			return new Line(origin, new Point(end.getX()-xStep, (int)newY));
//		}
	}
	
	public Point getIntersection(Line line) {
		float k1 = getTangent();
		float k2 = line.getTangent();
		float q1 = getYIntercept();
		float q2 = line.getYIntercept();
		float xf = (q2-q1)/(k1-k2);		
		float yf = q1 + k1*xf;		
		if (Float.isNaN(xf) || Float.isNaN(yf)) {
			return null;
		}
		int x = Math.round(xf);
		int y = Math.round(yf);
		if (log.isDebugEnabled()) {
			log.debug("Intersection of " + this + " with " + line + " is [" + x + ", " + y + "].");
		}
		if (origin.getX()<end.getX()) {
			if (x<origin.getX() || x>end.getX()) {
				return null;
			}
		} else {
			if (x>origin.getX() || x<end.getX()) {
				return null;
			}
		}		
		if (line.origin.getX()<line.end.getX()) {
			if (x<line.origin.getX() || x>line.end.getX()) {
				return null;
			}
		} else {
			if (x>line.origin.getX() || x<line.end.getX()) {
				return null;
			}
		}
		
		if (origin.getY()<end.getY()) {
			if (y<origin.getY() || y>end.getY()) {
				return null;
			}
		} else {
			if (y>origin.getY() || y<end.getY()) {
				return null;
			}
		}		
		if (line.origin.getY()<line.end.getY()) {
			if (y<line.origin.getY() || y>line.end.getY()) {
				return null;
			}
		} else {
			if (y>line.origin.getY() || y<line.end.getY()) {
				return null;
			}
		}		
		return new Point(x, y);
	}
	
	public Line transform(Mat3 mat) {
		return new Line(origin.transform(mat), end.transform(mat));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
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
		Line other = (Line) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + origin + end;
	}		
}	

