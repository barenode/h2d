package h2d.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class PolygonIntersection implements BiFunction<Polygon, Polygon, Polygon> {
	
	@Override
	public Polygon apply(Polygon clipping, Polygon clipped) {
		Polygon intersected = new Polygon(clipped.getPoints());
		List<Point> points = new ArrayList<>();
		for (Line edge : clipping.getEdges()) {			
			for (Line e : intersected.getEdges()) {
				if (edge.isInside(e.getEnd(), clipping.getOrientation())) {
					if (!edge.isInside(e.getOrigin(), clipping.getOrientation())) {
						Point intersection = edge.getIntersectionUnounded(e);	
						if (intersection!=null) {
							points.add(intersection);
						}							
					}
					points.add(e.getEnd());
				} else {
					if (edge.isInside(e.getOrigin(), clipping.getOrientation())) {						
						Point intersection = edge.getIntersectionUnounded(e);			
						if (intersection!=null) {
							points.add(intersection);
						}
					}				
				}
			}
			if (points.size()>2) {
				intersected = new Polygon(points);
			}			
			points.clear();
		}
		return intersected;
	}	
}
