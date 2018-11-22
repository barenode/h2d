package hylmargx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class PolygonRenderer implements Renderer<Polygon> {
	private static final Logger log = Logger.getLogger("Polygon");
	
	private final Renderer<Line> lineRenderer;
	
	public PolygonRenderer() {
		this(new LineRendererDDA());
	}
	
	public PolygonRenderer(Renderer<Line> lineRenderer) {
		super();
		this.lineRenderer = lineRenderer;
	}

	@Override
	public void render(Polygon shape, Image image) {
		//scan line
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		List<Line> lines = new ArrayList<>();
		for (Line edge : shape.getEdges()) {
			if (edge.isHorizontal()) {
				continue;
			}
			Line line = edge;			
			minX = Math.min(minX, line.getOrigin().getX());
			minX = Math.min(minX, line.getEnd().getX());
			maxX = Math.max(maxX, line.getOrigin().getX());
			maxX = Math.max(maxX, line.getEnd().getX());
			minY = Math.min(minY, line.getOrigin().getY());
			minY = Math.min(minY, line.getEnd().getY());
			maxY = Math.max(maxY, line.getOrigin().getY());
			maxY = Math.max(maxY, line.getEnd().getY());		
			if (line.getEnd().getY()<line.getOrigin().getY()) {
				line = line.flip();
			}
			line = line.trim();
			if (log.isDebugEnabled()) {
				log.debug(edge + " > " + line);
			}
			lines.add(line);			
		}
		
		for (int y=minY; y<=maxY; y++) {
			Line yline = new Line(minX, y, maxX, y);
			List<Point> intersections = new ArrayList<>();
			for (Line line : lines) {
				Point intersection = yline.getIntersection(line);
				if (intersection!=null) {
					intersections.add(intersection);
				}				
			}
			
			if (!intersections.isEmpty()) {
				Collections.sort(intersections, Point.XSORT);
				if (log.isDebugEnabled()) {
					log.debug(yline + " intersections: " + intersections);
				}
				for (int i=0; i<intersections.size()/2; i++) {					
					if (log.isDebugEnabled()) {
						log.debug("fill line" + intersections.get(i*2) + intersections.get(i*2+1));
					}
					lineRenderer.render(new Line(intersections.get(i*2), intersections.get(i*2+1)), image);
				}
			}						
		}			
		//render edges
		for (Line edge : shape.getEdges()) {
			lineRenderer.render(edge, image);
		}
	}
}
