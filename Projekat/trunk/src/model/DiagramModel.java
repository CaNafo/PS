/***********************************************************************
 * Module:  DiagramModel.java
 * Author:  Boris
 * Purpose: Defines the Class DiagramModel
 ***********************************************************************/

package model;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import command.CommandInterface;

public class DiagramModel extends DocumentAbstract {
	public static Dimension DEFAULT_SIZE = new Dimension(7000, 5000);

	private int elementCount = 0;
	private int relationCount = 0;
	private Dimension size;
	private HashSet<Element> elements;

	private Collection<RelationModel> relations;

	private transient ProjectModel projectModel;
	private static transient WorkspaceModel workspaceModel;
	private transient double scaleFactor;
	private transient Stack<CommandInterface> undoStack;
	private transient Stack<CommandInterface> redoStack;

	public void setProjectModel(ProjectModel projectModel) {
		this.projectModel = projectModel;
	}

	public void setWorkspaceModel(WorkspaceModel workspaceModel) {

		DiagramModel.workspaceModel = workspaceModel;
	}

	public DiagramModel() {
		this.size = DEFAULT_SIZE;
		this.scaleFactor = 1;
		undoStack = new Stack<>();
		redoStack=new Stack<>();
	}

	public DiagramModel(String name) {
		this.size = DEFAULT_SIZE;
		this.setDocumentName(name);
		this.scaleFactor = 1;
		undoStack = new Stack<CommandInterface>();
		redoStack=new Stack<>();
	}

	public DiagramModel(Dimension size, String name) {
		this.size = size;
		this.setDocumentName(name);
		this.scaleFactor = 1;
		undoStack = new Stack<CommandInterface>();
		redoStack=new Stack<>();
	}

	public void save() {

		HashSet<Element> tempElement = new HashSet<Element>();

		for (Iterator<Element> iterator = getIteratorElement(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if (element instanceof SelectedElement) {
				tempElement.add(((SelectedElement) element).getElement());
			} else {
				tempElement.add(element);
			}
		}

		removeAllElement();

		for (Iterator<Element> iterator = tempElement.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			elements.add(element);
		}

		if (this.getDocumentPath().contains(".json"))
			DiagramSerializerDeserializer.writeToFile(this, this.getDocumentPath());
		else
			DiagramSerializerDeserializer.writeToFile(this, this.getDocumentPath() + ".json");

	}

	public void delete() {
		removeAllElement();
		removeAllRelationModel();
		if (projectModel != null)
			projectModel.removeDijagrami(this);
		else if (workspaceModel != null)
			workspaceModel.removeDiagramModel(this);
	}

	public void deleteFromDisc() {
		delete();

		String path = getDocumentPath();
		if (!path.contains(".json"))
			path = path + ".json";

		File file = new File(path);
		file.delete();
		workspaceModel.save();
	}

	public void update() {
		// TODO: implement
	}

	/** @param actionListener */
	public void setListener(ActionListener actionListener) {
		// TODO: implement
	}

	public void zoomIn() {
		// TODO: implement
	}

	public void zoomOut() {
		// TODO: implement
	}

	/** @pdGenerated default getter */
	public Collection<RelationModel> getRelationModel() {
		if (relations == null)
			relations = new HashSet<RelationModel>();
		return relations;
	}

	/** @pdGenerated default iterator getter */
	public Iterator<RelationModel> getIteratorRelationModel() {
		if (relations == null)
			relations = new HashSet<RelationModel>();
		return relations.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newRelationModel
	 */
	public void setRelationModel(Collection<RelationModel> newRelationModel) {
		removeAllRelationModel();
		for (Iterator<RelationModel> iter = newRelationModel.iterator(); iter.hasNext();)
			addRelationModel((RelationModel) iter.next());
		notifyAllObservers();
	}

	/**
	 * @pdGenerated default add
	 * @param newRelationModel
	 */
	public void addRelationModel(RelationModel newRelationModel) {
		if (newRelationModel == null)
			return;
		if (this.relations == null)
			this.relations = new HashSet<RelationModel>();
		if (!this.relations.contains(newRelationModel))
			this.relations.add(newRelationModel);
		notifyAllObservers();
	}

	/**
	 * @pdGenerated default remove
	 * @param oldRelationModel
	 */
	public void removeRelationModel(RelationModel oldRelationModel) {
		if (oldRelationModel == null)
			return;
		if (this.relations != null)
			if (this.relations.contains(oldRelationModel))
				this.relations.remove(oldRelationModel);
		notifyAllObservers();
	}

	/** @pdGenerated default removeAll */
	public void removeAllRelationModel() {
		if (relations != null)
			relations.clear();
		notifyAllObservers();
	}

	/** @pdGenerated default getter */
	public Collection<Element> getElement() {
		if (elements == null)
			elements = new HashSet<Element>();
		return elements;
	}

	/** @pdGenerated default iterator getter */
	public Iterator<Element> getIteratorElement() {
		if (elements == null)
			elements = new HashSet<Element>();
		return elements.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newElement
	 */
	public void setElement(Collection<Element> newElement) {
		removeAllElement();
		for (Iterator<Element> iter = newElement.iterator(); iter.hasNext();)
			addElement((Element) iter.next());
		notifyAllObservers();
	}

	/**
	 * @pdGenerated default add
	 * @param newElement
	 */
	public void addElement(Element newElement) {
		if (newElement == null)
			return;
		if (this.elements == null)
			this.elements = new HashSet<Element>();
		if (!this.elements.contains(newElement))
			this.elements.add(newElement);
		notifyAllObservers();
	}

	/**
	 * @pdGenerated default remove
	 * @param oldElement
	 */
	public void removeElement(Element oldElement) {
		HashSet<RelationModel> deletedRelations = new HashSet<RelationModel>();

		for (Iterator<RelationModel> iterator = getIteratorRelationModel(); iterator.hasNext();) {
			RelationModel relation = (RelationModel) iterator.next();
			if (relation.getStartElement().equals(oldElement) || relation.getEndElement().equals(oldElement)) {
				deletedRelations.add(relation);
			}
		}

		for (Iterator<RelationModel> iterator = deletedRelations.iterator(); iterator.hasNext();) {
			RelationModel relation = (RelationModel) iterator.next();
			this.removeRelationModel(relation);
		}

		if (oldElement == null)
			return;
		if (this.elements != null)
			if (this.elements.contains(oldElement))
				this.elements.remove(oldElement);

		for (Iterator<Element> iterator = this.getIteratorElement(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if (element instanceof SelectedElement) {
				if (((SelectedElement) element).getElement() == oldElement) {
					this.elements.remove((SelectedElement) element);
					return;
				}
			}
		}

		notifyAllObservers();
	}

	/** @pdGenerated default removeAll */
	public void removeAllElement() {
		if (elements != null)
			elements.clear();
		notifyAllObservers();
	}

	public int getElementCount() {
		return elementCount;
	}

	public int getRelationCount() {
		return relationCount;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public double getScaleFactor() {
		return scaleFactor;
	}

	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	public void incrementElementCount() {
		elementCount++;
	}

	public void incrementRelationCounter() {
		relationCount++;
	}

	public Element getElementById(int ID) {
		for (Iterator<Element> iterator = getIteratorElement(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if (element.getID() == ID) {
				return element;
			}
		}
		return null;
	}

	public static DiagramModel open(String documentPath) {
		DiagramModel model = (DiagramSerializerDeserializer.readFromFile(documentPath));

		for (Iterator<Element> iterator = model.getIteratorElement(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if (element instanceof UseCaseModel) {
				((UseCaseModel) element).setDiagramModel(model);
			}

			if (element instanceof ActorModel) {
				((ActorModel) element).setDiagramModel(model);
			}
		}
		for (Iterator<RelationModel> iterator = model.getIteratorRelationModel(); iterator.hasNext();) {
			RelationModel relation = (RelationModel) iterator.next();

			relation.setDiagramModel(model);
		}
		return model;
	}

	public WorkspaceModel getWorkspaceModel() {
		return workspaceModel;
	}

	public Stack<CommandInterface> getUndoStack() {
		if (undoStack == null)
			undoStack = new Stack<CommandInterface>();
		return undoStack;
	}

	public void setUndoStack(Stack<CommandInterface> undoStack) {
		this.undoStack = undoStack;
	}

	public Stack<CommandInterface> getRedoStack() {
		return redoStack;
	}
	
}