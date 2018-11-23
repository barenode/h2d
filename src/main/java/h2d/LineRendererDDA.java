package h2d;

public class LineRendererDDA extends LineRenderer {
	
	public LineRendererDDA() {
		super();
	}

	@Override
	protected void leadByX(Line line, Image image) {
		final int x1 = line.getOrigin().getX();
		final int y1 = line.getOrigin().getY();
		final int x2 = line.getEnd().getX();
		final float k = line.getTangent(); 
		float y = y1;			
		for (int x = x1; x <= x2; x++) {
			image.pixel(x, (int)y, line.getColor());
			y += k;
		}		
	}

	@Override
	protected void leadByY(Line line, Image image) {
		final int x1 = line.getOrigin().getX();
		final int y1 = line.getOrigin().getY();
		final int y2 = line.getEnd().getY();
		float k = line.getTangent();
		//g is zero for infinity tangent
		final float g = 1/k;				
		float x = x1;				
		for (int y = y1; y <= y2; y++) {
			image.pixel((int)x, y, line.getColor());
			x += g;
		}
	}	
}
