package h2d.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PolygonRenderer implements Renderer<Polygon> {
	
	private final Renderer<Line> lineRenderer;
	private final Renderer<Line> backgroundRenderer;	
	
	public PolygonRenderer(Renderer<Line> lineRenderer, Renderer<Line> backgroundRenderer) {
		super();
		this.lineRenderer = lineRenderer;
		this.backgroundRenderer = backgroundRenderer;
	}

	@Override
	public void render(Polygon shape, Image image) {
		if (backgroundRenderer!=null) {		
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
					for (int i=0; i<intersections.size()/2; i++) {				
						backgroundRenderer.render(new Line(intersections.get(i*2), intersections.get(i*2+1)), image);
					}
				}						
			}		
		}
		//render edges
		for (Line edge : shape.getEdges()) {
			lineRenderer.render(edge, image);
		}
	}
}
