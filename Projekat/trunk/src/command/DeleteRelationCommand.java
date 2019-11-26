/***********************************************************************
 * Module:  DeleteRelationCommand.java
 * Author:  Boris
 * Purpose: Defines the Class DeleteRelationCommand
 ***********************************************************************/

package command;

import model.DiagramModel;
import model.Element;
import model.RelationModel;

public class DeleteRelationCommand implements CommandInterface {
	@SuppressWarnings("unused")
	private String description;
	private RelationModel deletedRelation;

	public DiagramModel model;

	/** @param model */
	public DeleteRelationCommand(DiagramModel model, RelationModel relationModel) {
		this.model = model;
		this.deletedRelation = relationModel;
	}

	public void execute(String[] args) {
		model.removeRelationModel(deletedRelation);
	}

	public void unexecute() {
		model.addRelationModel(deletedRelation);
	}

	public Element getElement() {
		// TODO: implement
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}