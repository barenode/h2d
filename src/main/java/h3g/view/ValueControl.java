package h3g.view;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ValueControl extends JPanel {		
	
	private final Label valueLabel;
	private final double step;
	
	private ActionListener actionListener;
	private double value; 
	
	public ValueControl(String name, final double initialValue, final double step) {
		super();
		this.step = step;
		this.value = initialValue;
		add(new Label(name + ": "));
		
		JButton minus = new JButton("-");
		minus.addActionListener(e->{
			value -= step;
			referesh();
		});
		add(minus);
		
		valueLabel = new Label();		
		add(valueLabel);		
		JButton plus = new JButton("+");
		plus.addActionListener(e->{
			value += step;
			referesh();
		});
		add(plus);
		referesh();
	}
	
	private void referesh() {
		valueLabel.setText(String.valueOf(value));
		if (actionListener!=null) {
			actionListener.actionPerformed(new ActionEvent(value, 0, ""));
		}		
	}

	public ActionListener getActionListener() {
		return actionListener;
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}	
}
