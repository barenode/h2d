package h2d.common;

import java.awt.Color;

public class LineRendererBresenhamRevisited extends LineRenderer {	
	
	public LineRendererBresenhamRevisited() {
		super();
	}
	
	public LineRendererBresenhamRevisited(Color color) {
		super(color);
	}

	@Override
	protected void leadByX(Line line, Image image) {
		final int x1 = line.getOrigin().getX();
		final int y1 = line.getOrigin().getY();
		final int x2 = line.getEnd().getX();
		final int y2 = line.getEnd().getY();
		final int dx = x2-x1;
		int dy = y2-y1;	
		int yi = 1;
		if (dy < 0) {
		    yi = -1;
		    dy = -dy;
		}	
		int D = 2*dy - dx;
		int y = y1;
		for (int x=x1; x<=x2; x++) {		
			image.pixel(x, y, getColor());
			if (D>0) {
		       y = y + yi;
		       D = D - 2*dx;
			}	
		    D = D + 2*dy;
		}
	}

	@Override
	protected void leadByY(Line line, Image image) {
		final int x1 = line.getOrigin().getX();
		final int y1 = line.getOrigin().getY();
		final int x2 = line.getEnd().getX();
		final int y2 = line.getEnd().getY();
		int dx = x2-x1;
		final int dy = y2-y1;		
		int xi = 1;		
		if (dx < 0) {
			xi = -1;
			dx = -dx;
		}		
		int D = 2*dx - dy;
		int x = x1;
		for (int y=y1; y<=y2; y++) {
			image.pixel(x, y, getColor());
			if (D>0) {
		       x = x + xi;
		       D = D - 2*dy;
			}
			D = D + 2*dx;
		}
	}	
}
