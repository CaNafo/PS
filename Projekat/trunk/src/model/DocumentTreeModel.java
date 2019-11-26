package model;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import application.CustomJTabbedPane;
import view.DiagramToolbar;
import view.DocumentTreeView;
import view.MenuBarView;
import view.StatusBarView;
import view.TreeMenuView;

@SuppressWarnings("serial")
public class DocumentTreeModel extends DefaultTreeModel {

	public WorkspaceModel ws;
	private TreeMenuView contextMenu;
	public static DocumentTreeModel documentTreeModel;
	private static DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
	@SuppressWarnings("unused")
	private static MenuBarView barView;
	public DocumentTreeModel() {
		super(root);
		/*UIManager.put("Tree.expandedIcon",  
		        new ImageIcon("images/treeMinus.gif"));
		    UIManager.put("Tree.collapsedIcon", 
		        new ImageIcon("images/treePlus.gif"));*/
	}
	
	public DocumentTreeModel(WorkspaceModel ws, CustomJTabbedPane tabbedPane, DiagramToolbar toolbar,StatusBarView statusBarView, MenuBarView barView) {
		super(root);
		DocumentTreeModel.barView = barView;
		documentTreeModel = this;
		UIManager.put("Tree.expandedIcon",  
		        new ImageIcon("images/treeMinus.gif"));
		    UIManager.put("Tree.collapsedIcon", 
		        new ImageIcon("images/treePlus.gif"));
		this.contextMenu = new TreeMenuView(tabbedPane, toolbar,statusBarView, barView);
		this.ws = ws;
	}

	public WorkspaceModel getWorkSpace() {
		return ws;
	}

	public TreeMenuView getContextMenu() {
		return contextMenu;
	}
	
	public static void clearTree(JTree tree) {
	    if (tree.toString() == null) { return; }
	    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
	    DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
	    root.removeAllChildren();
		new DocumentTreeView();
	    model.reload();
	}
	public static void hideTree(JTree tree) {
	    if (tree.toString() == null) { return; }
	    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
	    DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
	    root.removeAllChildren();
	    model.reload();
	}
}
