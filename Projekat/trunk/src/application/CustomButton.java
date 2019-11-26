package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class CustomButton extends JButton {

	private Boolean isPresed;

	private ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isPresed) {
				CustomButton.this.setButtonPresseed(true);
			} 
		}
	};

	public CustomButton() {
		super();
		this.addActionListener(listener);
		this.setButtonPresseed(false);
	}

	public CustomButton(Action a) {
		super(a);
		this.addActionListener(listener);
		this.setButtonPresseed(false);
	}

	public CustomButton(Icon icon) {
		super(icon);
		this.addActionListener(listener);
		this.setButtonPresseed(false);
	}

	public CustomButton(String text, Icon icon) {
		super(text, icon);
		this.addActionListener(listener);
		this.setButtonPresseed(false);
	}

	public CustomButton(String text) {
		super(text);
		this.addActionListener(listener);
		this.setButtonPresseed(false);
	}

	public Boolean isPressed() {
		return isPresed;
	}

	public void setButtonPresseed(Boolean pressed) {
		if(pressed) {
			isPresed = true;
			CustomButton.this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		}else {
			isPresed = false;
			CustomButton.this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
	}

}
