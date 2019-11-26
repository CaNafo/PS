/***********************************************************************
 * Module:  Element.java
 * Author:  Boris
 * Purpose: Defines the Class Element
 ***********************************************************************/

package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class Element {
	public static Color DEFAULT_LINE_COLOR = Color.BLACK;
	public static Color DEFAULT_BACKGROUND_COLOR = new Color(0, 128, 255);
	public static int DEFAULT_LINE_WIDTH = 2;

	private int ID;
	private Dimension dimension;
	private Point location;
	private String name;
	private String comment;
	private Color backgroundColor;
	private Color lineColor;
	private int lineWidth;
	private Boolean selected;

	/**
	 * Konstruktor
	 * 
	 * @param model    -Model dokumenta u koji se element smjesta
	 * @param location -Tacka u koju se smjesta gornji lijevi ugao elementa
	 */
	public Element(DiagramModel model, Point location) {
		this.location = location;
		this.comment = "";
		this.backgroundColor = DEFAULT_BACKGROUND_COLOR;
		this.lineColor = DEFAULT_LINE_COLOR;
		this.lineWidth = DEFAULT_LINE_WIDTH;
		this.selected = false;
	}

	/**
	 * Metoda za iscrtavanje elementa
	 * 
	 * @param g -Grafika elementa na kome se ovaj element iscrtava
	 */
	public abstract void drawElement(Graphics2D g);

	public abstract void delete();

	public abstract void changeSize();

	public abstract void move();

	/** @param point */
	public Boolean existOnLocation(Point point) {
		
		if (point.x >= location.x && point.x <= location.x + dimension.width)
			if (point.y >= location.y && point.y <= location.y + dimension.height)
				return true;
		return false;
	}

	/**
	 * @return Tacka koja je centar elementa
	 */
	public Point getCenter() {
		return new Point(location.x + dimension.width / 2, location.y + dimension.height / 2);
	}

	// Getters and Setters
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Point getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}