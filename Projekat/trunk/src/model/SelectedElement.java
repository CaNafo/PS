/***********************************************************************
 * Module:  SelectedElement.java
 * Author:  Ca
 * Purpose: Defines the Class SelectedElement
 ***********************************************************************/

package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public class SelectedElement extends Element {

	private Element element;

	public SelectedElement(DiagramModel model, Element element) {
		super(model, element.getLocation());
		this.element = element;
	}

	public void drawElement(Graphics2D g) {
		element.drawElement(g);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Point A = element.getLocation();
		int width = element.getDimension().width;
		int height = element.getDimension().height;
		float[] dash = { 5 };
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, dash, 0));
		g.setColor(Color.BLACK);
		g.drawRect(A.x, A.y, width, height);

	}
	
	@Override
	public void setLocation(Point location) {
		element.setLocation(location);
	}
	
	@Override
	public Boolean existOnLocation(Point point) {
		return element.existOnLocation(point);
	}
	
	@Override
	public Point getCenter() {
		return element.getCenter();
	}

	public void delete() {
		// TODO: implement
	}

	public void changeSize() {
		// TODO: implement
	}

	public void move() {
		// TODO: implement
	}
	
	public Element getElement() {
		return element;
	}
}