package h2d;

public class Circle {

	private final Point centre;
	private final int radius;
	
	public Circle(Point centre, int radius) {
		super();
		this.centre = centre;
		this.radius = radius;
	}

	public Point getCentre() {
		return centre;
	}

	public int getRadius() {
		return radius;
	}	
}
