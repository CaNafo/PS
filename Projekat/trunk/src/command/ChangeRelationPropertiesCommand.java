/***********************************************************************
 * Module:  ChangeRelationPropertiesCommand.java
 * Author:  Boris
 * Purpose: Defines the Class ChangeRelationPropertiesCommand
 ***********************************************************************/

package command;

import model.DiagramModel;
import model.RelationModel;

public class ChangeRelationPropertiesCommand implements CommandInterface {
	@SuppressWarnings("unused")
	private String description;
	private RelationModel oldRelationState;

	public DiagramModel model;

	/** @param model */
	public ChangeRelationPropertiesCommand(DiagramModel model) {
		// TODO: implement
	}

	public void execute(String[] args) {
		// TODO: implement
	}

	public void unexecute() {
		// TODO: implement
	}

	public RelationModel getOldRelationState() {
		return oldRelationState;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}