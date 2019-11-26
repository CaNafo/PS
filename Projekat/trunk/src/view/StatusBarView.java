package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class StatusBarView extends JPanel {
	private JLabel zoomOut;
	private JSlider zoomSlider;
	private JLabel zoomIn;
	private JTextField zoomValue;

	public StatusBarView() {
		setPreferredSize(new Dimension(1000, 50));
		setLayout(new FlowLayout(FlowLayout.RIGHT));

		Image zoomOutImage = getToolkit().getImage("images/zoom-out.png");
		Image zoomInImage = getToolkit().getImage("images/zoom-in.png");

		zoomOutImage = zoomOutImage.getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);
		zoomInImage = zoomInImage.getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);

		zoomOut = new JLabel(new ImageIcon(zoomOutImage));
		zoomIn = new JLabel(new ImageIcon(zoomInImage));
		zoomSlider = new JSlider();
		zoomValue = new JTextField(3);
		
		zoomSlider.setMinimum(25);
		zoomSlider.setMaximum(400);
		zoomSlider.setValue(100);
		zoomValue.setText(String.valueOf(100));

		add(zoomOut);
		add(zoomSlider);
		add(zoomIn);
		add(zoomValue);
		add(new JLabel("%"));
	}

	public JTextField getZoomValue() {
		return zoomValue;
	}

	public void setZoomValue(JTextField zoomValue) {
		this.zoomValue = zoomValue;
	}

	public JLabel getZoomOut() {
		return zoomOut;
	}

	public JSlider getZoomSlider() {
		return zoomSlider;
	}

	public JLabel getZoomIn() {
		return zoomIn;
	}
	
}
