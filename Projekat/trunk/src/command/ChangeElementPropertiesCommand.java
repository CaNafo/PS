/***********************************************************************
 * Module:  ChangeElementPropertiesCommand.java
 * Author:  Boris
 * Purpose: Defines the Class ChangeElementPropertiesCommand
 ***********************************************************************/

package command;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import controller.ActorPropertiesController;
import controller.RelationPropertiesController;
import controller.UseCasePropertiesController;
import model.ActorModel;
import model.DiagramModel;
import model.Element;
import model.RelationModel;
import model.SelectedElement;
import model.Specification;
import model.UseCaseModel;
import view.ActorPropertiesView;
import view.RelationPropertiesView;
import view.UseCasePropertiesView;

public class ChangeElementPropertiesCommand implements CommandInterface {
	@SuppressWarnings("unused")
	private String description;
	private Element element = null;
	private RelationModel relationModel = null;

	private DiagramModel model;
	private Point clicked;

	// Èuvanje atributa elementa/relacije (prije izmjene)
	private String nameBefore;
	private String commentBefore;
	private String preConditionsBefore;
	private String actionStepsBefore;
	private String extensionPointsBefore;
	private String exceptionsBefore;
	private String postConditionsBefore;
	private String stereotypeBefore;
	private Color lineColorBefore;
	private Color bgColorBefore;
	private int lineWidthBefore;

	private String nameAfter;
	private String commentAfter;
	private String preConditionsAfter;
	private String actionStepsAfter;
	private String extensionPointsAfter;
	private String exceptionsAfter;
	private String postConditionsAfter;
	private String stereotypeAfter;
	private Color lineColorAfter;
	private Color bgColorAfter;
	private int lineWidthAfter;

	/** @param model */
	public ChangeElementPropertiesCommand(DiagramModel model, Point clicked) {
		this.model = model;
		this.clicked = clicked;
	}

	/**
	 * 
	 */
	public void execute(String[] args) {
		// Redo
		if (element != null) {
			if (element instanceof UseCaseModel) {
				element.setName(nameAfter);
				element.setComment(commentAfter);
				((UseCaseModel) element).setSpecification(new Specification(preConditionsAfter, actionStepsAfter,
						extensionPointsAfter, exceptionsAfter, postConditionsAfter));
				element.setLineColor(lineColorAfter);
				element.setBackgroundColor(bgColorAfter);
				element.setLineWidth(lineWidthAfter);
			} else if (element instanceof ActorModel) {
				element.setName(nameAfter);
				element.setComment(commentAfter);
				element.setLineColor(lineColorAfter);
				element.setBackgroundColor(bgColorAfter);
				element.setLineWidth(lineWidthAfter);
			}
			return;
		}
		if(relationModel!=null) {
			relationModel.setComment(commentAfter);
			relationModel.setLineColor(lineColorAfter);
			relationModel.setLineWidth(lineWidthAfter);
			relationModel.setStereoType(stereotypeAfter);
			return;
		}
		
		//Prvo izvrsavanje
		Boolean elementHit = false;
		for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if (element.existOnLocation(clicked)) {
				if (element instanceof SelectedElement) {
					element = ((SelectedElement) element).getElement();
				}
				this.element = element;
				if (element instanceof UseCaseModel) {
					// Backup starih atributa
					this.nameBefore = element.getName();
					this.commentBefore = element.getComment();
					this.preConditionsBefore = ((UseCaseModel) element).getSpecification().getPreConditions();
					this.actionStepsBefore = ((UseCaseModel) element).getSpecification().getActionSteps();
					this.extensionPointsBefore = ((UseCaseModel) element).getSpecification().getExtensionPoints();
					this.exceptionsBefore = ((UseCaseModel) element).getSpecification().getExceptions();
					this.postConditionsBefore = ((UseCaseModel) element).getSpecification().getPostConditions();
					this.lineColorBefore = element.getLineColor();
					this.bgColorBefore = element.getBackgroundColor();
					this.lineWidthBefore = element.getLineWidth();

					UseCasePropertiesView properties = new UseCasePropertiesView((UseCaseModel) element);
					new UseCasePropertiesController(properties, (UseCaseModel) element);

					properties.addWindowListener(new WindowAdapter() {
						public void windowClosed(WindowEvent e) {
							// Backup novih atributa
							ChangeElementPropertiesCommand.this.nameAfter = ChangeElementPropertiesCommand.this.element
									.getName();
							ChangeElementPropertiesCommand.this.commentAfter = ChangeElementPropertiesCommand.this.element
									.getComment();
							ChangeElementPropertiesCommand.this.preConditionsAfter = ((UseCaseModel) ChangeElementPropertiesCommand.this.element)
									.getSpecification().getPreConditions();
							ChangeElementPropertiesCommand.this.actionStepsAfter = ((UseCaseModel) ChangeElementPropertiesCommand.this.element)
									.getSpecification().getActionSteps();
							ChangeElementPropertiesCommand.this.extensionPointsAfter = ((UseCaseModel) ChangeElementPropertiesCommand.this.element)
									.getSpecification().getExtensionPoints();
							ChangeElementPropertiesCommand.this.exceptionsAfter = ((UseCaseModel) ChangeElementPropertiesCommand.this.element)
									.getSpecification().getExceptions();
							ChangeElementPropertiesCommand.this.postConditionsAfter = ((UseCaseModel) ChangeElementPropertiesCommand.this.element)
									.getSpecification().getPostConditions();
							ChangeElementPropertiesCommand.this.lineColorAfter = ChangeElementPropertiesCommand.this.element
									.getLineColor();
							ChangeElementPropertiesCommand.this.bgColorAfter = ChangeElementPropertiesCommand.this.element
									.getBackgroundColor();
							ChangeElementPropertiesCommand.this.lineWidthAfter = ChangeElementPropertiesCommand.this.element
									.getLineWidth();
						}

					});

				}
				if (element instanceof ActorModel) {
					// Backup starih atributa
					this.nameBefore = element.getName();
					this.commentBefore = element.getComment();
					this.lineColorBefore = element.getLineColor();
					this.bgColorBefore = element.getBackgroundColor();
					this.lineWidthBefore = element.getLineWidth();

					ActorPropertiesView properties = new ActorPropertiesView((ActorModel) element);
					new ActorPropertiesController(properties, (ActorModel) element);

					// Backup novih atributa
					properties.addWindowListener(new WindowAdapter() {
						public void windowClosed(WindowEvent e) {
							ChangeElementPropertiesCommand.this.nameAfter = ChangeElementPropertiesCommand.this.element
									.getName();
							ChangeElementPropertiesCommand.this.commentAfter = ChangeElementPropertiesCommand.this.element
									.getComment();
							ChangeElementPropertiesCommand.this.lineColorAfter = ChangeElementPropertiesCommand.this.element
									.getLineColor();
							ChangeElementPropertiesCommand.this.bgColorAfter = ChangeElementPropertiesCommand.this.element
									.getBackgroundColor();
							ChangeElementPropertiesCommand.this.lineWidthAfter = ChangeElementPropertiesCommand.this.element
									.getLineWidth();
						}
					});

				}
				elementHit = true;
				break;
			}
		}
		if (!elementHit) {
			for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
				RelationModel relation = (RelationModel) iterator.next();
				if (relation.existOnLocation(clicked)) {
					this.relationModel = relation;
					// Backup starih atributa
					this.commentBefore = relation.getComment();
					this.lineColorBefore = relation.getLineColor();
					this.lineWidthBefore = relation.getLineWidth();
					this.stereotypeBefore = relation.getStereoType();

					RelationPropertiesView properties = new RelationPropertiesView(relation);
					new RelationPropertiesController(properties, relation);
					
					properties.addWindowListener(new WindowAdapter() {
						public void windowClosed(WindowEvent e) {
							ChangeElementPropertiesCommand.this.commentAfter = ChangeElementPropertiesCommand.this.relationModel
									.getComment();
							ChangeElementPropertiesCommand.this.lineColorAfter = ChangeElementPropertiesCommand.this.relationModel
									.getLineColor();
							ChangeElementPropertiesCommand.this.stereotypeAfter = ChangeElementPropertiesCommand.this.relationModel
									.getStereoType();
							ChangeElementPropertiesCommand.this.lineWidthAfter = ChangeElementPropertiesCommand.this.relationModel
									.getLineWidth();
						}
					});
					
					break;
				}
			}
		}
	}

	/**
	 * 
	 */
	public void unexecute() {
		if (element != null) {
			if (element instanceof UseCaseModel) {
				element.setName(nameBefore);
				element.setComment(commentBefore);
				((UseCaseModel) element).setSpecification(new Specification(preConditionsBefore, actionStepsBefore,
						extensionPointsBefore, exceptionsBefore, postConditionsBefore));
				element.setLineColor(lineColorBefore);
				element.setBackgroundColor(bgColorBefore);
				element.setLineWidth(lineWidthBefore);
			} else if (element instanceof ActorModel) {
				element.setName(nameBefore);
				element.setComment(commentBefore);
				element.setLineColor(lineColorBefore);
				element.setBackgroundColor(bgColorBefore);
				element.setLineWidth(lineWidthBefore);
			}
		}
		if (relationModel != null) {
			relationModel.setComment(commentBefore);
			relationModel.setLineColor(lineColorBefore);
			relationModel.setLineWidth(lineWidthBefore);
			relationModel.setStereoType(stereotypeBefore);
		}
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}