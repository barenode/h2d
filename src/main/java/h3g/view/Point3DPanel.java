package h3g.view;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import h3df.Utils;
import transforms.Point3D;

@SuppressWarnings("serial")
public class Point3DPanel extends JPanel {
	
	private final JTextField x;
	private final JTextField y;
	private final JTextField z;
	
	private Point3D point;
	private ActionListener listener;
	
	public Point3DPanel(int index, String shape) {
		super();		
		setBorder(BorderFactory.createTitledBorder("bod " + index + " (" + shape + ")")); 
		add(new Label("x: "));
		x = new JTextField("", 5);
		x.getDocument().addDocumentListener(new Listener());
		add(x);
		add(new Label("y: "));
		y = new JTextField("", 5);
		y.setSize(50, 30);
		y.getDocument().addDocumentListener(new Listener());
		add(y);
		add(new Label("z: "));
		z = new JTextField("", 5);
		z.setSize(50, 30);
		z.getDocument().addDocumentListener(new Listener());
		add(z);
	}
	
	public void setPoint(Point3D point) {
		this.point = point;
		x.setText(Utils.formatDouble(point.getX()));		
		y.setText(Utils.formatDouble(point.getY()));
		z.setText(Utils.formatDouble(point.getZ()));
	}	
	
	public Point3D getPoint() {
		return point;				
	}
	
	public void updatePoint() {
		try {
			Point3D  current = new Point3D(
				Utils.parseDouble(x.getText()),
				Utils.parseDouble(y.getText()),
				Utils.parseDouble(z.getText())
			);
			this.point = current;
			if (listener!=null) {
				listener.actionPerformed(new ActionEvent(this, 0, ""));
			}
		} catch (Exception e) {}		
	}
	
	public void setPointUpdatedListener(ActionListener listener) {
		this.listener = listener;
	}	
	
	private class Listener implements DocumentListener {
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			updatePoint();
		}
	
		@Override
		public void insertUpdate(DocumentEvent e) {
			updatePoint();
		}
	
		@Override
		public void removeUpdate(DocumentEvent e) {
			updatePoint();
		}	
	}
}
