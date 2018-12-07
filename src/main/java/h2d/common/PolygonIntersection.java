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
			logger.info("clipping edge: " + edge);
			for (Line e : intersected.getEdges()) {
				logger.info("\t intersected edge: " + e);
				if (edge.isInside(e.getEnd(), clipping.getOrientation())) {
					logger.info("\t\t end [" + e.getEnd() + "] inside");
					if (!edge.isInside(e.getOrigin(), clipping.getOrientation())) {
						logger.info("\t\t origin [" + e.getOrigin() + "] not inside: ");
						Point intersection = edge.getIntersectionUnounded(e);	
						logger.info("\t\t intersection: " + intersection);
						if (intersection!=null) {
							logger.info("\t\t + " + intersection);
							points.add(intersection);
						}							
					} else {
						logger.info("\t\t origin [" + e.getOrigin() + "] inside: ");
					}	
					logger.info("\t\t + " + e.getEnd());
					points.add(e.getEnd());
				} else {
					logger.info("\t\t end [" + e.getEnd() + "]  not inside: ");
					if (edge.isInside(e.getOrigin(), clipping.getOrientation())) {
						logger.info("\t\t origin [" + e.getOrigin() + "] inside");
						Point intersection = edge.getIntersectionUnounded(e);				
						logger.info("\t\t intersection: " + intersection);
						if (intersection!=null) {
							logger.info("\t\t + " + intersection);
							points.add(intersection);
						}
					} else {
						logger.info("\t\t origin [" + e.getOrigin() + "] not inside: ");
					}					
				}
			}
			logger.info(" Semi result: " + points);
			if (points.size()>2) {
				intersected = new Polygon(points);
			}			
			points.clear();
		}
		logger.info("------------------------------------------------");
		logger.info(" Result: " + intersected.getPoints());
		return intersected;
	}	
}
