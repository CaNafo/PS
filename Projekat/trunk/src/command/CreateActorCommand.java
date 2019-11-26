/***********************************************************************
 * Module:  CreateActorCommand.java
 * Author:  Boris
 * Purpose: Defines the Class CreateActorCommand
 ***********************************************************************/

package command;

import java.awt.Point;
import java.util.Iterator;

import model.DiagramModel;
import model.Element;
import model.ActorModel;

public class CreateActorCommand implements CommandInterface {

	private static String description;
	private ActorModel createdActor=null;
	private DiagramModel model;
	private Point clicked;

	/** @param model */
	public CreateActorCommand(DiagramModel model, Point clicked) {
		this.model = model;
		this.clicked = clicked;
	}

	public void execute(String[] args) {
		//Redo
		if(createdActor!=null) {
			model.addElement(createdActor);
			return;
		}
		
		//Dodavanje novog elementa
		Boolean taken = false;

		int locationX = clicked.x - ActorModel.DEFAULT_DIMENSIONS.width / 2;
		int locationY = clicked.y - ActorModel.DEFAULT_DIMENSIONS.height / 2;
		locationX = (locationX < 0) ? 0 : locationX;
		locationY = (locationY < 0) ? 0 : locationY;
		int maxX = model.getSize().width - ActorModel.DEFAULT_DIMENSIONS.width;
		int maxY = model.getSize().height - ActorModel.DEFAULT_DIMENSIONS.height;
		locationX = (locationX > maxX) ? maxX : locationX;
		locationY = (locationY > maxY) ? maxY : locationY;

		for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if (element.existOnLocation(clicked)) {
				taken = true;
			}
		}

		if (!taken) {
			createdActor = new ActorModel(model, new Point(locationX, locationY));
			model.addElement(createdActor);
		}
	}

	public void unexecute() {
		model.removeElement(createdActor);
	}

	public String getDescription() {
		return description;
	}

	public ActorModel getCreatedActort() {
		return createdActor;
	}

}