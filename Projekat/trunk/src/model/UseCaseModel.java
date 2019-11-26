/***********************************************************************
 * Module:  UseCaseModel.java
 * Author:  Ca
 * Purpose: Defines the Class UseCaseModel
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
import java.util.ArrayList;

import com.sun.prism.j2d.paint.MultipleGradientPaint.CycleMethod;
import com.sun.prism.j2d.paint.RadialGradientPaint;

public class UseCaseModel extends Element {
	public static Dimension DEFAULT_DIMENSIONS = new Dimension(120, 80);

	private Specification specification;
	private transient DiagramModel diagramModel;

	/**
	 * Konstruktor
	 * 
	 * @param model    -Model dokumenta u koji se element smjesta
	 * @param location -Tacka u koju se smjesta gornji lijevi ugao elementa
	 */
	public UseCaseModel(DiagramModel model, Point location) {
		super(model, location);
		super.setID(model.getElementCount());
		model.incrementElementCount();
		super.setName("UseCase_" + getID());
		this.specification = new Specification();
		this.setDimension(DEFAULT_DIMENSIONS);
		this.diagramModel = model;
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

		// Draw outline
		g.setStroke(new BasicStroke(super.getLineWidth()));
		g.setColor(super.getLineColor());
		g.drawOval(posX, posY, width, height);

		// Gradient fill
		Point2D center = new Point2D.Float(posX + width / 2, posY + height / 2);
		float radius = width;
		Point2D focus = new Point2D.Float(posX + width / 2, posY + height / 2);
		float[] dist = { 0.0f, 0.2f, 1.0f };
		Color[] colors = { super.getBackgroundColor(), super.getBackgroundColor(), Color.WHITE };
		RadialGradientPaint gradient = new RadialGradientPaint(center, radius, focus, dist, colors,
				CycleMethod.NO_CYCLE);
		g.setPaint(gradient);
		g.fillOval(posX + getLineWidth() / 2, posY + getLineWidth() / 2, width - getLineWidth() / 2,
				height - getLineWidth() / 2);

		// Text rendering
		int colorIntensity = getBackgroundColor().getRed() + getBackgroundColor().getGreen()
				+ getBackgroundColor().getBlue();
		Color fgColor = (colorIntensity > 384) ? Color.BLACK : Color.WHITE;
		g.setColor(fgColor);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		FontMetrics fMetrics = g.getFontMetrics();

		String text = getName();
		ArrayList<String> list = new ArrayList<>();
		int lineWidth = 0;
		for (int i = 0, j = 0; i < text.length(); i++) {
			lineWidth += fMetrics.charWidth(text.charAt(i));
			if (lineWidth > width * 2 / 3) {
				
				String substr=text.substring(j, i);
				if(text.charAt(i)!=' ') {
					substr=substr.concat("-");
				}
				list.add(substr);
				lineWidth = 0;
				j = i;
			}
			if (i == text.length() - 1)
				list.add(text.substring(j, i + 1));
			if (list.size() * fMetrics.getHeight() > height / 3 * 2) {
				list.remove(list.get(list.size() - 1));
				list.add("...");
				break;
			}
		}

		for (String string : list) {
			lineWidth = 0;
			for (int i = 0; i < string.length(); i++)
				lineWidth += fMetrics.charWidth(string.charAt(i));
			int x = posX + width / 2 - lineWidth / 2;
			int y = posY + height / 2 + fMetrics.getHeight() * 3 / 4 - list.size() * fMetrics.getHeight() / 2
					+ fMetrics.getHeight() * list.indexOf(string);
			g.drawString(string, x, y);
		}
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

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
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

	public UseCaseModel clone(UseCaseModel useCaseModel) {
		this.specification = useCaseModel.specification;
		this.setName(useCaseModel.getName() + " - Copy");
		this.setDimension(useCaseModel.getDimension());
		this.setLineColor(useCaseModel.getLineColor());
		this.setBackgroundColor(useCaseModel.getBackgroundColor());
		this.setLineWidth(useCaseModel.getLineWidth());
		
		return this;
	}
}