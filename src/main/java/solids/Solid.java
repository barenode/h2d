package solids;

import static java.util.stream.Collectors.toList;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import h2d.common.Line;
import h2d.common.LineRendererDDA;
import h2d.common.Renderer;
import h3df.Pair;
import transforms.Mat4;
import transforms.Point3D;

public interface Solid {
	Logger logger = Logger.getLogger(Solid.class);
	Renderer<Line> DEFAULT_RENDERER = new LineRendererDDA(Color.BLACK);
	
    List<Point3D> getVerticies();

    List<Integer> getIndicies();    
    
    Mat4 getTransformation();
    
    Point3D getCentroid();
    
    default Renderer<Line> getEdgeRenderer(int index) {
    	return DEFAULT_RENDERER;
    }
    
    default void transform() {
    	transform(getTransformation());
    }
    
    default void transform(Mat4 mat) {
    	List<Point3D> transformed = getVerticies().stream().map(p -> {
    		Point3D t = p.mul(mat);
//    		logger.debug(p + " > " + t);
    		return t;
    	}).collect(toList());
    	getVerticies().clear();
    	getVerticies().addAll(transformed);
    }
    
    default List<Pair<Point3D>> getEdges() {
    	List<Pair<Point3D>> result = new ArrayList<>();
    	for (int i = 0; i < getIndicies().size(); i += 2) {
    		result.add(Pair.of(
    			getVerticies().get(getIndicies().get(i)), 
    			getVerticies().get(getIndicies().get(i+1))));
    	}
    	return result;
    }    
}
