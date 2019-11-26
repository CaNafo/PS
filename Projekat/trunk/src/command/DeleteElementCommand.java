/***********************************************************************
 * Module:  DeleteElementCommand.java
 * Author:  Boris
 * Purpose: Defines the Class DeleteElementCommand
 ***********************************************************************/

package command;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;

import model.DiagramModel;
import model.DocumentTreeModel;
import model.Element;
import model.RelationModel;
import model.SelectedElement;
import view.DocumentTreeView;

public class DeleteElementCommand implements CommandInterface {
	@SuppressWarnings("unused")
	private String description;
	private HashSet<Element> deletedElements;
	private HashSet<RelationModel> deletedRelations;

	public DiagramModel model;

	/** @param model */
	public DeleteElementCommand(DiagramModel model) {
		this.model = model;
		this.deletedElements = new HashSet<>();
		this.deletedRelations = new HashSet<>();
	}
	
	public DeleteElementCommand(DiagramModel model, Element deletedElement) {
		this.model = model;
		this.deletedElements = new HashSet<>();
		deletedElements.add(deletedElement);	
		deletedRelations=new HashSet<>();
	}

	public void execute(String[] args) {
		//Redo
		if(deletedElements.size()>0 & deletedRelations.size()>0) {
			for (Iterator<Element> iterator = deletedElements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				model.removeElement(element);
			}
			for (Iterator<RelationModel> iterator = deletedRelations.iterator(); iterator.hasNext();) {
				RelationModel relation = (RelationModel) iterator.next();
				model.removeRelationModel(relation);
			}
		}
		
		
		//Brisanje elemenata alatkom sa toolbar-a
		if(args!=null) {
			if(args[0]=="one-at-a-time") {
				Point clicked = new Point(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
				clicked.x=(int)(clicked.x*model.getScaleFactor());
				clicked.y=(int)(clicked.y*model.getScaleFactor());
				for(Iterator<Element> iterator =model.getIteratorElement(); iterator.hasNext();) {
					Element element = iterator.next();
					if(element.existOnLocation(clicked)) {
						deletedElements.add(element);
					}
				}
				
				
				for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
					RelationModel relation = (RelationModel) iterator.next();
					for (Iterator<Element> iterator2 = deletedElements.iterator(); iterator2.hasNext();) {
						Element element = (Element) iterator2.next();
						if(element instanceof SelectedElement) {
							element=((SelectedElement) element).getElement();
						}
						if (relation.getStartElement().equals(element) || relation.getEndElement().equals(element)) {
							deletedRelations.add(relation);
						}
					}
				}
				
				for (Iterator<Element> iterator = deletedElements.iterator(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					model.removeElement(element);
				}
				for (Iterator<RelationModel> iterator = deletedRelations.iterator(); iterator.hasNext();) {
					RelationModel relation = (RelationModel) iterator.next();
					model.removeRelationModel(relation);
				}
				
				return;
			}
			if(args[0]=="from-tree") {
				for(Iterator<Element> iterator=deletedElements.iterator(); iterator.hasNext();) {
					Element element=iterator.next();
					if (element instanceof SelectedElement) {
						element=((SelectedElement) element).getElement();
					}
					
					for(Iterator<RelationModel> iterator2 = model.getIteratorRelationModel(); iterator2.hasNext();) {
						RelationModel relation=iterator2.next();
						if(relation.getStartElement()==element || relation.getEndElement()==element) {
							deletedRelations.add(relation);
						}
					}
					
				}
				
				for (Iterator<Element> iterator = deletedElements.iterator(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					model.removeElement(element);
				}
				
				for (Iterator<RelationModel> iterator = deletedRelations.iterator(); iterator.hasNext();) {
					RelationModel relation = (RelationModel) iterator.next();
					model.removeRelationModel(relation);
				}
				return;
			}
		}
		
		
		//Brisanje Selektovanih elemenata
		for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if (element instanceof SelectedElement) {
				deletedElements.add(element);
			}
		}

		for (Iterator<Element> iterator = deletedElements.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			model.removeElement(element);
		}

		for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
			RelationModel relation = (RelationModel) iterator.next();
			for (Iterator<Element> iterator2 = deletedElements.iterator(); iterator2.hasNext();) {
				Element element = (Element) iterator2.next();
				Element temp=element;
				if (temp instanceof SelectedElement) {
					temp = ((SelectedElement) element).getElement();					
				}
				if (relation.getStartElement().equals(temp) || relation.getEndElement().equals(temp)) {
					deletedRelations.add(relation);
				}
			}
		}
		
		for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
			RelationModel relation = (RelationModel) iterator.next();
			if (relation.getSelected()==true) {
				deletedRelations.add(relation);
			}
		}

		for (Iterator<RelationModel> iterator = deletedRelations.iterator(); iterator.hasNext();) {
			RelationModel relation = (RelationModel) iterator.next();
			model.removeRelationModel(relation);
		}
		
		@SuppressWarnings("static-access")
		DocumentTreeView view = new DocumentTreeView().documentTreeView;
		DocumentTreeModel.clearTree(view);
		DocumentTreeView.expandAllNodes(view, 0, view.getRowCount());
	}

	public void unexecute() {
		for (Iterator<Element> iterator = deletedElements.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			model.addElement(element);
		}
		for (Iterator<RelationModel> iterator = deletedRelations.iterator(); iterator.hasNext();) {
			RelationModel relation = (RelationModel) iterator.next();
			model.addRelationModel(relation);
		}
		
		DocumentTreeView view = DocumentTreeView.documentTreeView;
		DocumentTreeModel.clearTree(view);
		DocumentTreeView.expandAllNodes(view, 0, view.getRowCount());
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