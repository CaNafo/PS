/***********************************************************************
 * Module:  ActorModel.java
 * Author:  Ca
 * Purpose: Defines the Class ActorModel
 ***********************************************************************/

package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import com.sun.prism.j2d.paint.RadialGradientPaint;
import com.sun.prism.j2d.paint.MultipleGradientPaint.CycleMethod;

public class ActorModel extends Element {

	public static Dimension DEFAULT_DIMENSIONS = new Dimension(48, 72);
	private transient DiagramModel diagramModel;
	/**
	 * Konstruktor
	 * 
	 * @param model    -Model dokumenta u koji se element smjesta
	 * @param location -Tacka u koju se smjesta gornji lijevi ugao elementa
	 */
	public ActorModel(DiagramModel model, Point location) {
		super(model, location);
		this.diagramModel = model;
		setID(model.getElementCount());
		model.incrementElementCount();
		setName("Actor_" + getID());
		super.setDimension(DEFAULT_DIMENSIONS);
	}

	/**
	 * Metoda za iscrtavanje elementa
	 * 
	 * @param g -Grafika elementa na kome se ovaj element iscrtava
	 */
	public void drawElement(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int posX = super.getLocation().x;
		int posY = super.getLocation().y;
		int width = super.getDimension().width;
		int height = super.getDimension().height;

		// Gradient fill
		Point2D center = new Point2D.Float(posX + width / 2, posY + height / 12);
		float radius = width / 6;
		Point2D focus = new Point2D.Float(posX + width / 2, posY + height / 12);
		Color[] colors = { super.getBackgroundColor(), super.getBackgroundColor(), Color.WHITE };
		float[] dist = { 0.0f, 0.2f, 1.0f };
		RadialGradientPaint gradient = new RadialGradientPaint(center, radius, focus, dist, colors,
				CycleMethod.NO_CYCLE);
		g.setPaint(gradient);
		g.fillOval(posX + width * 3 / 8, posY, width / 4, height / 6);

		// Draw Lines
		g.setColor(super.getLineColor());
		g.setStroke(new BasicStroke(super.getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.drawLine(posX, posY + height / 4, posX + width, posY + height / 4);
		g.drawLine(posX + width / 2, posY + height / 6, posX + width / 2, posY + height * 7 / 12);
		g.drawLine(posX + width / 8, posY + height, posX + width / 2, posY + height * 7 / 12);
		g.drawLine(posX + width / 2, posY + height * 7 / 12, posX + width * 7 / 8, posY + height);
		g.setColor(super.getLineColor());
		g.drawOval(posX + width * 3 / 8, posY, width / 4, height / 6);
		
		// Text rendering
		int colorIntensity = getBackgroundColor().getRed() + getBackgroundColor().getGreen()
				+ getBackgroundColor().getBlue();
		Color fgColor = (colorIntensity > 384) ? Color.BLACK : Color.WHITE;
		g.setColor(fgColor);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		FontMetrics fMetrics = g.getFontMetrics();
		int lineWidth = 0;
		String text = getName();
		for (int i = 0; i < text.length(); i++) {
			lineWidth += fMetrics.charWidth(text.charAt(i));
			if (lineWidth > width) {
				text = text.substring(0, i) + "...";
				break;
			}
		}
		g.setColor(Color.BLACK);
		g.drawString(text, posX + width / 2 - lineWidth / 2, posY + height + fMetrics.getAscent() + 2);
	}

	public void delete() {	
		diagramModel.removeElement(this);
	}

	public void changeSize() {
		// TODO: implement
	}

	public void move() {
		// TODO: implement
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
		diagramModel.notifyAllObservers();
	}

	public void setDiagramModel(DiagramModel diagramModel) {
		this.diagramModel = diagramModel;
	}

	public DiagramModel getDiagramModel() {
		return diagramModel;
	}
	
	public ActorModel clone(ActorModel actorModel) {

		this.setName(actorModel.getName() + " - Copy");
		this.setDimension(actorModel.getDimension());
		this.setLineColor(actorModel.getLineColor());
		this.setBackgroundColor(actorModel.getBackgroundColor());
		this.setLineWidth(actorModel.getLineWidth());
		
		return this;
	}
}