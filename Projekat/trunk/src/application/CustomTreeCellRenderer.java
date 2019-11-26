package application;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import controller.TreeMenuController;
import model.ActorModel;
import model.DiagramModel;
import model.ProjectModel;
import model.RelationModel;
import model.SelectedElement;
import model.UseCaseModel;
import model.WorkspaceModel;

@SuppressWarnings("serial")
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

	private static JTree tree;
	
	
	public CustomTreeCellRenderer() {
		setOpaque(true);
		setBackgroundSelectionColor(new Color(217, 217, 217));
		setBackgroundNonSelectionColor(Color.BLUE);
	}

	@SuppressWarnings("static-access")
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		setOpaque(true);		
		setBackground(tree.getBackground());
		setForeground(tree.getForeground());
		setText(value.toString());
		
		Object obj = ((DefaultMutableTreeNode) value).getUserObject();

		if (obj instanceof WorkspaceModel) {
			Image image = getToolkit().getImage("images" + File.separator + "tree-workspace.png");
			setIcon(new ImageIcon(image));
			WorkspaceModel ws = (WorkspaceModel)obj;
			setText(ws.getDocumentName());
		} else if (obj instanceof ProjectModel) {
			Image image = getToolkit().getImage("images" + File.separator + "folder-closed.png");
			ProjectModel project = (ProjectModel)obj;
			setText(project.getDocumentName());
			setIcon(new ImageIcon(image));
		} else if (obj instanceof DiagramModel) {
			Image image = getToolkit().getImage("images" + File.separator + "tree-diagram.png");
			DiagramModel diagram = (DiagramModel)obj;
			setText(diagram.getDocumentName());
			setIcon(new ImageIcon(image));
		} else if (obj instanceof ActorModel) {
			ActorModel actorModel = (ActorModel)obj;
			Image image = getToolkit().getImage("images" + File.separator + "actor-tree.png");
			setIcon(new ImageIcon(image));
			setText(actorModel.getName());
		}else if (obj instanceof UseCaseModel) {
			UseCaseModel uc = (UseCaseModel)obj;
			Image image = getToolkit().getImage("images" + File.separator + "use-case-tree.png");
			setIcon(new ImageIcon(image));
			setText(uc.getName());
		}else if (obj instanceof RelationModel) {
			Image image;
			RelationModel relationModel = (RelationModel)obj;
			if(relationModel.getRelationType() == 1) {
				image = getToolkit().getImage("images" + File.separator + "generalization-tree.png");
				setIcon(new ImageIcon(image));				
				setText("Generalization");
			}
			else if(relationModel.getRelationType() == 2) {
				image = getToolkit().getImage("images" + File.separator + "association-tree.png");
				setIcon(new ImageIcon(image));
				setText("Associoation");
			}
			else {
				image = getToolkit().getImage("images" + File.separator + "dependency-tree.png");
				setIcon(new ImageIcon(image));
				setText("Dependency");
			}
			
		}else if(obj instanceof SelectedElement){
			SelectedElement selectedElement = (SelectedElement)obj;
			if (selectedElement.getElement() instanceof ActorModel) {
				Image image = getToolkit().getImage("images" + File.separator + "actor-tree.png");
				setIcon(new ImageIcon(image));
				setText("Actor");
			}else if (selectedElement.getElement() instanceof UseCaseModel) {
				Image image = getToolkit().getImage("images" + File.separator + "use-case-tree.png");
				setIcon(new ImageIcon(image));
				setText("Use Case");
			}
		}else {
			Image image = getToolkit().getImage("images" + File.separator + "folder-elements.png");
			setIcon(new ImageIcon(image));
		} 
		if(hasFocus) {

			new TreeMenuController(row).setObj(obj);			
			this.tree = tree;
			setBackground(getBackgroundSelectionColor());
		}
		if (leaf) {
			if (selected) {
				setBackground(getBackgroundSelectionColor());
			} else {
			}
		} else {
			if (hasFocus) {
				//new TreeMenuController(tree,row).obj = obj;				
				setBackground(getBackgroundSelectionColor());
			}
			if (expanded) {
				if (obj instanceof ProjectModel) {
					Image image = getToolkit().getImage("images" + File.separator + "folder-open.png");
					setIcon(new ImageIcon(image));
				}				
			}
		}

		return this;
	}

	public static void setFocus(int row) {
		tree.setSelectionRow(row);
		
	}
	
}
