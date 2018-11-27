package h2d.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import transforms.Mat3;

public class Polygon {

	private final List<Point> points;
	
	public Polygon(Point... points) {
		this(Arrays.asList(points));
	}
	
	public Polygon(List<Point> points) {
		super();
		if (points.size()<2) {
			throw new IllegalArgumentException("Minimum points to construct polygin is 2!");
		}
		this.points = new ArrayList<>(points);
	}

	public List<Point> getPoints() {
		return points;
	}
	
	public List<Line> getEdges() {
		List<Line> edges = new ArrayList<>();
		Point previous = null;
		for (Point point : points) {
			if (previous!=null) {
				edges.add(new Line(previous, point));
			}
			previous = point;
		}
		edges.add(new Line(previous, points.get(0)));
		return edges;
	}
	
	public Polygon transform(Mat3 mat) {
		return new Polygon(points.stream().map(p->p.transform(mat)).collect(Collectors.toList()));
	}		
	
	@Override
	public String toString() {
		return "Polygon [points=" + points + "]";
	}

	public Orientation getOrientation() {
		int sum = 0;
		for (Line edge : getEdges()) {
			sum += (edge.getEnd().getX()-edge.getOrigin().getX())*(edge.getEnd().getY()+edge.getOrigin().getY());
		} 
		if (sum>0) {
			return Orientation.Clockwise;
		} else {
			return Orientation.CounterClockwise;
		}
		
//		Orientation o = null;
//		for (int i=0; i<points.size(); i++) {
//			Point start = getPoint(i-1);
//			Point pivot = getPoint(i);
//			Point end = getPoint(i+1);
//			System.out.println(start);
//			System.out.println(pivot);
//			System.out.println(end);
//			double sine = new Line(start, pivot).sine(end);
//			System.out.println(sine);
//			if (sine>0d) {
//				if (o==null) {
//					o = Orientation.CounterClockwise;
//				} else {
//					if (o != Orientation.CounterClockwise) {
//						o = Orientation.Mixed;
//						break;
//					}
//				}
//			} else if (sine<0d) {
//				if (o==null) {
//					o = Orientation.Clockwise;
//				} else {
//					if (o != Orientation.Clockwise) {
//						o = Orientation.Mixed;
//						break;
//					}
//				}
//			}
//		}
//		if (o==null) {
//			//whatever
//			o = Orientation.CounterClockwise;
//		}
//		return o;		
	}	
	
//	private Point getPoint(int index) {
//		if (index==-1) {
//			//last
//			return points.get(points.size()-1);
//		} else if (index==points.size()) {
//			return points.get(0);
//		} else {
//			return points.get(index);
//		}
//	}
	
	
	
	public enum Orientation {
		Clockwise,
		CounterClockwise,
		Mixed
	}
}
