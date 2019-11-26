package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import application.CreateDialog;
import application.CustomJTabbedPane;
import application.CustomTreeCellRenderer;
import application.RenameDialog;
import command.CommandInterface;
import command.DeleteElementCommand;
import command.DeleteRelationCommand;
import model.ActorModel;
import model.DiagramModel;
import model.DocumentTreeModel;
import model.ProjectModel;
import model.RelationModel;
import model.UseCaseModel;
import model.WorkspaceModel;
import view.ActorPropertiesView;
import view.DiagramToolbar;
import view.DiagramView;
import view.DocumentTreeView;
import view.MenuBarView;
import view.OpenView;
import view.ProjectOpenVIew;
import view.RelationPropertiesView;
import view.StatusBarView;
import view.TreeMenuView;
import view.UseCasePropertiesView;

public class TreeMenuController implements ActionListener {
	private TreeMenuView model;
	@SuppressWarnings("unused")
	private ResourceBundle resourceBundle;
	private static CustomJTabbedPane tabbedPane;
	private static DiagramToolbar toolbar;
	private static Object obj = null;
	private static StatusBarView statusBarView;
	private static MenuBarView barView;
	static int row;
	private DocumentTreeView view;

	public TreeMenuController(DocumentTreeView view) {
		view.addKeyListener(diagramKeyListener);
		view.addMouseListener(ml);

	}

	@SuppressWarnings("static-access")
	public TreeMenuController(int row) {
		this.row = row;
	}

	@SuppressWarnings("static-access")
	public TreeMenuController(TreeMenuView model, CustomJTabbedPane tabbedPane, DiagramToolbar toolbar,
			StatusBarView statusBarView, MenuBarView barView) {
		this.barView = barView;
		this.model = model;
		this.getResources();
		this.tabbedPane = tabbedPane;
		this.toolbar = toolbar;
		this.statusBarView = statusBarView;

		model.getAddNewProject().addActionListener(this);
		model.getAddExistingProject().addActionListener(this);
		model.getaddNewDiagram().addActionListener(this);
		model.getAddExistingScheme().addActionListener(this);
		model.getProperties().addActionListener(this);
		model.getRename().addActionListener(this);
		model.getDelete().addActionListener(this);
		model.getSave().addActionListener(this);
		model.getRemoveFromDisk().addActionListener(this);
		model.getShowInSystemExplorer().addActionListener(this);

	}

	public void getResources() {
		// this.resourceBundle = ResourceBundle.getBundle("popup");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Add new project":
			addNewProject();
			break;
		case "Add new diagram":
			addNewDiagram();
			break;
		case "Add existing project":
			addExistingProject();
			break;
		case "Add existing diagram":
			addExistingDiagram();
			break;
		case "Properties":
			setProperties();
			break;
		case "Open file in system explorer":
			openFileInSystemExplorer();
			break;
		case "Delete":
			delete();
			break;
		case "Remove from disk":
			removeFromDisc();
			break;
		case "Save":
			save();
			break;
		case "Rename":
			rename();
			break;
		default:
			break;
		}

	}

	private void setProperties() {
		if (obj instanceof UseCaseModel) {
			UseCasePropertiesView view = new UseCasePropertiesView((UseCaseModel) obj);
			new UseCasePropertiesController(view, (UseCaseModel) obj);
		} else if (obj instanceof ActorModel) {
			ActorPropertiesView view = new ActorPropertiesView((ActorModel) obj);
			new ActorPropertiesController(view, (ActorModel) obj);
		} else {
			RelationPropertiesView view = new RelationPropertiesView((RelationModel) obj);
			new RelationPropertiesController(view, (RelationModel) obj);
		}

	}

	private void rename() {
		new RenameDialog(new JFrame(), obj);
	}

	public TreeMenuView getModel() {
		return model;
	}

	@SuppressWarnings("rawtypes")
	private void delete() {
		if (obj instanceof WorkspaceModel) {

			WorkspaceModel ws = (WorkspaceModel) obj;
			ws.delete();
			ws.setDocumentName("New Workspace");
			tabbedPane.removeAll();
			hide();

		} else if (obj instanceof ProjectModel) {

			ProjectModel project = (ProjectModel) obj;

			for (Iterator iterator = project.getIteratorDijagrami(); iterator.hasNext();) {
				DiagramModel diagram = (DiagramModel) iterator.next();

				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if (diagram.getDocumentName() == tabbedPane.getTitleAt(i)) {
						tabbedPane.remove(i);
						i--;
					}
				}
			}
			project.delete();
			update(false, true);

		} else if (obj instanceof DiagramModel) {
			DiagramModel diagram = (DiagramModel) obj;
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if (diagram.getDocumentName() == tabbedPane.getTitleAt(i)) {
					tabbedPane.remove(i);
					i--;
				}
			}
			diagram.delete();
			update(false, true);

		} else if (obj instanceof ActorModel) {
			ActorModel actorModel = (ActorModel) obj;
			DiagramModel diagramModel = ((ActorModel) obj).getDiagramModel();

			DeleteElementCommand command = new DeleteElementCommand(diagramModel, actorModel);
			command.execute(new String[] {"from-tree"});
			diagramModel.getUndoStack().push(command);

			update(false, true);

		} else if (obj instanceof UseCaseModel) {
			UseCaseModel useCaseModel = (UseCaseModel) obj;
			
			DiagramModel diagramModel=((UseCaseModel)obj).getDiagramModel();
			DeleteElementCommand command = new DeleteElementCommand(diagramModel, useCaseModel);
			command.execute(new String[] {"from-tree"});
			diagramModel.getUndoStack().push(command);
			
			update(false, true);

		} else if (obj instanceof RelationModel) {
			RelationModel relationModel = (RelationModel) obj;
			DiagramModel model = relationModel.getDiagramModel();
			
			CommandInterface command = new DeleteRelationCommand(model,relationModel);		
			command.execute(null);
			model.getUndoStack().push(command);
			
			update(false, true);
		}
		obj = null;
	}

	private void save() {
		if (obj instanceof WorkspaceModel) {
			((WorkspaceModel) obj).save();

		} else if (obj instanceof ProjectModel) {
			((ProjectModel) obj).save();

		} else if (obj instanceof DiagramModel) {
			((DiagramModel) obj).save();

		}

	}

	private void addExistingDiagram() {
		if (obj instanceof ProjectModel) {

			new OpenView(obj, "diagram", tabbedPane, toolbar, statusBarView, barView).showOpenDialog();

		} else if (obj instanceof WorkspaceModel) {

			new OpenView(obj, "diagram", tabbedPane, toolbar, statusBarView, barView).showOpenDialog();

		}
		update(false, false);

	}

	private void addExistingProject() {

		new ProjectOpenVIew(obj, "addProjectToWorkspace", tabbedPane, toolbar, statusBarView, barView).showOpenDialog();

		update(false, false);
	}

	private void addNewDiagram() {
		new CreateDialog(new JFrame(), obj, "diagram", toolbar, tabbedPane, statusBarView,barView);

	}

	private void addNewProject() {

		new CreateDialog(new JFrame(), obj, "project", toolbar, tabbedPane, statusBarView,barView);
	}

	private void removeFromDisc() {
		if (obj instanceof DiagramModel) {
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if (((DiagramModel) obj).getDocumentName() == tabbedPane.getTitleAt(i)) {
					tabbedPane.remove(i);
					i--;
				}
			}

			((DiagramModel) obj).deleteFromDisc();
			update(false, true);

		} else if (obj instanceof ProjectModel) {
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = ((ProjectModel) obj).getIteratorDijagrami(); iterator.hasNext();) {
				DiagramModel diagram = (DiagramModel) iterator.next();

				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if (diagram.getDocumentName() == tabbedPane.getTitleAt(i)) {
						tabbedPane.remove(i);
						i--;
					}
				}
			}

			((ProjectModel) obj).deleteFromDisc();
			update(false, true);

		} else if (obj instanceof WorkspaceModel) {
			((WorkspaceModel) obj).deleteFromDisc();
			tabbedPane.removeAll();
			hide();

		}
	}

	private KeyListener diagramKeyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// Brisanje elementa
			if (e.getKeyCode() == KeyEvent.VK_DELETE) {
				delete();

			}

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	};

	@SuppressWarnings("static-access")
	private void update(boolean doubleClick, boolean delete) {
		view = DocumentTreeView.documentTreeView;

		if (delete)
			view.update();
		else {
			new DocumentTreeModel().clearTree(view);
			view.expandAllNodes(view, 0, view.getRowCount());
		}

		view.addKeyListener(diagramKeyListener);

		if (!doubleClick)
			new CustomTreeCellRenderer().setFocus(this.row);

	}

	@SuppressWarnings("static-access")
	private void hide() {
		view = DocumentTreeView.documentTreeView;
		new DocumentTreeModel().hideTree(view);
		view.addKeyListener(diagramKeyListener);

	}

	public void openFileInSystemExplorer() {
		String path = "";

		if (obj instanceof WorkspaceModel) {
			path = ((WorkspaceModel) obj).getDocumentPath();
			try {
				Runtime.getRuntime().exec("explorer.exe /open, " + path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (obj instanceof ProjectModel) {
			path = ((ProjectModel) obj).getDocumentPath();
			try {
				Runtime.getRuntime().exec("explorer.exe /open, " + path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (obj instanceof DiagramModel) {
			path = ((DiagramModel) obj).getDocumentPath();
			try {
				Runtime.getRuntime().exec("explorer.exe /select, " + path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void setObj(Object obj) {
		TreeMenuController.obj = obj;
	}

	MouseListener ml = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {

			if (e.getClickCount() == 1) {
			} else if (e.getClickCount() >= 2) {
				if (obj instanceof DiagramModel) {
					try {
						DiagramModel diagramModel = ((DiagramModel) obj);

						DiagramView view = new DiagramView(diagramModel, toolbar, statusBarView,barView);

						diagramModel.addObserver(view);

						tabbedPane.addTab(((DiagramModel) obj).getDocumentName(), view);

						for (int i = 0; i < tabbedPane.getTabCount(); i++) {
							if (tabbedPane.getTitleAt(i) == diagramModel.getDocumentName()) {
								tabbedPane.setSelectedIndex(i);
							}
						}
						obj = null;
						update(true, false);
					} catch (Exception e2) {
						// TODO: handle exception
					}

				} else if (obj instanceof ActorModel || obj instanceof UseCaseModel || obj instanceof RelationModel) {
					setProperties();
				}
			}
		}

	};
}
