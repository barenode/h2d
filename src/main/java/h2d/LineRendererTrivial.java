package h2d;

public class LineRendererTrivial extends LineRenderer {	
	
	public LineRendererTrivial() {
		super();
	}	
	
	@Override
	protected void leadByX(Line line, Image image) {
		final int x1 = line.getOrigin().getX();
		final int x2 = line.getEnd().getX();
		final float k = line.getTangent();
		final float q = line.getYIntercept();
		for (int x = x1; x <= x2; x++) {
        	int y = (int)(q+x*k);
        	image.pixel(x, y, line.getColor());
        }
	}
	
	@Override
	protected void leadByY(Line line, Image image) {		
		final int x1 = line.getOrigin().getX();
		final int y1 = line.getOrigin().getY();
		final int y2 = line.getEnd().getY();
		final float k = line.getTangent();
		if (Float.isInfinite(k)) {//zero delta x
			for (int y = y1; y <= y2; y++) {
				image.pixel(x1, y, line.getColor());
			}
		} else {
			final float q = line.getYIntercept();
			for (int y = y1; y <= y2; y++) {
	    		int x = (int)((y-q)/k);
	    		image.pixel(x, y, line.getColor());
	    	}
		}	
	}
}
