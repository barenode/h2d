package h2d.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.log4j.Logger;

import h2d.PolygonIntersectionController;


public class PolygonIntersection implements BiFunction<Polygon, Polygon, Polygon> {
	private static final Logger logger = Logger.getLogger(PolygonIntersection.class);
	
	@Override
	public Polygon apply(Polygon clipping, Polygon clipped) {
		logger.info("==================================================");
		logger.info("clippingPolygon: " + clipping);
		Polygon intersected = new Polygon(clipped.getPoints());
		List<Point> points = new ArrayList<>();
		for (Line edge : clipping.getEdges()) {			
			for (Line e : intersected.getEdges()) {
				if (edge.isInside(e.getEnd(), clipping.getOrientation())) {
					if (!edge.isInside(e.getOrigin(), clipping.getOrientation())) {
						Point intersection = edge.getIntersection(e);	
						if (intersection!=null) {
							points.add(intersection);
						}							
					}	
					points.add(e.getEnd());
				} else {
					if (edge.isInside(e.getOrigin(), clipping.getOrientation())) {
						Point intersection = edge.getIntersection(e);					
						if (intersection!=null) {
							points.add(intersection);
						}
					}						
				}
			}
			logger.info(" Result: " + points);
			if (points.size()>2) {
				intersected = new Polygon(points);
			}			
			points.clear();
		}
		return intersected;
	}	
}
