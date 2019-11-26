/***********************************************************************
 * Module:  CreateUseCaseCommand.java
 * Author:  Boris
 * Purpose: Defines the Class CreateUseCaseCommand
 ***********************************************************************/

package command;

import java.awt.Point;
import java.util.Iterator;

import model.DiagramModel;
import model.Element;
import model.UseCaseModel;

public class CreateUseCaseCommand implements CommandInterface {

	private static String description;
	private UseCaseModel createdUseCase=null;
	private DiagramModel model;
	private Point clicked;

	/** @param model */
	public CreateUseCaseCommand(DiagramModel model, Point clicked) {
		this.model = model;
		this.clicked = clicked;
	}

	public void execute(String[] args) {
		//Redo
		if (createdUseCase!=null) {
			model.addElement(createdUseCase);
			return;
		}
		
		//Dodavanje novog elementa
		Boolean taken = false;

		int locationX = clicked.x - UseCaseModel.DEFAULT_DIMENSIONS.width / 2;
		int locationY = clicked.y - UseCaseModel.DEFAULT_DIMENSIONS.height / 2;
		locationX = (locationX < 0) ? 0 : locationX;
		locationY = (locationY < 0) ? 0 : locationY;
		int maxX = model.getSize().width - UseCaseModel.DEFAULT_DIMENSIONS.width;
		int maxY = model.getSize().height - UseCaseModel.DEFAULT_DIMENSIONS.height;
		locationX = (locationX > maxX) ? maxX : locationX;
		locationY = (locationY > maxY) ? maxY : locationY;

		for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if (element.existOnLocation(clicked)) {
				taken = true;
			}
		}

		if (!taken) {
			createdUseCase = new UseCaseModel(model, new Point(locationX, locationY));
			model.addElement(createdUseCase);
		}
	}

	public void unexecute() {
		model.removeElement(createdUseCase);
	}

	public String getDescription() {
		return description;
	}

	public UseCaseModel getCreatedUseCase() {
		return createdUseCase;
	}

}