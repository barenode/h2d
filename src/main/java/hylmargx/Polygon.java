package hylmargx;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Polygon {

	private final List<Point> points;
	private final Color color;
	private final Color background;
	
	public Polygon(List<Point> points) {
		this(points, Color.BLACK, Color.RED);
	}
	
	public Polygon(List<Point> points, Color color, Color background) {
		super();
		this.points = points;
		this.color = color;
		this.background = background;
	}

	public List<Point> getPoints() {
		return points;
	}
	
	public List<Line> getEdges() {
		List<Line> edges = new ArrayList<>();
		Point previous = null;
		for (Point point : points) {
			if (previous!=null) {
				edges.add(new Line(previous, point, color));
			}
			previous = point;
		}
		edges.add(new Line(previous, points.get(0), color));
		return edges;
	}
}
