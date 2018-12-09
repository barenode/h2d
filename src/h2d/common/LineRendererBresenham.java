package h2d.common;

import java.awt.Color;

public class LineRendererBresenham extends LineRenderer {
	
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
		for (int i=x1; i<=x2; i++) {
			image.pixel(i, y, getColor());
			if(p<0) {
				p = p+k1;
			} else {
				p = p+k2;
				y = y+1;
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
		for (int i=y1; i<=y2; i++) {
			image.pixel(x, i, getColor());
			if(p<0) {
				p = p+k1;
			} else {
				p = p+k2;
				x = x+1;
			}					
		}		
	}
}
