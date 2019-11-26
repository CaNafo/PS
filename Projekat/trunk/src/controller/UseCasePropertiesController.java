package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.UseCaseModel;
import view.UseCasePropertiesView;

public class UseCasePropertiesController {
		private UseCasePropertiesView view;
		UseCaseModel model;
		
		public UseCasePropertiesController(UseCasePropertiesView view, UseCaseModel model) {
			this.view = view;
			this.model = model;
			
			setListeners();
			view.setKeyListeners(keyListener);
		}
		
		ActionListener okListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setSpecification(view.getSpecification());
				setGraphicSpecification();
				view.dispose();
			}
		};
		
		ActionListener canelListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				view.dispose();
			}
		};
		
		private void setListeners() {
			view.setOkListener(okListener);
			view.setCancel(canelListener);
		}
		
		private void setGraphicSpecification() {
			model.setName(view.getTxtName());			
			model.setComment(view.getTxtAreaComment());
			model.setLineWidth(view.getCb());
			model.setLineColor(view.getLineColor());
			model.setBackgroundColor(view.getBackgroundColor());
		}
		
		
		KeyListener keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER) {
					model.setSpecification(view.getSpecification());
					setGraphicSpecification();
					view.dispose();
				}
				
			}
		};
	}
