package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.DiagramModel;
import view.DiagramView;
import view.StatusBarView;

public class ZoomController {

	private DiagramModel model;
	private DiagramView view;
	private StatusBarView statusBar;

	public ZoomController(DiagramModel model, DiagramView view, StatusBarView statusBar) {
		this.model = model;
		this.view = view;
		this.statusBar = statusBar;
		statusBar.getZoomSlider().setValue((int) (model.getScaleFactor() * 100));
		addListeners();
	}

	private void addListeners() {
		statusBar.getZoomOut().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int value = statusBar.getZoomSlider().getValue();
				if (value % 25 == 0) {
					value -= 25;
					if (value < 25) {
						value = 25;
					}
				} else {
					value -= (value % 25);
				}

				statusBar.getZoomSlider().setValue(value);
				statusBar.getZoomValue().setText(String.valueOf(value));
				model.setScaleFactor(((double) value) / 100);

				int width = model.getSize().width;
				int height = model.getSize().height;
				width=(int)(width*model.getScaleFactor());
				height=(int)(height*model.getScaleFactor());
				view.setPreferredSize(new Dimension(width, height));
				view.revalidate();
				
				model.notifyAllObservers();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		statusBar.getZoomIn().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int value = statusBar.getZoomSlider().getValue();
				if (value % 25 == 0) {
					value += 25;
					if (value > 400) {
						value = 400;
					}
				} else {
					value += (25 - value % 25);
				}
				statusBar.getZoomSlider().setValue(value);
				statusBar.getZoomValue().setText(String.valueOf(value));
				model.setScaleFactor(((double) value) / 100);

				int width = model.getSize().width;
				int height = model.getSize().height;
				width=(int)(width*model.getScaleFactor());
				height=(int)(height*model.getScaleFactor());
				view.setPreferredSize(new Dimension(width, height));
				view.revalidate();
				
				model.notifyAllObservers();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		statusBar.getZoomSlider().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				int value = statusBar.getZoomSlider().getValue();
				statusBar.getZoomValue().setText(String.valueOf(value));
				model.setScaleFactor(((double) value) / 100);
				
				int width = model.getSize().width;
				int height = model.getSize().height;
				width=(int)(width*model.getScaleFactor());
				height=(int)(height*model.getScaleFactor());
				view.setPreferredSize(new Dimension(width, height));
				view.revalidate();
				
				model.notifyAllObservers();
			}
		});

		statusBar.getZoomValue().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int value = Integer.valueOf(statusBar.getZoomValue().getText());
					value = (value < 25) ? 25 : value;
					value = (value > 400) ? 400 : value;
					statusBar.getZoomSlider().setValue(value);
					model.setScaleFactor(((double) value) / 100);
					
					int width = model.getSize().width;
					int height = model.getSize().height;
					width=(int)(width*model.getScaleFactor());
					height=(int)(height*model.getScaleFactor());
					view.setPreferredSize(new Dimension(width, height));
					view.revalidate();
					
					model.notifyAllObservers();
				} catch (Exception e) {
					statusBar.getZoomValue().setText(String.valueOf(statusBar.getZoomSlider().getValue()));
				}
			}
		});
	}

}
