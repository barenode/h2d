package h2d.common;

import java.awt.Color;

import org.apache.log4j.Logger;

public class LineRendererBresenham extends LineRenderer {
	private static final Logger log = Logger.getLogger("Bresenham");	
	
	public LineRendererBresenham() {
		super();
	}
	
	public LineRendererBresenham(Color color) {
		super(color);
	}
	
	@Override
	protected void leadByX(Line line, Image image) {
		final int x1 = line.getOrigin().getX();
		final int y1 = line.getOrigin().getY();
		final int x2 = line.getEnd().getX();
		final int y2 = line.getEnd().getY();
		final int dx = x2-x1;
		final int dy = y2-y1;
		final int k1 = 2*dy;
		final int k2 = 2*(dy-dx);		
		int p = 2*dy-dx;
		int y = y1;
		if (log.isDebugEnabled()) {
			log.debug("======================");
			log.debug("x1: " + x1);
			log.debug("y1: " + y1);
			log.debug("x2: " + x2);
			log.debug("y2: " + y2);
			log.debug("dx: " + dx);
			log.debug("dy: " + dy);
			log.debug("k1: " + k1);
			log.debug("k2: " + k2);
		}
		for (int i=x1; i<=x2; i++) {
			if (log.isDebugEnabled()) {
				log.debug("-----------------------");
				log.debug("x: " + i);
				log.debug("y: " + y);	
				log.debug("p: " + p);		
			}
			image.pixel(i, y, getColor());
			if(p<0) {
				p = p+k1;
				if (log.isDebugEnabled()) {
					log.debug("p = p+k1: " + p);
				}
			} else {
				p = p+k2;
				y = y+1;
				if (log.isDebugEnabled()) {
					log.debug("p = p+k2: " + p);
				}
			}					
		}		
	}

	@Override
	protected void leadByY(Line line, Image image) {
		final int x1 = line.getOrigin().getX();
		final int y1 = line.getOrigin().getY();
		final int x2 = line.getEnd().getX();
		final int y2 = line.getEnd().getY();
		final int dx = x2-x1;
		final int dy = y2-y1;
		final int k1 = 2*dx;
		final int k2 = 2*(dx-dy);		
		int p = 2*dx-dy;
		int x = x1;
		if (log.isDebugEnabled()) {
			log.debug("======================");
			log.debug("x1: " + x1);
			log.debug("y1: " + y1);
			log.debug("x2: " + x2);
			log.debug("y2: " + y2);
			log.debug("dx: " + dx);
			log.debug("dy: " + dy);
			log.debug("k1: " + k1);
			log.debug("k2: " + k2);
		}
		for (int i=y1; i<=y2; i++) {
			if (log.isDebugEnabled()) {
				log.debug("-----------------------");
				log.debug("x: " + x);
				log.debug("y: " + i);	
				log.debug("p: " + p);		
			}
			image.pixel(x, i, getColor());
			if(p<0) {
				p = p+k1;
				if (log.isDebugEnabled()) {
					log.debug("p = p+k1: " + p);
				}
			} else {
				p = p+k2;
				x = x+1;
				if (log.isDebugEnabled()) {
					log.debug("p = p+k2: " + p);
				}
			}					
		}		
	}
}
