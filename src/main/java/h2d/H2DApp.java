package h2d;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class H2DApp extends JFrame {

	public H2DApp() {
		super();
		setTitle("H2D");
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setLayout(new BorderLayout());
		setSize(600, 600);
		//tool bar
		Toolbar toolbar = new Toolbar();
		add(toolbar, BorderLayout.PAGE_START);
		//controls
		add(new Excersises(), BorderLayout.WEST);
		//canvas
		H2DCanvas canvas = new H2DCanvas();
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
			add(new Excersise1());
			add(new Excersise2());
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
