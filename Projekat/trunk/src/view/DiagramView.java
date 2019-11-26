/***********************************************************************
 * Module:  DijagramPogled.java
 * Author:  Ca
 * Purpose: Defines the Class DijagramPogled
 ***********************************************************************/

package view;

import model.DiagramModel;
import model.Element;
import model.RelationModel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JPanel;

import controller.DiagramEditController;
import controller.DiagramMenuController;
import controller.ZoomController;

@SuppressWarnings("serial")
public class DiagramView extends JPanel implements ViewInterface {
	private DiagramModel model;
	private DiagramToolbar toolbar;
	
	public DiagramView(DiagramModel model, DiagramToolbar toolbar, StatusBarView statusBarView, MenuBarView barView) {

		this.model = model;
		this.toolbar = toolbar;
		this.setPreferredSize(model.getSize());
		this.setBackground(Color.WHITE);
		this.setFocusable(true);
		new DiagramEditController(model, this, toolbar,barView);
		new ZoomController(model, this, statusBarView);		
		this.setMouseCursor();
		new DiagramMenuController(barView, this, statusBarView);
	}

	public void update() {
		this.repaint();
	}

	private void setMouseCursor() {
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		
		if(toolbar.getBtnSelectArrow().isPressed()) {
			this.setCursor(Cursor.getDefaultCursor());
		}else if(toolbar.getBtnSelectRect().isPressed()) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}else if(toolbar.getBtnActor().isPressed()) {
			Image actor = toolkit.getImage("images/actor.png");
			actor = actor.getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);
			Cursor actorCursor = toolkit.createCustomCursor(actor, new Point(16, 16), "actor");
			this.setCursor(actorCursor);
		}else if(toolbar.getBtnUseCase().isPressed()) {
			Image useCase = toolkit.getImage("images/use-case.png");
			useCase = useCase.getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);
			Cursor useCaseCursor = toolkit.createCustomCursor(useCase, new Point(16, 16), "use-case");
			this.setCursor(useCaseCursor);
		}else if(toolbar.getBtnGeneralization().isPressed()) {
			Image generalization=toolkit.getImage("images/generalization-cursor.png");
			Cursor generalizationCursor=toolkit.createCustomCursor(generalization, new Point(0, 16), "generalization");
			this.setCursor(generalizationCursor);
		}else if(toolbar.getBtnDependency().isPressed()) {
			Image dependency=toolkit.getImage("images/dependency-cursor.png");
			Cursor dependencyCursor=toolkit.createCustomCursor(dependency, new Point(0, 16), "dependency");
			this.setCursor(dependencyCursor);
		}else if(toolbar.getBtnAssociation().isPressed()) {
			Image association=toolkit.getImage("images/association-cursor.png");
			Cursor associationCursor=toolkit.createCustomCursor(association, new Point(0, 16), "association");
			this.setCursor(associationCursor);
		}else if(toolbar.getBtnDelete().isPressed()) {
			Image delete = toolkit.getImage("images/delete.png");
			Cursor deleteCurosor=toolkit.createCustomCursor(delete, new Point(16, 16), "delete");
			this.setCursor(deleteCurosor);
		}
	}
	
	/** @param actionListener */
	public void setListener(ActionListener actionListener) {
		// TODO: implement
	}

	public void zoomIn() {
		// TODO: implement
	}

	public void zoomOut() {
		// TODO: implement
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D grafika = (Graphics2D) g;
		grafika.scale(model.getScaleFactor(), model.getScaleFactor());

		for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
			RelationModel relation = (RelationModel) iterator.next();
			relation.drawRelation(grafika);

		}

		for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			element.drawElement(grafika);
		}
		
	}
	
	public DiagramModel getModel() {
		return model;
	}
}