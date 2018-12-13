package solids;

import java.util.ArrayList;
import java.util.List;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

public abstract class SolidBase implements Solid {

    private final List<Point3D> verticies;
    private final List<Integer> indicies;
    
    private Mat4 transformation = new Mat4Identity();

    public SolidBase() {
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

    @Override
	public Mat4 getTransformation() {
		return transformation;
	}

	public void setTransformation(Mat4 transformation) {
		this.transformation = transformation;
	}    
}
