package controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import application.CustomJPopoupMenu;
import command.ChangeElementPropertiesCommand;
import command.CommandInterface;
import command.CreateActorCommand;
import command.CreateRelationCommand;
import command.CreateUseCaseCommand;
import command.DeleteElementCommand;
import command.MoveElementCommand;
import command.PasteElementCommand;
import command.SelectDeselectCommand;
import model.ActorModel;
import model.DiagramModel;
import model.DocumentTreeModel;
import model.Element;
import model.RelationModel;
import model.SelectedElement;
import model.UseCaseModel;
import view.DiagramView;
import view.DocumentTreeView;
import view.MenuBarView;
import view.DiagramToolbar;

public class DiagramEditController {
	private DiagramModel model;
	private DiagramView view;
	private DiagramToolbar toolbar;
	private Point startPoint;
	private Point startPointRelative;
	private Point endPoint;
	private Point ortogonalPoint;
	private Graphics2D tempGraphics = null;
	private Element startElement;
	private Element endElement;
	private Element tempCopyElement = null;
	private Element movingElement = null;
	private Point moveElementStartLocation;
	private ArrayList<Point> relationPath;

	private int x = 0, y = 0;
	/**
	 * Mouse Listener
	 */
	private MouseListener diagramMouseListener = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent arg0) {

			x = 0;
			y = 0;
			// Selekcija pravougaonikom
			if (toolbar.getBtnSelectRect().isPressed()) {
				endPoint = scaleFctrDivide(arg0.getPoint());
				CommandInterface command;

				if (toolbar.getBtnSelectRect().isPressed()) {
					String ctrl = (arg0.isControlDown()) ? "controldown" : "";
					String[] args = new String[] { "dragged", ctrl, String.valueOf(startPoint.x),
							String.valueOf(startPoint.y), String.valueOf(endPoint.x), String.valueOf(endPoint.y) };
					command = new SelectDeselectCommand(model, startPoint);
					command.execute(args);
					model.notifyAllObservers();
				}
				tempGraphics = null;
			}

			// Pomjeranje
			if (toolbar.getBtnSelectArrow().isPressed() && movingElement != null) {
				toolbar.setMouseCursor(Cursor.getDefaultCursor());
				toolbar.getRootPane().setCursor(Cursor.getDefaultCursor());					
				CommandInterface command = new MoveElementCommand(model, movingElement);
				String[] args = new String[] { String.valueOf(moveElementStartLocation.x),
						String.valueOf(moveElementStartLocation.y) };
				command.execute(args);
				model.getUndoStack().push(command);
			}

		}

		@Override
		public void mousePressed(MouseEvent arg0) {

			view.requestFocus();
			startPoint = scaleFctrDivide(arg0.getPoint());
			startPointRelative = arg0.getPoint();
			CommandInterface command;
			Point clicked = scaleFctrDivide(new Point(arg0.getX(), arg0.getY()));
			tempGraphics = (Graphics2D) view.getGraphics();

			// Dohvatanje elementa koji ce se pomjerati
			if (toolbar.getBtnSelectArrow().isPressed()) {
				for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					if (element.existOnLocation(clicked)) {
						movingElement = element;
						moveElementStartLocation = new Point(movingElement.getLocation().x,
								movingElement.getLocation().y);
					}
				}
			}

			////////////////
			// Crtanje veza
			if (toolbar.getBtnAssociation().isPressed() || toolbar.getBtnDependency().isPressed()
					|| toolbar.getBtnGeneralization().isPressed()) {

				if (startElement == null) {
					// Dohvatanje pocetnog elementa
					for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
						Element element = (Element) iterator.next();
						if (element.existOnLocation(clicked)) {
							startElement = element;
						}
					}
				} else {
					// Dohvatanje tacke ili zavrsnog elementa
					Boolean elementHit = false;
					for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
						Element element = (Element) iterator.next();
						if (element.existOnLocation(clicked)) {
							// Dohvatanje zavrsnog elementa
							elementHit = true;
							endElement = element;
						}
					}
					if (!elementHit) {
						// Dohvatanje tacke
						relationPath.add(ortogonalPoint);
					}
				}

				//////////////////////
				// Kreiranje relacije
				if (startElement != null && endElement != null) {
					RelationModel relation = null;
					if (toolbar.getBtnAssociation().isPressed()) {
						relation = new RelationModel(model, RelationModel.ASSOCIATION, startElement, endElement,
								relationPath);
					} else if (toolbar.getBtnDependency().isPressed()) {
						relation = new RelationModel(model, RelationModel.DEPENDENCY, startElement, endElement,
								relationPath);
					} else if (toolbar.getBtnGeneralization().isPressed()) {
						relation = new RelationModel(model, RelationModel.GENERALIZATION, startElement, endElement,
								relationPath);
					}
					command = new CreateRelationCommand(model, relation);
					command.execute(null);
					model.getUndoStack().push(command);
					model.notifyAllObservers();
					startElement = null;
					endElement = null;
					relationPath = new ArrayList<>();
				}

			}

			// Selekcija strelicom
			if (toolbar.getBtnSelectArrow().isPressed()) {
				command = new SelectDeselectCommand(model, clicked);
				String ctrl = (arg0.isControlDown()) ? "controldown" : "";
				String[] args = new String[] { "static", ctrl };
				command.execute(args);

				tempCopyElement = null;
				model.notifyAllObservers();
			}
			if (toolbar.getBtnCopy().isPressed()) {
				command = new SelectDeselectCommand(model, clicked);
				String ctrl = (arg0.isControlDown()) ? "controldown" : "";
				String[] args = new String[] { "static", ctrl };
				command.execute(args);

				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Image paste = toolkit.getImage("images/paste.png");
				Cursor pasteCursor = toolkit.createCustomCursor(paste, new Point(16, 16), "paste");
				toolbar.setMouseCursor(pasteCursor);

				if (tempCopyElement != null) {

					Point newLocation = scaleFctrDivide(new Point(arg0.getX(), arg0.getY()));

					if (((SelectedElement) tempCopyElement).getElement() instanceof UseCaseModel) {
						UseCaseModel useCaseModel = new UseCaseModel(model, newLocation)
								.clone((UseCaseModel) ((SelectedElement) tempCopyElement).getElement());
						
						CommandInterface command1 = new PasteElementCommand(model, useCaseModel);
						command1.execute(args);
						
						model.getUndoStack().push(command1);
						model.notifyAllObservers();
						
					} else if (((SelectedElement) tempCopyElement).getElement() instanceof ActorModel) {
						ActorModel actorModel = new ActorModel(model, newLocation)
								.clone((ActorModel) ((SelectedElement) tempCopyElement).getElement());

						CommandInterface command1 = new PasteElementCommand(model, actorModel);
						command1.execute(args);
						
						model.getUndoStack().push(command1);
						model.notifyAllObservers();
					}
					DocumentTreeView documentTreeView = DocumentTreeView.documentTreeView;
					DocumentTreeModel.clearTree(documentTreeView);

					DocumentTreeView.expandAllNodes(documentTreeView, 0, documentTreeView.getRowCount());

				}

				for (@SuppressWarnings("rawtypes")
				Iterator iterator = model.getIteratorElement(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					if (element instanceof SelectedElement) {
						tempCopyElement = element;
					}
				}
			}

			if (arg0.getButton() == MouseEvent.BUTTON3) {
				DiagramModel model = view.getModel();
				CustomJPopoupMenu jPopupMenu = null;
				boolean selected = false;

				for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					if (element instanceof SelectedElement) {
						jPopupMenu = new CustomJPopoupMenu(element, view);
						jPopupMenu.setLocation(arg0.getX(), arg0.getY());
						selected = true;
					}
				}
				for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
					RelationModel relationModel = (RelationModel) iterator.next();
					if (relationModel.getSelected()) {
						jPopupMenu = new CustomJPopoupMenu(relationModel, view);
						jPopupMenu.setLocation(arg0.getX(), arg0.getY());
						selected = true;
					}
				}

				if (!selected && view.getComponentPopupMenu() != null)
					view.getComponentPopupMenu().removeAll();
			}

			// Crtanje Ucesnika
			if (toolbar.getBtnActor().isPressed()) {
				int elementCountBefore = model.getElementCount();
				command = new CreateActorCommand(model, clicked);
				command.execute(null);
				if (model.getElementCount() > elementCountBefore)
					model.getUndoStack().push(command);
				model.notifyAllObservers();
			}

			// Crtanje Slucaja Upotrebe
			if (toolbar.getBtnUseCase().isPressed()) {
				int elementCountBefore = model.getElementCount();
				command = new CreateUseCaseCommand(model, clicked);
				command.execute(null);
				if (model.getElementCount() > elementCountBefore)
					model.getUndoStack().push(command);
				model.notifyAllObservers();
			}

			// Brisanje elementa alatkom sa toolbar-a
			if (toolbar.getBtnDelete().isPressed()) {
				int elementCountBefore = model.getElement().size();
				int relationCountBefore = model.getRelationModel().size();
				command = new DeleteElementCommand(model);
				String argX = String.valueOf(scaleFctrDivide(clicked).x);
				String argY = String.valueOf(scaleFctrDivide(clicked).y);
				String[] args = new String[] { "one-at-a-time", argX, argY };
				command.execute(args);
				if (model.getElement().size() < elementCountBefore
						|| model.getRelationModel().size() < relationCountBefore)
					model.getUndoStack().push(command);
				model.notifyAllObservers();
				DocumentTreeView documentTreeView = DocumentTreeView.documentTreeView;
				DocumentTreeModel.clearTree(documentTreeView);

				DocumentTreeView.expandAllNodes(documentTreeView, 0, documentTreeView.getRowCount());
			}

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			CommandInterface command;

			// Otvaranje prozora za properties
			if ((toolbar.getBtnSelectArrow().isPressed() || toolbar.getBtnSelectRect().isPressed())
					&& arg0.getClickCount() == 2) {
				Point clicked = scaleFctrDivide(arg0.getPoint());
				command = new ChangeElementPropertiesCommand(model, clicked);
				command.execute(null);
				model.getUndoStack().push(command);
				model.notifyAllObservers();
			}
		}
	};

	/**
	 * Mouse Motion Listener
	 */
	private MouseMotionListener diagramMouseMotionListener = new MouseMotionListener() {

		@Override
		public void mouseMoved(MouseEvent e) {

			// Crtanje veza (Guidelines)
			if (startElement != null && endElement == null) {
				Point last = startElement.getCenter();
				try {
					last = relationPath.get(relationPath.size() - 1);
				} catch (Exception e2) {
					// TODO: handle exception
				}
				ortogonalPoint = scaleFctrDivide(new Point(e.getX(), e.getY()));

				if (ortogonalPoint.x > model.getSize().width) {
					ortogonalPoint.x = model.getSize().width;
				}
				if (ortogonalPoint.y > model.getSize().height) {
					ortogonalPoint.y = model.getSize().height;
				}

				if (Math.abs(ortogonalPoint.x - last.x) > Math.abs(ortogonalPoint.y - last.y)) {
					ortogonalPoint.y = last.y;
				} else {
					ortogonalPoint.x = last.x;
				}

				model.notifyAllObservers();

				// Iscrtavanje pomocnih linija
				tempGraphics.setColor(new Color(0, 128, 255));
				float[] dash = { 2 };
				tempGraphics.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, dash, 0));

				if (relationPath.size() >= 1) {
					Point p = scaleFctrMultiply(relationPath.get(0));
					tempGraphics.drawLine(scaleFctrMultiply(startElement.getCenter()).x,
							scaleFctrMultiply(startElement.getCenter()).y, p.x, p.y);
				}

				for (int i = 0; i < relationPath.size() - 1; i++) {
					Point p1 = scaleFctrMultiply(relationPath.get(i));
					Point p2 = scaleFctrMultiply(relationPath.get(i + 1));
					tempGraphics.drawLine(p1.x, p1.y, p2.x, p2.y);
				}
				tempGraphics.drawLine(scaleFctrMultiply(last).x, scaleFctrMultiply(last).y,
						scaleFctrMultiply(ortogonalPoint).x, scaleFctrMultiply(ortogonalPoint).y);
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// Pomjeranje
			if (x == 0 && y == 0) {
				x = e.getX();
				y = e.getY();
			}

			if (toolbar.getBtnSelectArrow().isPressed()) {
				for (@SuppressWarnings("rawtypes")
				Iterator iterator = model.getIteratorElement(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					
					if (element instanceof SelectedElement) {
						BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
						Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
						    cursorImg, new Point(0, 0), "blank cursor");
						toolbar.getRootPane().setCursor(blankCursor);					
						Toolkit toolkit = Toolkit.getDefaultToolkit();
						Cursor arrowCursor = toolkit.createCustomCursor(cursorImg, new Point(16, 16), "arrow");
						toolbar.setMouseCursor(arrowCursor);
						
						Point oldLocation = ((SelectedElement) element).getLocation();

						
						if (x > e.getX())
							oldLocation.x = oldLocation.x - 7;
						else if (x < e.getX())
							oldLocation.x = oldLocation.x + 7;

						if (y > e.getY())
							oldLocation.y = oldLocation.y - 7;
						else if (y < e.getY())
							oldLocation.y = oldLocation.y + 7;

						x = e.getX();
						y = e.getY();

						((SelectedElement) element).getElement().setLocation(oldLocation);
					}
				}

				model.notifyAllObservers();

			}

			// Crtanje pravougaonika za selekciju
			if (toolbar.getBtnSelectRect().isPressed()) {

				model.notifyAllObservers();

				tempGraphics.setColor(new Color(0, 128, 255));
				float[] dash = { 2 };
				tempGraphics.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, dash, 0));
				int startX = startPointRelative.x;
				int startY = startPointRelative.y;

				int currentX = e.getPoint().x;
				int currentY = e.getPoint().y;
				tempGraphics.drawLine(startX, startY, currentX, startY);
				tempGraphics.drawLine(startX, startY, startX, currentY);
				tempGraphics.drawLine(startX, currentY, currentX, currentY);
				tempGraphics.drawLine(currentX, startY, currentX, currentY);

			}
		}

	};

	@SuppressWarnings("unused")
	private MouseWheelListener diagramMouseWheelListener = new MouseWheelListener() {

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (e.getWheelRotation() > 0) {
				// Zoom out
				int value = (int) (model.getScaleFactor() * 100);
			} else if (e.getWheelRotation() < 0) {
				// Zoom in
			}
		}
	};

	/**
	 * Key Listener
	 */
	private KeyListener diagramKeyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {

			// Brisanje elementa
			if (e.getKeyCode() == KeyEvent.VK_DELETE) {
				int elementCountBefore = model.getElement().size();
				int relationCountBefore = model.getRelationModel().size();
				CommandInterface command = new DeleteElementCommand(model);
				command.execute(null);
				if (model.getElement().size() < elementCountBefore
						|| model.getRelationModel().size() < relationCountBefore)
					model.getUndoStack().push(command);
				model.notifyAllObservers();
			}

			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if (toolbar.getBtnCopy().isPressed()) {
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Image copy = toolkit.getImage("images/copy.png");
					Cursor copyCursor = toolkit.createCustomCursor(copy, new Point(16, 16), "paste");
					toolbar.setMouseCursor(copyCursor);
					tempCopyElement = null;
				}
			}

			int down = KeyEvent.CTRL_DOWN_MASK;

			if ((e.getModifiersEx() & down) == down && (e.getKeyCode() == KeyEvent.VK_C)) {
				for (@SuppressWarnings("rawtypes")
				Iterator iterator = model.getIteratorElement(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					if (element instanceof SelectedElement) {
						tempCopyElement = element;
						if (toolbar.getBtnSelectArrow().isPressed()) {
							toolbar.getBtnSelectArrow().setButtonPresseed(false);
							toolbar.getBtnCopy().setButtonPresseed(true);
						}
					}
				}
			}

			// Odustajanje od crtanja veze
			if (toolbar.getBtnAssociation().isPressed() || toolbar.getBtnDependency().isPressed()
					|| toolbar.getBtnGeneralization().isPressed()) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					startElement = null;
					endElement = null;
					relationPath = new ArrayList<>();
				}
			}

			// �uvanje dijagrama

			if ((e.getModifiersEx() & down) == down && (e.getKeyCode() == KeyEvent.VK_S)) {
				model.save();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * Focus Listener
	 */
	private FocusListener diagramFocusListener = new FocusListener() {

		@Override
		public void focusLost(FocusEvent e) {

			// Crtanje veza
			startElement = null;
			endElement = null;
			relationPath = new ArrayList<>();
		}

		@Override
		public void focusGained(FocusEvent e) {
		}
	};

	private ActionListener undoListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!model.getUndoStack().isEmpty()) {
				CommandInterface command = (CommandInterface) model.getUndoStack().pop();
				command.unexecute();
				model.notifyAllObservers();
				model.getRedoStack().push(command);
			}
		}
	};

	private ActionListener redoListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!model.getRedoStack().isEmpty()) {
				CommandInterface command = model.getRedoStack().pop();
				command.execute(null);
				model.notifyAllObservers();
				model.getUndoStack().push(command);
			}
		}
	};

	// Konstruktor
	public DiagramEditController(DiagramModel model, DiagramView view, DiagramToolbar toolbar, MenuBarView barView) {
		this.model = model;
		this.view = view;
		this.toolbar = toolbar;
		relationPath = new ArrayList<>();
		startElement = null;
		endElement = null;

		view.addMouseListener(diagramMouseListener);
		view.addMouseMotionListener(diagramMouseMotionListener);
		view.addKeyListener(diagramKeyListener);
		view.addFocusListener(diagramFocusListener);
		toolbar.getBtnUndo().addActionListener(undoListener);
		toolbar.getBtnRedo().addActionListener(redoListener);
		barView.getItemUndo().addActionListener(undoListener);	
		barView.getItemRedo().addActionListener(redoListener);	

	}

	public MouseListener getDiagramMouseListener() {
		return diagramMouseListener;
	}

	public MouseMotionListener getDiagramMouseMotionListener() {
		return diagramMouseMotionListener;
	}

	public KeyListener getDiagramKeyListener() {
		return diagramKeyListener;
	}

	public FocusListener getDiagramFocusListener() {
		return diagramFocusListener;
	}

	/**
	 * Metoda koja transformise zadatu tacku koristeci scale faktor koji se nalazi u
	 * Diagram modelu
	 * 
	 * Prihvata absolutne koordinate tacke i vraca relativne koordinate zavisne od
	 * navedenog faktora
	 * 
	 * @param point
	 * @return
	 */
	private Point scaleFctrDivide(Point point) {
		int x = point.x;
		int y = point.y;
		x = (int) (((double) x) / model.getScaleFactor());
		y = (int) (((double) y) / model.getScaleFactor());
		return new Point(x, y);
	}

	private Point scaleFctrMultiply(Point point) {
		int x = point.x;
		int y = point.y;
		x = (int) (((double) x) * model.getScaleFactor());
		y = (int) (((double) y) * model.getScaleFactor());
		return new Point(x, y);
	}

}
