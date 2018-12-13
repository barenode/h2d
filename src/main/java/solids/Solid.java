package solids;

import static java.util.stream.Collectors.toList;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import h3df.Pair;
import transforms.Mat4;
import transforms.Point3D;

public interface Solid {
	Logger logger = Logger.getLogger(Solid.class);
	
    List<Point3D> getVerticies();

    List<Integer> getIndicies();    
    
    Mat4 getTransformation();
    
    default Color getColorByEdge(int index) {
        return Color.BLACK;
    }
    
    default void transform() {
    	transform(getTransformation());
    }
    
    default void transform(Mat4 mat) {
    	List<Point3D> transformed = getVerticies().stream().map(p -> {
    		Point3D t = p.mul(mat);
    		logger.debug(p + " > " + t);
    		return t;
    	}).collect(toList());
    	getVerticies().clear();
    	getVerticies().addAll(transformed);
    }
    
    default List<Pair<Point3D>> getEdges() {
    	List<Pair<Point3D>> result = new ArrayList<>();
    	for (int i = 0; i < getIndicies().size(); i += 2) {
    		result.add(Pair.of(getVerticies().get(i), getVerticies().get(i=1)));
    	}
    	return result;
    }    
}
