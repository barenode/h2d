package hylmargx;

import java.awt.Color;

import org.apache.log4j.Logger;

public final class Line {
	private static final Logger log = Logger.getLogger("Bresenham");
	
	private final Point origin;
	private final Point end;
	private final Color color;
	
	public Line(int originX, int originY, int endX, int endY) {
		this(new Point(originX, originY), new Point(endX, endY));
	}
	
	public Line(Point origin, Point end) {
		this(origin, end, Color.BLACK);
	}
	
	public Line(Point origin, Point end, Color color) {
		super();
		this.origin = origin;
		this.end = end;
		this.color = color;
	}

	public Point getOrigin() {
		return origin;
	}

	public Point getEnd() {
		return end;
	}	
	
	public Color getColor() {
		return color;
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
		return new Point(x, y, color);
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

