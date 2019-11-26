/***********************************************************************
 * Module:  MoveElementCommand.java
 * Author:  Boris
 * Purpose: Defines the Class MoveElementCommand
 ***********************************************************************/

package command;

import model.DiagramModel;
import model.Element;

import java.awt.Point;

public class MoveElementCommand implements CommandInterface {
	@SuppressWarnings("unused")
	private String description;
	private Point oldLocation;
	private Point newLocation=null;
	private Element element;

	public DiagramModel model;

	/** @param model */
	public MoveElementCommand(DiagramModel model, Element element) {
		this.model = model;
		this.element = element;
	}

	public void execute(String[] args) {
		//Redo
		if(newLocation!=null) {
			element.setLocation(newLocation);
			return;
		}
		
		oldLocation = new Point(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
		newLocation=element.getLocation();
	}

	public void unexecute() {
		element.setLocation(oldLocation);
	}

	public Point getOldLocation() {
		return oldLocation;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}