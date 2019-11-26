package application;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;

import controller.MenuBarController;
import model.DocumentTreeModel;
import model.WorkspaceModel;
import view.DiagramToolbar;
import view.DocumentTreeView;
import view.MainToolbarView;
import view.MenuBarView;
import view.StatusBarView;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private JTree documentTreeView;
	private CustomJTabbedPane tabbedPane;
	private DiagramToolbar diagramToolbar;
	private DocumentTreeModel documentTreeModel;
	private MenuBarView menuBarView;
	private MainToolbarView mainToolbar;
	private StatusBarView statusBarView;

	public MainWindow() {
		
		tabbedPane = new CustomJTabbedPane();
		diagramToolbar = new DiagramToolbar(tabbedPane);
		mainToolbar = new MainToolbarView();
		menuBarView=new MenuBarView();
		statusBarView=new StatusBarView();

		setLayout(new BorderLayout());
		
		loadTestData();

		JScrollPane treeScroll = new JScrollPane();
		treeScroll.setViewportView(documentTreeView);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(300);
		splitPane.setLeftComponent(treeScroll);
		splitPane.setRightComponent(tabbedPane);

		
		add(splitPane, BorderLayout.CENTER);
		add(mainToolbar, BorderLayout.NORTH);
		add(diagramToolbar, BorderLayout.EAST);
		add(statusBarView, BorderLayout.SOUTH);
		
		setJMenuBar(menuBarView);
		
		Image appIcon=Toolkit.getDefaultToolkit().getImage("images/app-icon.png");
		setIconImage(appIcon);
		setTitle("UseCase Editor");
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		setLocationRelativeTo(null);
	}

	@SuppressWarnings("static-access")
	private void loadTestData() {
		
		WorkspaceModel workspaceModel = new WorkspaceModel("files\\Workspace.ws");
		workspaceModel.open("files\\Workspace.ws");
		
		documentTreeModel = new DocumentTreeModel(workspaceModel, tabbedPane, diagramToolbar,statusBarView,menuBarView);
		documentTreeView = new DocumentTreeView(documentTreeModel);

		new MenuBarController(menuBarView, workspaceModel, this, tabbedPane, diagramToolbar,statusBarView);
		
		new MenuBarController(mainToolbar, workspaceModel, this, tabbedPane, diagramToolbar, statusBarView);
	}

}
