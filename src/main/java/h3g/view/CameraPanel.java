package h3g.view;

import java.awt.Label;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import h3df.Utils;
import h3g.Scene;
import transforms.Point3D;
import transforms.Vec3D;

@SuppressWarnings("serial")
public class CameraPanel extends JPanel {
	
	
	public CameraPanel(Scene scene) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ValueControl posX = new ValueControl("pos x", scene.getCamera().getPosition().getX(), 0.5);
		posX.setActionListener(e->{
			scene.setCamera(scene.getCamera().withPosition(scene.getCamera().getPosition().withX((Double)e.getSource())));
		});
		add(posX);
		
		ValueControl posY = new ValueControl("pos y", scene.getCamera().getPosition().getY(), 0.5);
		posY.setActionListener(e->{
			scene.setCamera(scene.getCamera().withPosition(scene.getCamera().getPosition().withY((Double)e.getSource())));
		});
		add(posY);
		
		ValueControl posZ = new ValueControl("pos z", scene.getCamera().getPosition().getZ(), 0.5);
		posZ.setActionListener(e->{
			scene.setCamera(scene.getCamera().withPosition(scene.getCamera().getPosition().withZ((Double)e.getSource())));
		});
		add(posZ);
		
		ValueControl azimuth = new ValueControl("azimuth", scene.getCamera().getAzimuth(), 0.1);
		azimuth.setActionListener(e->{
			scene.setCamera(scene.getCamera().withAzimuth((Double)e.getSource()));
		});
		add(azimuth);
		
		
		ValueControl zenith = new ValueControl("zenith", scene.getCamera().getZenith(), 0.1);
		zenith.setActionListener(e->{
			scene.setCamera(scene.getCamera().withZenith((Double)e.getSource()));
		});
		add(zenith);
		
		
//		Point3DPanel position = new Point3DPanel(0);
//		position.setPoint(toPoint(scene.getCamera().getPosition()));
//		position.setPointUpdatedListener(e -> {
//			scene.setCamera(scene.getCamera().withPosition(position.getPoint().ignoreW()));
//		});
//		add(position);
//		
//		add(new Label("zenith: "));
//		JTextField zenith = new JTextField(String.valueOf(scene.getCamera().getZenith()), 5);
//		add(zenith);
//		zenith.getDocument().addDocumentListener(new DocumentListener() {			
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//				try {
//					scene.setCamera(scene.getCamera().withZenith(Utils.parseDouble(zenith.getText())));
//				} catch (Exception ex) {}		
//			}
//		
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				try {
//					scene.setCamera(scene.getCamera().withZenith(Utils.parseDouble(zenith.getText())));
//				} catch (Exception ex) {}		
//			}
//		
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				try {
//					scene.setCamera(scene.getCamera().withZenith(Utils.parseDouble(zenith.getText())));
//				} catch (Exception ex) {}		
//			}	
//		});
//		
//		add(new Label("azimuth: "));
//		JTextField azimuth = new JTextField(String.valueOf(scene.getCamera().getAzimuth()), 5);
//		add(azimuth);
//		azimuth.getDocument().addDocumentListener(new DocumentListener() {			
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//				try {
//					scene.setCamera(scene.getCamera().withAzimuth(Utils.parseDouble(azimuth.getText())));
//				} catch (Exception ex) {}		
//			}
//		
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				try {
//					scene.setCamera(scene.getCamera().withAzimuth(Utils.parseDouble(azimuth.getText())));
//				} catch (Exception ex) {}		
//			}
//		
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				try {			
//					scene.setCamera(scene.getCamera().withAzimuth(Utils.parseDouble(azimuth.getText())));
//				} catch (Exception ex) {}		
//			}	
//		});
	}
	
	private Point3D toPoint(Vec3D vec) {
		return new Point3D(vec.getX(), vec.getY(), vec.getZ());
	}
}
