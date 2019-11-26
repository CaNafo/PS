/***********************************************************************
 * Module:  RelationModel.java
 * Author:  Boris
 * Purpose: Defines the Class RelationModel
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
import java.util.ArrayList;

public class RelationModel {
	public static final int GENERALIZATION = 1;
	public static final int ASSOCIATION = 2;
	public static final int DEPENDENCY = 3;
	public static Color DEFAULT_LINE_COLOR = new Color(0, 128, 255);
	public static int DEFAULT_LINE_WIDTH = 2;

	private int ID;
	@SuppressWarnings("unused")
	private String name;
	private String comment;
	private String stereoType = "";
	private int lineWidth;
	private Color lineColor;
	private int relationType;
	private ArrayList<Point> path;
	private Boolean selected;

	private transient Element startElement = null;
	private transient Element endElement = null;
	private int startElementID;
	private int endElementID;

	private transient DiagramModel diagramModel;

	// Konstruktor
	public RelationModel(DiagramModel dijagram, int relationType, Element startElement, Element endElement,
			ArrayList<Point> path) {
		diagramModel = dijagram;
		this.ID = dijagram.getRelationCount();
		dijagram.incrementRelationCounter();
		this.setComment("");
		this.lineWidth = DEFAULT_LINE_WIDTH;
		this.lineColor = DEFAULT_LINE_COLOR;
		this.relationType = relationType;
		this.selected = false;

		this.path = path;
		if (path == null) {
			this.path = new ArrayList<>();
		}

		if (startElement instanceof SelectedElement) {
			startElement = ((SelectedElement) startElement).getElement();
		}
		if (endElement instanceof SelectedElement) {
			endElement = ((SelectedElement) endElement).getElement();
		}
		this.startElement = startElement;
		this.endElement = endElement;
		this.startElementID = startElement.getID();
		this.endElementID = endElement.getID();

		switch (relationType) {
		case 1:
			name = "Generalization_" + ID;
			break;
		case 2:
			name = "Association_" + ID;
			break;
		case 3:
			name = "Dependency_" + ID;
			break;
		default:
			name = "Association_" + ID;
			this.relationType = 2;
			break;
		}

		comment = "Start element:  " + startElement.getName() + "     ---------->     End element:  "
				+ endElement.getName() + ".";
	}

	public int getRelationType() {
		return relationType;
	}

	public Element getStartElement() {
		return startElement;
	}

	public Element getEndElement() {
		return endElement;
	}

	public void setStartElement(Element startElement) {

		this.startElement = startElement;
	}

	public void setEndElement(Element endElement) {

		this.endElement = endElement;
	}

	public void setDiagramModel(DiagramModel diagramModel) {
		this.diagramModel = diagramModel;
	}

	public int getStartElementID() {
		return startElementID;
	}

	public int getEndElementID() {
		return endElementID;
	}

	public ArrayList<Point> getPath() {
		return path;
	}

	public void setPath(ArrayList<Point> path) {
		this.path = path;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public void delete() {
		diagramModel.removeRelationModel(this);
	}

	/** @param point */
	public Boolean existOnLocation(Point point) {
		Boolean hit = false;
		Point startPoint = startElement.getCenter();
		Point endPoint;
		if (path.size() > 0) {
			for (int i = 0; i < path.size(); i++) {
				endPoint = path.get(i);
				if (startPoint.x == endPoint.x) {
					// Vertikalni segment
					int y1 = startPoint.y;
					int y2 = endPoint.y;
					int x = startPoint.x;
					if (y1 > y2) {
						int temp = y1;
						y1 = y2;
						y2 = temp;
					}
					if (point.y >= y1 && point.y <= y2 && point.x >= x - lineWidth / 2 - 2
							&& point.x <= x + lineWidth / 2 + 2)
						hit = true;
				} else if (startPoint.y == endPoint.y) {
					// Horizontalni segment
					int x1 = startPoint.x;
					int x2 = endPoint.x;
					int y = startPoint.y;
					if (x1 > x2) {
						int temp = x1;
						x1 = x2;
						x2 = temp;
					}
					if (point.x >= x1 && point.x <= x2 && point.y >= y - lineWidth / 2 - 2
							&& point.y <= y + lineWidth / 2 + 2)
						hit = true;
				}
				startPoint = endPoint;

			}
		}
		endPoint = endElement.getCenter();
		if (startPoint.x == endPoint.x) {
			// Vertikalni segment
			int y1 = startPoint.y;
			int y2 = endPoint.y;
			int x = startPoint.x;
			if (y1 > y2) {
				int temp = y1;
				y1 = y2;
				y2 = temp;
			}
			if (point.y >= y1 && point.y <= y2 && point.x >= x - lineWidth / 2 - 2 && point.x <= x + lineWidth / 2 + 2)
				hit = true;
		} else if (startPoint.y == endPoint.y) {
			// Horizontalni segment
			int x1 = startPoint.x;
			int x2 = endPoint.x;
			int y = startPoint.y;
			if (x1 > x2) {
				int temp = x1;
				x1 = x2;
				x2 = temp;
			}
			if (point.x >= x1 && point.x <= x2 && point.y >= y - lineWidth / 2 - 2 && point.y <= y + lineWidth / 2 + 2)
				hit = true;
		}
		return hit;
	}

	/**
	 * Metoda za iscrtavanje elementa
	 * 
	 * @param g -Grafika elementa na kome se ovaj element iscrtava
	 */
	public void drawRelation(Graphics2D g) {
		try {
			getMissingElements();

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(lineColor);
			g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			if (relationType == DEPENDENCY) {
				float[] dash = { 10 };
				g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, dash, 0));
			}

			Point startPoint = startElement.getCenter();
			Point endPoint = endElement.getCenter();
			if (path != null) {
				for (int i = 0; i < path.size(); i++) {
					endPoint = path.get(i);
					g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
					startPoint = endPoint;
				}
			}

			// Crtanje poslednjeg segmenta linije i crtanje strelice
			int endX = endElement.getCenter().x;
			int endY = endElement.getCenter().y;

			Point arrowHead1 = null;
			Point arrowHead2 = null;
			if (startPoint.x == endX) {
				if (startPoint.y < endY) {
					// 1: Linija odozgo
					endY -= endElement.getDimension().height / 2;
					arrowHead1 = new Point(endX - 10, endY - 20);
					arrowHead2 = new Point(endX + 10, endY - 20);
				} else {
					// 2: Linija odozdo
					try {
						endY += endElement.getDimension().height / 2;

					} catch (Exception e) {
						endY += UseCaseModel.DEFAULT_DIMENSIONS.height / 2;
					}
					arrowHead1 = new Point(endX - 10, endY + 20);
					arrowHead2 = new Point(endX + 10, endY + 20);
				}
			} else {
				if (startPoint.x < endX) {
					// 3: Linija s lijeva
					endX -= endElement.getDimension().width / 2;
					arrowHead1 = new Point(endX - 20, endY + 10);
					arrowHead2 = new Point(endX - 20, endY - 10);
				} else {
					// 4: Linija s desna
					endX += endElement.getDimension().width / 2;
					arrowHead1 = new Point(endX + 20, endY + 10);
					arrowHead2 = new Point(endX + 20, endY - 10);
				}
			}
			g.drawLine(startPoint.x, startPoint.y, endX, endY);

			if ((relationType == DEPENDENCY || relationType == GENERALIZATION) && arrowHead1 != null
					&& arrowHead2 != null) {
				g.setStroke(new BasicStroke(lineWidth));
				g.drawLine(arrowHead1.x, arrowHead1.y, endX, endY);
				g.drawLine(arrowHead2.x, arrowHead2.y, endX, endY);
				if (relationType == GENERALIZATION) {
					int[] xPoints = new int[] { arrowHead1.x, arrowHead2.x, endX };
					int[] yPoints = new int[] { arrowHead1.y, arrowHead2.y, endY };
					g.setColor(Color.WHITE);
					g.fillPolygon(xPoints, yPoints, 3);
					g.setColor(lineColor);
					g.drawPolygon(xPoints, yPoints, 3);
				}
			}
			if (selected) {
				ArrayList<Point> points = getSegmentCenters();
				Point decor = new Point(startPoint.x + (endX - startPoint.x) / 2,
						startPoint.y + (endY - startPoint.y) / 2); // Last segment decoration
				points.add(decor);
				points.add(startElement.getCenter());
				points.add(endElement.getCenter());
				if (path.size() > 0) {
					for (int i = 0; i < path.size(); i++) {
						points.add(path.get(i));
					}
				}
				drawSelectDecoration(g, points);
			}

			drawStereotypeText(g);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawStereotypeText(Graphics2D g) {
		String stereoType;
		if (this.stereoType.length() > 0)
			stereoType = "<<" + this.stereoType + ">>";
		else {
			stereoType = "";
			return;
		}

		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.setColor(Color.BLACK);
		FontMetrics fMetrics = g.getFontMetrics();
		int textHeight = fMetrics.getHeight();
		int textWidth = 0;

		for (int i = 0; i < stereoType.length(); i++) {
			textWidth += fMetrics.charWidth(stereoType.charAt(i));
		}

		Point location = getStereotypeLocation();
		location.x -= textWidth / 2;
		location.y -= textHeight / 2;
		g.drawString(stereoType, location.x, location.y);
	}

	private Point getStereotypeLocation() {
		Point segmentStart;
		Point segmentEnd;
		if (path.size() == 0) {
			segmentStart = startElement.getCenter();
			segmentEnd = endElement.getCenter();
		} else {
			segmentStart = path.get(path.size() - 1);
			segmentEnd = endElement.getCenter();
		}
		int startX = segmentStart.x;
		int startY = segmentStart.y;
		int endX = segmentEnd.x;
		int endY = segmentEnd.y;
		if (startX > endX) {
			int temp = startX;
			startX = endX;
			endX = temp;
		}
		if (startY > endY) {
			int temp = startY;
			startY = endY;
			endY = temp;
		}
		int locationX = startX + (endX - startX) / 2;
		int locationY = startY + (endY - startY) / 2;
		return new Point(locationX, locationY);
	}

	/**
	 * Metoda crta markiranu relaciju u sluèaju da je ona selektovana
	 * 
	 * @param g      - Grafika na kojoj se relacija iscrtava
	 * @param points - Taèke u kojima se crtaju oznake
	 */
	private void drawSelectDecoration(Graphics2D g, ArrayList<Point> points) {
		g.setColor(Color.BLACK);
		for (int i = 0; i < points.size(); i++) {
			Point dot = points.get(i);
			Point rectStart = new Point(dot.x - lineWidth / 2 - 3, dot.y - lineWidth / 2 - 3);
			Dimension rectSize = new Dimension(lineWidth + 6, lineWidth + 6);
			g.fillRect(rectStart.x, rectStart.y, rectSize.width, rectSize.height);
		}
	}

	/**
	 * Metoda pronalazi i vraæa taèke koje su centri segmenata relacije
	 * 
	 * @return - Lista taèaka koje su centri segmenata relacije
	 */
	public ArrayList<Point> getSegmentCenters() {
		ArrayList<Point> centers = new ArrayList<>();
		Point start = startElement.getCenter();
		Point end;
		if (path.size() > 0) {
			for (int i = 0; i < path.size(); i++) {
				end = path.get(i);
				if (start.x == end.x) {
					if (start.y > end.y) {
						Point temp = start;
						start = end;
						end = temp;
					}
					int x = start.x;
					int y = start.y + (end.y - start.y) / 2;
					centers.add(new Point(x, y));
				} else if (start.y == end.y) {
					if (start.x > end.x) {
						Point temp = start;
						start = end;
						end = temp;
					}
					int x = start.x + (end.x - start.x) / 2;
					int y = start.y;
					centers.add(new Point(x, y));
				}
				start = end;
			}
		}
		end = endElement.getCenter();
		if (start.x == end.x) {
			if (start.y > end.y) {
				Point temp = start;
				start = end;
				end = temp;
			}
			int x = start.x;
			int y = start.y + (end.y - start.y) / 2;
			centers.add(new Point(x, y));
		} else if (start.y == end.y) {
			if (start.x > end.x) {
				Point temp = start;
				start = end;
				end = temp;
			}
			int x = start.x + (end.x - start.x) / 2;
			int y = start.y;
			centers.add(new Point(x, y));
		}
		return centers;
	}

	/**
	 * Metoda dohvata poèetni i krajni elemenat na osnovu njihovog ID-a
	 */
	private void getMissingElements() {
		if (startElement == null)
			startElement = diagramModel.getElementById(startElementID);
		if (endElement == null) {
			endElement = diagramModel.getElementById(endElementID);
		}
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void notifyAllObservers() {
		diagramModel.notifyAllObservers();
	}

	public String getStereoType() {
		return stereoType;
	}

	public void setStereoType(String stereoType) {
		this.stereoType = stereoType;
	}

	public DiagramModel getDiagramModel() {
		return diagramModel;
	}
}