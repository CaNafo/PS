/***********************************************************************
 * Module:  CreateRelationCommand.java
 * Author:  Boris
 * Purpose: Defines the Class CreateRelationCommand
 ***********************************************************************/

package command;

import java.awt.Point;

import model.DiagramModel;
import model.RelationModel;

public class CreateRelationCommand implements CommandInterface {
	@SuppressWarnings("unused")
	private static String description;
	private RelationModel createdRelation=null;

	public DiagramModel model;

	/** @param model */
	public CreateRelationCommand(DiagramModel model, RelationModel relationModel) {
		this.model = model;
		this.createdRelation = relationModel;
		model.addRelationModel(relationModel);
	}

	public void execute(String[] args) {
		//Redo
		if(createdRelation!=null) {
			model.addRelationModel(createdRelation);
//			return;
		}
		
		//Kreiranje nove relacije
		if(createdRelation.getPath().size()>0) {
			Point p1 = createdRelation.getPath().get(createdRelation.getPath().size() - 1);
			Point p2 = createdRelation.getEndElement().getCenter();
			Point p3=getPoint(p1, p2);
			if(p2.x!=p3.x) {
				p2.x=p3.x;
			}else if(p2.y!=p3.y) {
				p2.y=p3.y;
			}
			createdRelation.getPath().set(createdRelation.getPath().size()-1, p2);
			createdRelation.getPath().add(p3);			
		}else {
			Point p1 = createdRelation.getStartElement().getCenter();
			Point p2 = createdRelation.getEndElement().getCenter();
			Point p3 = getPoint(p1, p2);
			createdRelation.getPath().add(p3);
		}
	}

	public void unexecute() {
		model.removeRelationModel(createdRelation);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	private Point getPoint(Point startPoint, Point endPoint) {
		int startX = startPoint.x;
		int startY = startPoint.y;
		int endX = endPoint.x;
		int endY = endPoint.y;
		if (Math.abs(endX - startX) > Math.abs(endY - startY)) {
			return new Point(startX, endY);
		} else {
			return new Point(endX, startY);
		}
	}

}