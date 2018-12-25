package h3g.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import h3df.Utils;
import transforms.Point3D;

@SuppressWarnings("serial")
public class Point3DListPanel extends JPanel implements ActionListener {
	
	private final Point3DPanel point1;
	private final Point3DPanel point2;
	private final Point3DPanel point3;
	private final Point3DPanel point4;
	private final Point3DPanel point5;
	private final Point3DPanel point6;
	
	private ActionListener listener;
	
	public Point3DListPanel() {
		super();
		setSize(200, 600);	
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//1
		point1 = new Point3DPanel(1, "koule");
		point1.setPointUpdatedListener(this);
		add(point1);
		//2
		point2 = new Point3DPanel(2, "krychle");
		point2.setPointUpdatedListener(this);
		add(point2);
		//3
		point3 = new Point3DPanel(3, "jehlan");
		point3.setPointUpdatedListener(this);
		add(point3);
		//4
		point4 = new Point3DPanel(4, "valec");
		point4.setPointUpdatedListener(this);
		add(point4);
		//5
		point5 = new Point3DPanel(5, "osmisten");
		point5.setPointUpdatedListener(this);
		add(point5);
		//6
		point6 = new Point3DPanel(6, "diamant");
		point6.setPointUpdatedListener(this);
		add(point6);
	}
	
	public void shuffle() {
		point1.setPoint(randomPoint());
		point2.setPoint(randomPoint());
		point3.setPoint(randomPoint());
		point4.setPoint(randomPoint());
		point5.setPoint(randomPoint());
		point6.setPoint(randomPoint());
	}
	
	private Point3D randomPoint() {
		return new Point3D(
			Utils.randomDouble(9.0),
			Utils.randomDouble(9.0),
			Utils.randomDouble(9.0)
		);
	}
	
	public List<Point3D> getPoints() {
		return Arrays.asList(
			point1.getPoint(),
			point2.getPoint(),
			point3.getPoint(),
			point4.getPoint(),
			point5.getPoint(),
			point6.getPoint()
		);
	}
	
	public void setPointUpdatedListener(ActionListener listener) {
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if (listener!=null) {
			listener.actionPerformed(e);
		}
	}
}
