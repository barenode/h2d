package solids;

import java.util.ArrayList;
import java.util.List;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

public abstract class SolidBase implements Solid {
	public static final double SIN_60 = Math.sqrt(3.0)/2;	
	
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
	
	protected void addCircle(double radius, double z) {
		int startIndex = getVerticies().size();
		
		getVerticies().add(new Point3D(radius, 0.0, z));    
		getVerticies().add(new Point3D(radius*SIN_60, radius*0.5, z));    
		getVerticies().add(new Point3D(radius*0.5, radius*SIN_60, z));   
		getVerticies().add(new Point3D(0.0, radius, z));  
		
		getVerticies().add(new Point3D(-radius*0.5, radius*SIN_60, z));
		getVerticies().add(new Point3D(-radius*SIN_60, radius*0.5, z));   
		getVerticies().add(new Point3D(-radius, 0.0, z));   
		
		getVerticies().add(new Point3D(-radius*SIN_60, -radius*0.5, z));   
		getVerticies().add(new Point3D(-radius*0.5, -radius*SIN_60, z));
		getVerticies().add(new Point3D(0.0, -radius, z));  
		
		getVerticies().add(new Point3D(radius*0.5, -radius*SIN_60, z));
		getVerticies().add(new Point3D(radius*SIN_60, -radius*0.5, z));  		
		
		getIndicies().add(startIndex+0); getIndicies().add(startIndex+1);
        getIndicies().add(startIndex+1); getIndicies().add(startIndex+2);
        getIndicies().add(startIndex+2); getIndicies().add(startIndex+3);
        getIndicies().add(startIndex+3); getIndicies().add(startIndex+4);
        getIndicies().add(startIndex+4); getIndicies().add(startIndex+5);
        getIndicies().add(startIndex+5); getIndicies().add(startIndex+6);
        getIndicies().add(startIndex+6); getIndicies().add(startIndex+7);
        getIndicies().add(startIndex+7); getIndicies().add(startIndex+8);
        getIndicies().add(startIndex+8); getIndicies().add(startIndex+9);
        getIndicies().add(startIndex+9); getIndicies().add(startIndex+10);
        getIndicies().add(startIndex+10); getIndicies().add(startIndex+11);
        getIndicies().add(startIndex+11); getIndicies().add(startIndex+0);
	}
	
	protected void connectPoints(int startIndex, int endIndex, int offset) {
		for (int i=startIndex; i<=endIndex; i++) {
			getIndicies().add(i); getIndicies().add(i+offset);
		}
	}
	
	protected void connectWithPoint(int startIndex, int endIndex, int pointIndex) {
		for (int i=startIndex; i<=endIndex; i++) {
			getIndicies().add(i); getIndicies().add(pointIndex);
		}
	}
	
}
