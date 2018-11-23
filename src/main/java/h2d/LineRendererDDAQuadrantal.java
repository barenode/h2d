package h2d;

import java.awt.Color;

import org.apache.log4j.Logger;

public class LineRendererDDAQuadrantal implements Renderer<Line> {
	private static final Logger log = Logger.getLogger("DDAQuadrantal");
	
	private final Color color;
	
	public LineRendererDDAQuadrantal(Color color) {
		super();
		this.color = color;
	}
	
	@Override
	public void render(Line line, Image image) {
		final int x1 = line.getOrigin().getX();
		final int y1 = line.getOrigin().getY();
		final int x2 = line.getEnd().getX();
		final int y2 = line.getEnd().getY();
		final int absdx = Math.abs(x2-x1);
		final int absdy = Math.abs(y2-y1);
		final int xStep, yStep;
		if (x1>x2) {
			xStep = -1;
		} else {
			xStep = 1;
		}
		if (y1>y2) {
			yStep = -1;
		} else {
			yStep = 1;
		}
		int x = x1;
		int y = y1;
		int err = 0;
		int xi = 0;
		int yi = 0;
		if (log.isDebugEnabled()) {
			log.debug("======================");
			log.debug("absdx: " + absdx);
			log.debug("absdy: " + absdy);
			log.debug("xStep: " + xStep);
			log.debug("yStep: " + yStep);
		}		
		while(xi<=absdx && yi<=absdy) {
			if (log.isDebugEnabled()) {
				log.debug("-----------------------");
				log.debug("x: " + x);
				log.debug("y: " + y);
				log.debug("err: " + err);
			}
			image.pixel(x, y, color);
			if(absdy==0 || err>0) {
				x+=xStep;
				xi++;
				err-=absdy;
			} else {
				y+=yStep;
				yi++;
				err+=absdx;
			}
		}
	}	
}
