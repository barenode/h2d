package solids;

import java.awt.Color;
import java.util.List;

import transforms.Mat4;
import transforms.Point3D;

public interface Solid {

    List<Point3D> getVerticies();

    List<Integer> getIndicies();

    void transform(Mat4 mat);
    
    default Color getColorByEdge(int index){
        return Color.BLACK;
    }
    
    
}
