package h2d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import h2d.controller.PolygonIntersectionController;
import h2d.controller.SeedFillController;
import h2d.view.ColorChooserButton;
import h2d.view.H2DCanvas;

@SuppressWarnings("serial")
public class H2DApp extends JFrame {

	private final H2DCanvas canvas;
	private final Settings settings;
	
	public H2DApp() {
		super();
		setTitle("H2D");
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setLayout(new BorderLayout());
		setSize(1200, 850);
		//settings
		settings = new Settings();		
		//controllers
		add(new Excersises(), BorderLayout.WEST);
		//main
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		add(main, BorderLayout.CENTER);
		//tool bar
		Toolbar toolbar = new Toolbar();
		main.add(toolbar, BorderLayout.PAGE_START);
		//canvas
		canvas = new H2DCanvas();
		main.add(canvas, BorderLayout.CENTER);
		canvas.setEventListener(new SeedFillController(settings));
		pack();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new H2DApp().setVisible(true);
		});
	}
	
	class Excersises extends JPanel {
		public Excersises() {
			super();		
			setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			ButtonGroup controlButtons = new ButtonGroup();
			
			//EXCERSISE 2
			JPanel excersise2 = new JPanel();
			excersise2.setLayout(new BoxLayout(excersise2, BoxLayout.Y_AXIS));
			excersise2.setBorder(BorderFactory.createTitledBorder("Úloha 2")); 
			add(excersise2);
			//seed fill
			JToggleButton seedFillControllerButton = new JToggleButton("Semínkové vyplnění");
			seedFillControllerButton.addActionListener(e -> {
				canvas.setEventListener(new SeedFillController(settings));
			});	
			seedFillControllerButton.setSelected(true);
			
			excersise2.add(seedFillControllerButton);
			controlButtons.add(seedFillControllerButton);
			//polygon clip
			JToggleButton polyginClipControllerButton = new JToggleButton("Ořezání n-úhelníku");
			polyginClipControllerButton.addActionListener(e -> {
				canvas.setEventListener(new PolygonIntersectionController(settings));
			});
			excersise2.add(polyginClipControllerButton);
			controlButtons.add(polyginClipControllerButton);
		}
	}
	
	class Toolbar extends JToolBar {
		public Toolbar() {
			super();
			setLayout(new FlowLayout(FlowLayout.LEADING));
			//clear button
			JButton clearButton = new JButton("Smazat");
			clearButton.addActionListener(e -> {
				canvas.clear();
			});
			add(clearButton);
			add(new JSeparator(SwingConstants.VERTICAL));

			//pixel size
			JLabel pixelSizeLabel = new JLabel("Zoom: ", SwingConstants.TRAILING);
			add(pixelSizeLabel);		
			JSlider pixelSizeSlidder = new JSlider(1, 10, settings.getPixelSize());
			pixelSizeSlidder.addChangeListener(e -> {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	canvas.setPixelSize((int)source.getValue());		            
		        }
			});
			add(pixelSizeSlidder);		
			add(new JSeparator(SwingConstants.VERTICAL));
			
			//line algo
			JLabel lineAlgorithmLabel = new JLabel("Algoritmus úsečky: ", SwingConstants.TRAILING);
			add(lineAlgorithmLabel);		
			JComboBox<Settings.LineAlogrithm> lineAlgorithm = new JComboBox<>(Settings.LineAlogrithm.values());
			lineAlgorithm.setSelectedItem(settings.getLineAlgorithm());
			lineAlgorithm.addActionListener(e->{
				JComboBox<Settings.LineAlogrithm> cb = (JComboBox<Settings.LineAlogrithm>)e.getSource();
				Settings.LineAlogrithm value = (Settings.LineAlogrithm)cb.getSelectedItem();
				settings.setLineAlgorithm(value);
				canvas.onSettingChanged();
			});
			add(lineAlgorithm);		
			add(new JSeparator(SwingConstants.VERTICAL));
			
			//color
			JLabel colorLabel = new JLabel("Barva: ", SwingConstants.TRAILING);
			add(colorLabel);		
			ColorChooserButton colorChooser = new ColorChooserButton(settings.getColor());
			colorChooser.addColorChangedListener(color -> {
				settings.setColor(color);
				canvas.onSettingChanged();
			});
			add(colorChooser);		
			
			//background
			JLabel backgroundLabel = new JLabel("Barva pozadí: ", SwingConstants.TRAILING);
			add(backgroundLabel);		
			ColorChooserButton backgroundChooser = new ColorChooserButton(settings.getBackground());
			backgroundChooser.addColorChangedListener(color -> {
				settings.setBackground(color);
				canvas.onSettingChanged();
			});
			add(backgroundChooser);		
		}
	}	
	
	public static class Settings implements Cloneable {

		private int pixelSize = 4;
		private Color color = Color.RED;
		private Color background = Color.GREEN;
		private LineAlogrithm lineAlgorithm = LineAlogrithm.DDA;
		
		public Settings() {
			super();
		}	
		
		public int getPixelSize() {
			return pixelSize;
		}

		public void setPixelSize(int pixelSize) {
			this.pixelSize = pixelSize;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public Color getBackground() {
			return background;
		}

		public void setBackground(Color background) {
			this.background = background;
		}

		public LineAlogrithm getLineAlgorithm() {
			return lineAlgorithm;
		}

		public void setLineAlgorithm(LineAlogrithm lineAlgorithm) {
			this.lineAlgorithm = lineAlgorithm;
		}

		public static enum LineAlogrithm {		
			Trivial,
			DDA,
			Quadrantal,
			Bresenham
		}

		@Override
		public Object clone() {
			try {
				return super.clone();
			} catch (CloneNotSupportedException e) {
				throw new IllegalStateException(e);
			}
		}	
	}
}
