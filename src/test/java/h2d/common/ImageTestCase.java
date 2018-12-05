package h2d.common;

import java.awt.Color;
import java.util.Arrays;

import h2d.common.Circle;
import h2d.common.CircleRendererParametrical;
import h2d.common.ImageImpl;
import h2d.common.Line;
import h2d.common.Point;
import h2d.common.PointRenderer;
import h2d.common.Polygon;
import h2d.common.PolygonRenderer;
import h2d.common.Renderer;
import junit.framework.TestCase;
import transforms.Mat3Rot2D;
import transforms.Mat3RotX;
import transforms.Mat3Scale2D;
import transforms.Mat3Transl2D;

public class ImageTestCase extends TestCase {	

	private static final Line[] lines = new Line[]{
		new Line(0, 0, 10, 10),
		new Line(0, 5, 10, 10),
		new Line(0, 10, 10, 10),
		new Line(0, 15, 10, 10),
		new Line(0, 20, 10, 10),
		new Line(5, 20, 10, 10),
		new Line(10, 20, 10, 10),
		new Line(15, 20, 10, 10),
		new Line(20, 20, 10, 10),
		new Line(20, 15, 10, 10),
		new Line(20, 10, 10, 10),
		new Line(20, 5, 10, 10),
		new Line(20, 0, 10, 10),
		new Line(15, 0, 10, 10),
		new Line(10, 0, 10, 10),
		new Line(5, 0, 10, 10)
	};

	public void test() throws Exception {
		
		ImageImpl image = new ImageImpl(8, 100, 100);
		
//		new PolygonRenderer().render(new RegularPolygon(new Point(40, 40), new Point(50, 45), 5).asPolygon(), image);
		
//		Polygon polygon = new Polygon(Arrays.asList(
//			new Point(5, 5), 
//			new Point(10, 15), 
//			new Point(20, 5),
//			new Point(20, 5),
//			new Point(25, 15),
//			new Point(30, 5),
//			new Point(15, 15)));
//		System.out.println(polygon.getEdges());
//		
//		new PolygonRenderer().render(polygon, image);
//		
//		new PolygonRenderer().render(polygon.transform(new Mat3Scale2D(2, 2)), image);
//		new PolygonRenderer().render(polygon.transform(new Mat3Scale2D(2, 2).mul(new Mat3Transl2D(20, 20))), image);
//		new PolygonRenderer().render(polygon.transform(new Mat3Scale2D(2, 2).mul(new Mat3Transl2D(20, 20)).mul(new Mat3Rot2D(Math.PI/10))), image);
//		new PolygonRenderer().render(polygon.transform(new Mat3Scale2D(3, 1)), image);
//		new PolygonRenderer().render(polygon.transform(new Mat3Scale2D(2, 2).mul(new Mat3Rot2D(Math.PI))), image);
//		new PolygonRenderer().render(polygon.transform(new Mat3Transl2D(20, 20).mul(new Mat3Scale2D(2, 2))), image);
		
//		Renderer<Line> renderer = new LineRendererBresenhamRevisited();
//		for (Line line : lines) {
//			renderer.render(line, image);
//		}
//		renderer.render(new Line(5, 20, 10, 10), image);
		
		//Point p = new Point(10, 10);
		//new PointRenderer().render(p, img);	
		
//		Line line = new Line(5, 5, 15, 15);
//		LineRenderer renderer = new LineRendererBresenham(Color.BLACK);
//		renderer.render(new Line(17, 17, 6, 6), img);	
//		renderer.render(new Line(8, 20, 6, 6), img);	
		
//		renderer.render(new Line(3, 3, 5, 10), img);	
		
		//new LineRendererTrivial().render(new Line(5, 5, 15, 15), img);	
		
//		Point centre = new Point(20, 20);
//		Renderer<Point> pr = new PointRenderer(Color.BLACK);
//		pr.render(centre, image);
//		
//		Circle c = new Circle(centre, 15);
//		
//		Renderer<Circle> cr = new CircleRendererParametrical(Color.BLACK);
//		cr.render(c, image);
		
		image.save("p1");
	}
}
