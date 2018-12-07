package solids;

import java.util.ArrayList;
import java.util.List;

import transforms.Mat4;
import transforms.Point3D;

public abstract class SolidData implements Solid {

    private final List<Point3D> verticies;
    private final List<Integer> indicies;

    public SolidData() {
    	super();
        verticies = new ArrayList<>();
        indicies = new ArrayList<>();
    }

    @Override
    public List<Point3D> getVerticies() {
        return verticies;
    }

    @Override
    public List<Integer> getIndicies() {
        return indicies;
    }
    
    public void transform(Mat4 mat) {
    	verticies.stream().forEach(p -> p.mul(mat));
    }
}
