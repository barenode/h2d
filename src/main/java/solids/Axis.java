package solids;

import java.awt.Color;

import transforms.Point3D;

public class Axis extends SolidBase {

    public Axis() {
    	super();
    	getVerticies().add(new Point3D(0,0,0)); // 0.
    	getVerticies().add(new Point3D(1,0,0)); // 1.
    	getVerticies().add(new Point3D(0,1,0)); // 2.
        getVerticies().add(new Point3D(0,0,1)); // 3.

        getIndicies().add(0); getIndicies().add(1);
        getIndicies().add(0); getIndicies().add(2);
        getIndicies().add(0); getIndicies().add(3);
    }

    @Override
    public Color getColorByEdge(int index) {
        switch (index){
            case 0: return Color.RED;
            case 1: return Color.GREEN;
            case 2: return Color.BLUE;
            default: return Color.BLACK;
        }
    }
}
