package command;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import model.DiagramModel;
import model.Element;
import model.RelationModel;
import model.SelectedElement;

public class SelectDeselectCommand implements CommandInterface {

	private DiagramModel model;
	private Point clicked;

	public SelectDeselectCommand(DiagramModel model, Point clicked) {
		this.clicked = clicked;
		this.model = model;
	}

	@Override
	public void execute(String[] args) {
		if (args[0] == "dragged") {
			rectangularSelection(args);
		} else if (args[0] == "static") {
			arrowSelection(args);
		}
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Selection using arrow tool
	 * 
	 * @param args
	 */
	private void arrowSelection(String[] args) {
		HashSet<Element> tempElement = new HashSet<>();

		if (args[1] == "controldown") {
			// Selektovanje elemenata
			for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
				Element element = (Element) iterator.next();

				if (element.existOnLocation(clicked)) {
					if (!(element instanceof SelectedElement)) {
						element = new SelectedElement(model, element);
					} else {
						element = ((SelectedElement) element).getElement();
					}
					tempElement.add(element);
				} else {
					tempElement.add(element);
				}
			}

			// Selektovanje veza
			for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
				RelationModel relation = (RelationModel) iterator.next();
				if (relation.existOnLocation(clicked)) {
					if (relation.getSelected() == false) {
						relation.setSelected(true);
					} else {
						relation.setSelected(false);
					}
				}
			}
		} else {
			// Selektovanje elemenata
			for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
				Element element = (Element) iterator.next();

				if (element.existOnLocation(clicked)) {
					if (!(element instanceof SelectedElement)) {
						element = new SelectedElement(model, element);
					}
					tempElement.add(element);
				} else {
					if (element instanceof SelectedElement) {
						element = ((SelectedElement) element).getElement();
					}
					tempElement.add(element);
				}
			}
			// Selektovanje veza
			for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
				RelationModel relation = (RelationModel) iterator.next();

				if (relation.existOnLocation(clicked)) {
					if (relation.getSelected() == false) {
						relation.setSelected(true);
					}
				} else {
					if (relation.getSelected() == true) {
						relation.setSelected(false);
					}
				}

			}
		}

		model.removeAllElement();

		for (Iterator<Element> iterator = tempElement.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			model.addElement(element);
		}

	}

	/**
	 * Selection using rectangle
	 * 
	 * @param args
	 */
	private void rectangularSelection(String[] args) {
		HashSet<Element> temp = new HashSet<>();

		int startX = Integer.parseInt(args[2]);
		int startY = Integer.parseInt(args[3]);
		int endX = Integer.parseInt(args[4]);
		int endY = Integer.parseInt(args[5]);
		startX = (startX < 0) ? 0 : startX;
		startX = (startX > model.getSize().width) ? model.getSize().width : startX;
		endX = (endX < 0) ? 0 : endX;
		endX = (endX > model.getSize().width) ? model.getSize().width : endX;
		startY = (startY < 0) ? 0 : startY;
		startY = (startY > model.getSize().height) ? model.getSize().height : startY;
		endY = (endY < 0) ? 0 : endY;
		endY = (endY > model.getSize().height) ? model.getSize().height : endY;
		if (startX > endX) {
			int num = startX;
			startX = endX;
			endX = num;
		}
		if (startY > endY) {
			int num = startY;
			startY = endY;
			endY = num;
		}

		if (args[1] == "controldown") {
			for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				if (pointBetweenCoordinates(element.getCenter(), startX, startY, endX, endY)) {
					if (!(element instanceof SelectedElement)) {
						element = new SelectedElement(model, element);
					}
				}
				temp.add(element);
			}
			
			for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
				RelationModel relation = (RelationModel) iterator.next();
				Boolean hit=false;
				ArrayList<Point> centers=relation.getSegmentCenters();
				for(int i=0; i<relation.getPath().size(); i++) {
					centers.add(relation.getPath().get(i));
				}
				for(int i=0; i<centers.size(); i++) {
					if(pointBetweenCoordinates(centers.get(i), startX, startY, endX, endY)) {
						hit=true;
						break;
					}
				}
				
				if (hit) {
					if (relation.getSelected() == false) {
						relation.setSelected(true);
					}
				}
			}

		} else {
			for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
				Element element = (Element) iterator.next();

				if (pointBetweenCoordinates(element.getCenter(), startX, startY, endX, endY)) {
					if (!(element instanceof SelectedElement)) {
						element = new SelectedElement(model, element);
					}
					temp.add(element);
				} else {
					if (element instanceof SelectedElement) {
						element = ((SelectedElement) element).getElement();
					}
					temp.add(element);
				}
			}
			
			for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
				RelationModel relation = (RelationModel) iterator.next();
				Boolean hit=false;
				ArrayList<Point> centers=relation.getSegmentCenters();
				for(int i=0; i<relation.getPath().size(); i++) {
					centers.add(relation.getPath().get(i));
				}
				for(int i=0; i<centers.size(); i++) {
					if(pointBetweenCoordinates(centers.get(i), startX, startY, endX, endY)) {
						hit=true;
						break;
					}
				}
				
				if (hit) {
					if (relation.getSelected() == false) {
						relation.setSelected(true);
					} else {
						relation.setSelected(false);
					}
				}else {
					relation.setSelected(false);
				}
			}
			
		}

		model.removeAllElement();

		for (Iterator<Element> iterator = temp.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			model.addElement(element);
		}

	}

	// Metoda provjerava da li se centar elementa nalazi unutar pravougaonika
	// odredjenog sa dvije tacke
	private Boolean pointBetweenCoordinates(Point point, int startX, int startY, int endX, int endY) {
		if (point.x >= startX && point.x <= endX)
			if (point.y >= startY && point.y <= endY)
				return true;
		return false;
	}

}
