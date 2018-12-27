package h3g;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import h3df.Utils;
import h3g.view.CameraPanel;
import h3g.view.Point3DListPanel;
import solids.Axis;
import solids.Cube;
import solids.Curve;
import solids.Cylinder;
import solids.Diamond;
import solids.Octahedron;
import solids.Solid;
import solids.Sphere;
import solids.Spire;
import transforms.Cubic;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4RotX;
import transforms.Mat4RotY;
import transforms.Mat4RotZ;
import transforms.Mat4Transl;
import transforms.Point3D;
import transforms.Vec3D;

@SuppressWarnings("serial")
public class H3GApp extends JFrame implements ActionListener {

	private final Point3DListPanel points;
	private final Scene scene;
	
	public H3GApp() {
		super();
		setTitle("H3GApp");
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setLayout(new BorderLayout());
		setSize(1105, 805);	
		
		//points
		JPanel left = new JPanel();		
		left.setLayout(new BorderLayout());		
		add(left, BorderLayout.WEST);		
		
		//scene
		scene = new Scene();
		add(scene, BorderLayout.CENTER);
		
		points = new Point3DListPanel();		
		left.add(points, BorderLayout.NORTH);		
		left.add(new ControlPanel(), BorderLayout.CENTER);		
		
		
		points.shuffle();	
		prepareScene();
		startShow();
		//pack();
	}	
	
	private class ControlPanel extends JPanel {
		
		public ControlPanel() {
			super();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//			add(new CameraPanel(scene));
			
			JButton shuffle = new JButton("shuffle");
			shuffle.addActionListener(e -> {
				points.shuffle();				
				prepareScene();
			});
			add(shuffle);
			
		}
	}
	
	private void prepareScene() {
		scene.clear();
		List<Point3D> ps = points.getPoints();
		Solid solid1 = solid(0, ps.get(0));
		scene.add(solid1, rotation(solid1));
		Solid solid2 = solid(1, ps.get(1));
		scene.add(solid2, rotation(solid2));
		Solid solid3 = solid(2, ps.get(2));
		scene.add(solid3, rotation(solid3));
		Solid solid4 = solid(3, ps.get(3));
		scene.add(solid4, rotation(solid4));
		Solid solid5 = solid(4, ps.get(4));
		scene.add(solid5, rotation(solid5));
		Solid solid6 = solid(5, ps.get(5));
		scene.add(solid6, rotation(solid6));		
		scene.add(new Axis(10.0));
		
//		scene.add(curve(Cubic.BEZIER));
//		scene.add(curve(Cubic.BEZIER, 1));
//		scene.add(curve(Cubic.BEZIER, 2));
//		scene.add(curve(Cubic.BEZIER, 3));
//		scene.add(curve(Cubic.COONS));
//		scene.add(curve(Cubic.FERGUSON));
		bezier();
	}
	
	private Solid solid(int index, Point3D initialPosition) { 
		Solid s = new Sphere();
		switch (index) {
			case 0 :
				s = new Sphere();
				break;
			case 1 :
				s = new Cube();
				break;
			case 2 :
				s = new Spire();
				break;
			case 3 :
				s = new Cylinder();
				break;
			case 4 :
				s = new Octahedron();
				break;
			case 5 :
				s = new Diamond();
				break;
		}
		s.transform(new Mat4Transl(initialPosition.ignoreW()));
		return s;
	}
	
	private Mat4 rotation(Solid solid) {
		return //new Mat4Identity();
			new Mat4Transl(-solid.getCentroid().getX(), -solid.getCentroid().getY(), -solid.getCentroid().getZ()).mul(	
			new Mat4RotZ(Utils.randomDouble(Math.PI / 100))).mul(
			new Mat4RotY(Utils.randomDouble(Math.PI / 100))).mul(
			new Mat4RotX(Utils.randomDouble(Math.PI / 100))).mul(
			new Mat4Transl(solid.getCentroid().getX(), solid.getCentroid().getY(), solid.getCentroid().getZ()));
	}
	
	private void bezier() {
		scene.add(curve(Cubic.BEZIER, new Point3D[]{
			points.getPoints().get(0),
			points.getPoints().get(1),
			points.getPoints().get(2),
			points.getPoints().get(3)
		}));
		Point3D p2 = points.getPoints().get(2);
		Point3D p3 = points.getPoints().get(3);
		scene.add(curve(Cubic.BEZIER, new Point3D[]{
			points.getPoints().get(3),
			p3.add(new Point3D(p3.getX()-p2.getX(), p3.getY()-p2.getY(), p3.getZ()-p2.getZ())),
			points.getPoints().get(4),
			points.getPoints().get(5)
		}));
	}	
	
	private void coons() {
		scene.add(curve(Cubic.COONS, new Point3D[]{
			points.getPoints().get(0),
			points.getPoints().get(0),
			points.getPoints().get(0),
			points.getPoints().get(1)
		}));		
		scene.add(curve(Cubic.COONS, new Point3D[]{
			points.getPoints().get(0),
			points.getPoints().get(0),
			points.getPoints().get(1),
			points.getPoints().get(2)
		}));		
		scene.add(curve(Cubic.COONS, new Point3D[]{
			points.getPoints().get(0),
			points.getPoints().get(1),
			points.getPoints().get(2),
			points.getPoints().get(3)
		}));
		scene.add(curve(Cubic.COONS, new Point3D[]{
			points.getPoints().get(1),
			points.getPoints().get(2),
			points.getPoints().get(3),
			points.getPoints().get(4)
		}));
		scene.add(curve(Cubic.COONS, new Point3D[]{
			points.getPoints().get(2),
			points.getPoints().get(3),
			points.getPoints().get(4),
			points.getPoints().get(5)
		}));
		scene.add(curve(Cubic.COONS, new Point3D[]{
			points.getPoints().get(3),
			points.getPoints().get(4),
			points.getPoints().get(5),
			points.getPoints().get(5)
		}));
		scene.add(curve(Cubic.COONS, new Point3D[]{
			points.getPoints().get(4),
			points.getPoints().get(5),
			points.getPoints().get(5),
			points.getPoints().get(5)
		}));
	}
	
	private void ferguson() {
		scene.add(curve(Cubic.FERGUSON, new Point3D[]{
			points.getPoints().get(0),
			points.getPoints().get(1),
			points.getPoints().get(1),
			points.getPoints().get(2)
		}));
		
		scene.add(curve(Cubic.FERGUSON, new Point3D[]{
			points.getPoints().get(1),
			points.getPoints().get(2),
			points.getPoints().get(2),
			points.getPoints().get(3),
		}));
		
		scene.add(curve(Cubic.FERGUSON, new Point3D[]{
			points.getPoints().get(2),
			points.getPoints().get(3),
			points.getPoints().get(3),
			points.getPoints().get(4),
		}));
		
		scene.add(curve(Cubic.FERGUSON, new Point3D[]{
			points.getPoints().get(3),
			points.getPoints().get(4),
			points.getPoints().get(4),
			points.getPoints().get(5),
		}));
		
		scene.add(curve(Cubic.FERGUSON, new Point3D[]{
			points.getPoints().get(4),
			points.getPoints().get(5),
			points.getPoints().get(5),
			points.getPoints().get(0)
		}));		
		
		scene.add(curve(Cubic.FERGUSON, new Point3D[]{
			points.getPoints().get(5),
			points.getPoints().get(0),
			points.getPoints().get(0),
			points.getPoints().get(1)
		}));	
	}
	
	private Curve curve(Mat4 baseMat) {
		return curve(baseMat, new Point3D[]{
			points.getPoints().get(0),
			points.getPoints().get(1),
			new Point3D(),
			points.getPoints().get(2)
		});
	}
	
	private Curve curve(Mat4 baseMat, Point3D[] points) {
//		List<Point3D> ps = new ArrayList<>(); 
//		ps.add(points.getPoints().get(0));		
//		ps.add(points.getPoints().get(1));
//		ps.add(new Point3D());
//		ps.add(points.getPoints().get(2));
		
//		ps = ps.subList(index, ps.size());
		Cubic cubic = new Cubic(baseMat, points);
		List<Point3D> result = new ArrayList<>();
		for (double d=0.0; d<=2.0; d+=0.01) {
			result.add(cubic.compute(d));
		}
		return new Curve(result);
	}
	
	private void startShow() {
		new Timer(1000/60, this).start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		scene.actionPerformed(e);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new H3GApp().setVisible(true);
		});
	}	
}
