package view;

import java.util.Iterator;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import application.CustomTreeCellRenderer;
import controller.DocumentTreeController;
import controller.TreeMenuController;
import model.DiagramModel;
import model.DocumentTreeModel;
import model.Element;
import model.ProjectModel;
import model.RelationModel;
import model.SelectedElement;

@SuppressWarnings("serial")
public class DocumentTreeView extends JTree {
	private static  DocumentTreeModel model;
	public static DocumentTreeView documentTreeView;	
	
	public DocumentTreeView() {
		this.setRootVisible(false);
		this.setShowsRootHandles(true);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setEditable(false);
		
		
		setUp();
		
		this.setCellRenderer(new CustomTreeCellRenderer());		
		this.addMouseListener(new DocumentTreeController(model, this));	
		new TreeMenuController(this);		
	}
	
	public DocumentTreeView(DocumentTreeModel model) {
		super(model);
		documentTreeView = this;
		DocumentTreeView.model = model;
		this.setRootVisible(false);
		this.setShowsRootHandles(true);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setEditable(false);
		this.setUp();
		this.setCellRenderer(new CustomTreeCellRenderer());
		new TreeMenuController(this);
		this.addMouseListener(new DocumentTreeController(model, this));
		
	}

	public void setUp() {
		DefaultMutableTreeNode ws_node = addNode(model.getWorkSpace());
		
		for (@SuppressWarnings("rawtypes")
		Iterator iteratorProject = model.getWorkSpace().getIteratorProjekti(); iteratorProject.hasNext();) {
			ProjectModel projectModel = (ProjectModel) iteratorProject.next();
			DefaultMutableTreeNode proj_node = addNode(ws_node, projectModel, true);
			for (@SuppressWarnings("rawtypes")
			Iterator iteratorDiagram = projectModel.getIteratorDijagrami(); iteratorDiagram.hasNext();) {
				DiagramModel diagramModel = (DiagramModel) iteratorDiagram.next();
				DefaultMutableTreeNode diagramNode = addNode(proj_node, diagramModel, false);
				DefaultMutableTreeNode elementNode = addNode(diagramNode,"Elements", false);
				DefaultMutableTreeNode relationsNode = addNode(diagramNode,"Relations", false);
				for (@SuppressWarnings("rawtypes")
				Iterator iteratorElement = diagramModel.getIteratorElement(); iteratorElement.hasNext();) {
					Element element = (Element) iteratorElement.next();
					if (element instanceof SelectedElement) {
						SelectedElement selectedElement = (SelectedElement) element;
						addNode(elementNode,selectedElement.getElement(),false);
					}
					else
						addNode(elementNode,element,false);
				}
				for (@SuppressWarnings("rawtypes")
				Iterator iteratorRelation = diagramModel.getIteratorRelationModel(); iteratorRelation.hasNext();) {
					RelationModel relationModel = (RelationModel) iteratorRelation.next();
					addNode(relationsNode,relationModel,false);
				}
			}

		}
		
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = model.getWorkSpace().getIteratorDiagramModel(); iterator.hasNext();) {
			DiagramModel diagramModel = (DiagramModel) iterator.next();
			DefaultMutableTreeNode diagramNode = addNode(ws_node, diagramModel, true);			
			DefaultMutableTreeNode elementNode = addNode(diagramNode,"Elements", false);
			DefaultMutableTreeNode relationsNode = addNode(diagramNode,"Relations", false);
			for (@SuppressWarnings("rawtypes")
			Iterator iteratorElement = diagramModel.getIteratorElement(); iteratorElement.hasNext();) {
				Element element = (Element) iteratorElement.next();
				addNode(elementNode,element,false);
			}
			for (@SuppressWarnings("rawtypes")
			Iterator iteratorRelation = diagramModel.getIteratorRelationModel(); iteratorRelation.hasNext();) {
				RelationModel relationModel = (RelationModel) iteratorRelation.next();
				addNode(relationsNode,relationModel,false);
			}
		}
		expandAllNodes(this,0,this.getRowCount());
	}

	public DefaultMutableTreeNode addNode(Object child) {
		DefaultMutableTreeNode parent = null;
		TreePath parentPath = getSelectionPath();

		if (parentPath == null) {
			parent = (DefaultMutableTreeNode) model.getRoot();
		} else {
			parent = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
		}

		return addNode(parent, child, true);
	}

	public DefaultMutableTreeNode addNode(DefaultMutableTreeNode parent, Object child, boolean visible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = (DefaultMutableTreeNode) model.getRoot();
		}

		model.insertNodeInto(childNode, parent, parent.getChildCount());

		if (visible) {
			scrollPathToVisible(new TreePath(childNode.getPath()));
		}

		return childNode;
	}

	public void removeCurrentNode() {
		TreePath currentSelection = getSelectionPath();

		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
			MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());

			if (parent != null) {
				((DefaultTreeModel) treeModel).removeNodeFromParent(currentNode);
				return;
			}
		}
		
	}
	 public static void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	   for(int i=startingIndex;i<rowCount;++i){
	    	if(!(tree.getPathForRow(i).getLastPathComponent().toString() == "Elements" || 
	    					tree.getPathForRow(i).getLastPathComponent().toString() == "Relations"))
	    		tree.expandRow(i);
	    }
	    
	    if(tree.getRowCount()!=rowCount){
	       expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	
	}
	
	public static void update() {
		documentTreeView.removeCurrentNode();	
	}
}
