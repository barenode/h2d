package h3g;

import static java.lang.Math.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import h3df.Utils;
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
import transforms.Mat4RotX;
import transforms.Mat4RotY;
import transforms.Mat4RotZ;
import transforms.Mat4Transl;
import transforms.Point3D;

@SuppressWarnings("serial")
public class H3GApp extends JFrame implements ActionListener {

	private final Point3DListPanel points;
	private final Scene scene;
	private final Map<Solid, Mat4> solids = new HashMap<>();
	
	private boolean perspectiveEnabled = true;
	private boolean bezierCurve = true;
	private boolean coonsCurve = false;
	private boolean fergusonCurve = false;
	private boolean hypercircleCurve = false;
	
	public H3GApp() {
		super();
		setTitle("H3D");
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
		points.setPointUpdatedListener(e->{
			prepareSolids();
			adjustScene();
		});
		left.add(points, BorderLayout.NORTH);		
		left.add(new ControlPanel(), BorderLayout.CENTER);		
		
		initScene();
		startShow();
	}	
	
	private void initScene() {
		points.shuffle();	
		prepareSolids();
		adjustScene();
	}
	
	private class ControlPanel extends JPanel {
		
		public ControlPanel() {
			super();
			setLayout(new GridLayout(0, 1));
			//shuffle
			JPanel shufflePanel = new JPanel();
			JButton shuffle = new JButton("Zamíchat");
			shuffle.addActionListener(e -> {
				initScene();
			});
			shufflePanel.add(shuffle);
			add(shufflePanel);
			
			//projection			
			JPanel projectionPanel = new JPanel();
			ButtonGroup projectionGroup = new ButtonGroup();
			projectionPanel.setBorder(BorderFactory.createTitledBorder("Projekce")); 
			projectionPanel.setLayout(new GridLayout(0, 1));
			//orthogonal
			JRadioButton  orhogonalProjection = new JRadioButton("Ortogonalní", !perspectiveEnabled);
			orhogonalProjection.addItemListener(e -> {
				perspectiveEnabled = e.getStateChange() == 1 ? false : true;
				adjustScene();
			});
			projectionGroup.add(orhogonalProjection);
			projectionPanel.add(orhogonalProjection);

			//perspective
			JRadioButton perspectiveProjection = new JRadioButton("Perspektivní", perspectiveEnabled);			
			perspectiveProjection.addItemListener(e -> {
				perspectiveEnabled = e.getStateChange() == 1 ? true : false;
				adjustScene();
			});
			projectionGroup.add(perspectiveProjection);
			projectionPanel.add(perspectiveProjection);
			add(projectionPanel);
			
			//curves
			JPanel curvePanel = new JPanel();
			curvePanel.setBorder(BorderFactory.createTitledBorder("Křivky")); 
			curvePanel.setLayout(new GridLayout(0, 1));
			//bezier
			JCheckBox bezier = new JCheckBox("Bezier", bezierCurve);				
			bezier.addItemListener(e -> {
				bezierCurve = e.getStateChange() == 1 ? true : false;
				adjustScene();
			});
			curvePanel.add(bezier);
			//coons
			JCheckBox coons = new JCheckBox("Coons", coonsCurve);				
			coons.addItemListener(e -> {
				coonsCurve = e.getStateChange() == 1 ? true : false;
				adjustScene();
			});
			curvePanel.add(coons);
			//ferguson
			JCheckBox ferguson = new JCheckBox("Ferguson", fergusonCurve);		
			ferguson.addItemListener(e -> {
				fergusonCurve = e.getStateChange() == 1 ? true : false;
				adjustScene();
			});
			curvePanel.add(ferguson);	
			//hypercircle
			JCheckBox hypercircle = new JCheckBox("Hyperkružnice", hypercircleCurve);		
			hypercircle.addItemListener(e -> {
				hypercircleCurve = e.getStateChange() == 1 ? true : false;
				adjustScene();
			});
			curvePanel.add(hypercircle);
			add(curvePanel);
			
			//hint
			JPanel hintPanel = new JPanel();
			hintPanel.setBorder(BorderFactory.createTitledBorder("Ovládání")); 
			hintPanel.setLayout(new GridLayout(0, 1));
			hintPanel.add(new Label("Nutné kliknout na plátno pro získání fokusu!"));
			hintPanel.add(new Label("A, W, S, D - pohyb."));
			hintPanel.add(new Label("Mys - úhel pohledu."));
			add(hintPanel);
		}
	}
	
	private void prepareSolids() {
		solids.clear();		
		List<Point3D> ps = points.getPoints();
		Solid solid1 = solid(0, ps.get(0));
		solids.put(solid1, rotation(solid1));
		Solid solid2 = solid(1, ps.get(1));
		solids.put(solid2, rotation(solid2));
		Solid solid3 = solid(2, ps.get(2));
		solids.put(solid3, rotation(solid3));
		Solid solid4 = solid(3, ps.get(3));
		solids.put(solid4, rotation(solid4));
		Solid solid5 = solid(4, ps.get(4));
		solids.put(solid5, rotation(solid5));
		Solid solid6 = solid(5, ps.get(5));
		solids.put(solid6, rotation(solid6));	
	}
	
	private void adjustScene() {
		scene.clear();
		//solids
		for (Map.Entry<Solid, Mat4> entry : solids.entrySet()) {
			scene.add(entry.getKey(), entry.getValue());
		}
		//axis
		scene.add(new Axis(10.0));
		//projection
		if (perspectiveEnabled) {
			scene.perspectiveProjection();
		} else {
			scene.orthogonalProjection();
		}		
		//curves
		if (bezierCurve) {
			bezier();
		}
		if (coonsCurve) {
			coons();
		}
		if (fergusonCurve) {
			ferguson();
		}
		if (hypercircleCurve) {
			hypercircle();
		}		
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
	
	/**
	 * Random rotation is assigned to each axis separately.
	 */
	private Mat4 rotation(Solid solid) {
		return
			new Mat4Transl(-solid.getCentroid().getX(), -solid.getCentroid().getY(), -solid.getCentroid().getZ()).mul(	
			new Mat4RotZ(Utils.randomDouble(Math.PI / 100))).mul(
			new Mat4RotY(Utils.randomDouble(Math.PI / 100))).mul(
			new Mat4RotX(Utils.randomDouble(Math.PI / 100))).mul(
			new Mat4Transl(solid.getCentroid().getX(), solid.getCentroid().getY(), solid.getCentroid().getZ()));
	}
	
	private void hypercircle() {
		for (double n = 0.5; n<3.0; n+=.5) {
			hypercircle(n);
		}		
	}
	
	private void hypercircle(double n) {
		double angle = PI/2;
		List<Point3D> q1 = new ArrayList<>();
		List<Point3D> q2 = new ArrayList<>();
		List<Point3D> q3 = new ArrayList<>();
		List<Point3D> q4 = new ArrayList<>();
		for (double p = 0.0; p<=angle; p+=0.01) {
			double x = 0;
			double y = 3.0*pow(abs(sin(p)), n);
			double z = 3.0*pow(abs(cos(p)), n);
			q1.add(new Point3D(x, +y, +z));
			q2.add(new Point3D(x, +y, -z));
			q3.add(new Point3D(x, -y, +z));
			q4.add(new Point3D(x, -y, -z));
		}
		scene.add(new Curve(q1));
		scene.add(new Curve(q2));
		scene.add(new Curve(q3));
		scene.add(new Curve(q4));
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
	
	private Curve curve(Mat4 baseMat, Point3D[] points) {
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
