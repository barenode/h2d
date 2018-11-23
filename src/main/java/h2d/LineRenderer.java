package h2d;

public abstract class LineRenderer implements Renderer<Line> {
	
	public LineRenderer() {
		super();
	}
	
	protected abstract void leadByX(Line line, Image image);
	
	protected abstract void leadByY(Line line, Image image);

	@Override
	public final void render(Line line, Image image) {	
		final float k = line.getTangent();
        if (Math.abs(k)>1) {//condition pass for zero delta x (infinity tangent)
        	if (line.getOrigin().getY()>line.getEnd().getY()) {
        		leadByY(line.flip(), image);
        	} else {
        		leadByY(line, image);
        	}
        } else {
        	if (line.getOrigin().getX()>line.getEnd().getX()) {
        		leadByX(line.flip(), image);
        	} else {
        		leadByX(line, image);
        	}
        }
	}
}
