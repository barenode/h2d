package h2d;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class H2DApp extends JFrame {

	private final H2DCanvas canvas;
	
	public H2DApp() {
		super();
		setTitle("H2D");
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setLayout(new BorderLayout());
		setSize(1200, 850);
		//tool bar
		Toolbar toolbar = new Toolbar();
		add(toolbar, BorderLayout.PAGE_START);
		//controls
		add(new Excersises(), BorderLayout.WEST);
		//canvas
		canvas = new H2DCanvas();
		add(canvas, BorderLayout.CENTER);
		canvas.setEventListener(new NAngleController());
		//pack();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new H2DApp().setVisible(true);
		});
	}
	
	static class Toolbar extends JToolBar {
		public Toolbar() {
			super();
			setLayout(new FlowLayout(FlowLayout.LEADING));
		}
	}
	
	static class Excersises extends JPanel {
		public Excersises() {
			super();		
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			ButtonGroup controlButtons = new ButtonGroup();
			
			//EXCERSISE 1
			JPanel excersise1 = new JPanel();
			excersise1.setLayout(new BoxLayout(excersise1, BoxLayout.Y_AXIS));
			excersise1.setBorder(BorderFactory.createTitledBorder("Úloha 1")); 
			add(excersise1);
			//line			
			JToggleButton lineControllerButton = new JToggleButton("Kreslení úsečky");
			excersise1.add(lineControllerButton);
			controlButtons.add(lineControllerButton);
			//polygon
			JToggleButton polygonControllerButton = new JToggleButton("N-úhelník");
			excersise1.add(polygonControllerButton);
			controlButtons.add(polygonControllerButton);
			//regular polygon
			JToggleButton regularPolygonControllerButton = new JToggleButton("Pravidelný n-úhelník");
			excersise1.add(regularPolygonControllerButton);
			controlButtons.add(regularPolygonControllerButton);
		
			
			//EXCERSISE 1
			JPanel excersise2 = new JPanel();
			excersise2.setLayout(new BoxLayout(excersise2, BoxLayout.Y_AXIS));
			excersise2.setBorder(BorderFactory.createTitledBorder("Úloha 2")); 
			add(excersise2);
			//seed fill
			JToggleButton seedFillControllerButton = new JToggleButton("Semínkové vyplnění");
			excersise2.add(seedFillControllerButton);
			controlButtons.add(seedFillControllerButton);
			//polygon clip
			JToggleButton polyginClipControllerButton = new JToggleButton("Ořezání n-úhelníku");
			excersise2.add(polyginClipControllerButton);
			controlButtons.add(polyginClipControllerButton);
		}
	}
	
	static class Excersise1 extends JPanel {
		
		public Excersise1() {
			super();			
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBorder(BorderFactory.createTitledBorder("Úloha 1")); 
			add(new JButton("Kreslení úsečky"));
			add(new JButton("N-úhelník"));
			add(new JButton("Pravidelný n-úhelník"));
		}			
	}
	
	static class Excersise2 extends JPanel {
		
		public Excersise2() {
			super();		
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBorder(BorderFactory.createTitledBorder("Úloha 2")); 
			add(new JButton("Semínkové vyplnění"));
			add(new JButton("Ořezání n-úhelníku"));
		}			
	}
}
