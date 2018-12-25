package solids;

import java.awt.Color;

import h2d.common.Line;
import h2d.common.LineRendererDDA;
import h2d.common.Renderer;
import transforms.Point3D;

public class Axis extends SolidBase {
	Renderer<Line> X_RENDERER = new LineRendererDDA(Color.RED);
	Renderer<Line> Y_RENDERER = new LineRendererDDA(Color.GREEN);
	Renderer<Line> Z_RENDERER = new LineRendererDDA(Color.BLUE);
	
    public Axis(double length) {
    	super();
    	getVerticies().add(new Point3D(0, 0, 0)); // 0.
    	getVerticies().add(new Point3D(length, 0, 0)); // 1.
    	getVerticies().add(new Point3D(0, length, 0)); // 2.
        getVerticies().add(new Point3D(0, 0, length)); // 3.

        getIndicies().add(0); getIndicies().add(1);
        getIndicies().add(0); getIndicies().add(2);
        getIndicies().add(0); getIndicies().add(3);
    }

    @Override
    public Renderer<Line> getEdgeRenderer(int index) {
        switch (index){
            case 0: return X_RENDERER;
            case 1: return Y_RENDERER;
            case 2: return Z_RENDERER;
            default: return DEFAULT_RENDERER;
        }
    }

	@Override
	public Point3D getCentroid() {
		return new Point3D(0, 0, 0);
	}
}
