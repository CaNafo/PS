package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.DiagramView;
import view.MenuBarView;
import view.StatusBarView;

public class DiagramMenuController {
	@SuppressWarnings("unused")
	private MenuBarView barView;
	private DiagramView diagramView; 
	private StatusBarView statusBarView;

	public DiagramMenuController(MenuBarView barView, DiagramView diagramView, StatusBarView statusBarView) {
		this.diagramView = diagramView;
		this.barView = barView;
		this.statusBarView = statusBarView;
		
		barView.setEditActionListner(actionListener);
	}

	ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "zoomIn":
				int value = (int) (diagramView.getModel().getScaleFactor()*100);
				if (value % 25 == 0) {
					value += 25;
					if (value > 400) {
						value = 400;
					}
				} else {
					value += (25 - value % 25);
				}
				statusBarView.getZoomSlider().setValue(value);
				statusBarView.getZoomValue().setText(String.valueOf(value));
				diagramView.getModel().setScaleFactor(((double) value) / 100);

				int width = diagramView.getModel().getSize().width;
				int height = diagramView.getModel().getSize().height;
				width=(int)(width*diagramView.getModel().getScaleFactor());
				height=(int)(height*diagramView.getModel().getScaleFactor());
				diagramView.setPreferredSize(new Dimension(width, height));
				diagramView.revalidate();
				
				diagramView.getModel().notifyAllObservers();
				break;
			case "zoomOut":
				 value = (int) (diagramView.getModel().getScaleFactor()*100);
				if (value % 25 == 0) {
					value -= 25;
					if (value < 25) {
						value = 25;
					}
				} else {
					value -= (value % 25);
				}

				statusBarView.getZoomSlider().setValue(value);
				statusBarView.getZoomValue().setText(String.valueOf(value));
				diagramView.getModel().setScaleFactor(((double) value) / 100);

				 width = diagramView.getModel().getSize().width;
				 height = diagramView.getModel().getSize().height;
				 width=(int)(width*diagramView.getModel().getScaleFactor());
				 height=(int)(height*diagramView.getModel().getScaleFactor());
				 diagramView.setPreferredSize(new Dimension(width, height));
				 diagramView.revalidate();
				break;

			default:
				break;
			}

		}
	};
}
