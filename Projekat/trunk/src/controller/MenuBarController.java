package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import application.CreateDialog;
import application.CustomJTabbedPane;
import application.Language;
import application.MainWindow;
import model.DocumentTreeModel;
import model.WorkspaceModel;
import view.DiagramToolbar;
import view.DocumentTreeView;
import view.MainToolbarView;
import view.MenuBarView;
import view.OpenView;
import view.ProjectOpenVIew;
import view.StatusBarView;

public class MenuBarController {
	private static MenuBarView view;
	private WorkspaceModel model;
	private MainWindow mainWindow;
	private CustomJTabbedPane tabbedPane;
	private DiagramToolbar toolbar;
	private MainToolbarView viewToolbar;
	DocumentTreeModel documentTreeModel;
	private StatusBarView statusBarView;
	@SuppressWarnings("unused")
	private Language language = new Language();
	
	@SuppressWarnings("static-access")
	public MenuBarController(MenuBarView view, WorkspaceModel model, MainWindow mainWindow, CustomJTabbedPane tabbedPane, DiagramToolbar toolbar, StatusBarView statusBarView) {
				this.model = model;
				this.view = view;
				this.mainWindow = mainWindow;
				this.tabbedPane = tabbedPane;
				this.toolbar = toolbar;
				this.statusBarView = statusBarView;
				
				setFileActionListener();
			}
	
	public MenuBarController( MainToolbarView viewToolbar, WorkspaceModel model, MainWindow mainWindow, CustomJTabbedPane tabbedPane, DiagramToolbar toolbar, StatusBarView statusBarView) {
		this.model = model;
		this.viewToolbar = viewToolbar;
		this.mainWindow = mainWindow;
		this.tabbedPane = tabbedPane;
		this.toolbar = toolbar;
		this.statusBarView = statusBarView;
		
		setFileActionListenerToolbar();
	}
	
	private void setFileActionListener() {
		view.setActionListener(fileActionListener);
	}
	
	private void setFileActionListenerToolbar() {
		viewToolbar.setActionListener(fileActionListener);
	}
	ActionListener fileActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "addNewWorkspace":
					addNewWorkspace();					 
					break;
				case "addNewProject":
					addNewProject();					 
					break;
				case "addNewDiagram":
					addNewDiagram();				 
					break;
				case "openWorkspace":
					openWorkspace();
					break;
				case "openProject":
					openProject();
					break;
				case "openDiagram":
					openDiagram();
					break;
				case "saveAllDiagrams":
					model.save();
					break;
				case "changeLanguage":
					changeLanguage();
					break;
				case "exitApplication":
					mainWindow.dispose();
					break;	
				default:
					break;
				}
				
			}
		};
	
		@SuppressWarnings("static-access")
		private void changeLanguage() {
			Language language = new Language();
			
			switch (language.getLanguage()) {
			case "en_us":
				language.setLanguage("srb_rs");
				break;
			case "srb_rs":
				language.setLanguage("en_us");
				break;

			default:
				break;
			}
			
		    int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog (null, language.getLanguage("Would You like to perform automatic restart ?\n All unsaved work will be lost."),language.getLanguage("Warning"),dialogButton);
			if(dialogResult == JOptionPane.YES_OPTION){
			    	new MainWindow();
					mainWindow.dispose();
					DocumentTreeModel.clearTree(DocumentTreeView.documentTreeView);
					DocumentTreeView.documentTreeView.expandAllNodes(DocumentTreeView.documentTreeView, 0, 
																			DocumentTreeView.documentTreeView.getRowCount());
				}
		}
		
		private void addNewWorkspace() {			
			new CreateDialog(new JFrame(),model,"workspace",toolbar,tabbedPane, statusBarView,view);
		}
		
		private void addNewProject() {
			 new CreateDialog(new JFrame(),model,"project",toolbar,tabbedPane, statusBarView,view);
		}
		
		private void addNewDiagram() {
			 new CreateDialog(new JFrame(),model,"diagram",toolbar,tabbedPane, statusBarView,view);
		}
		
	    private void openWorkspace() {	    	
	    	//Metoda showOpenDioalog vraæa boolean u zavisnosti da li je potvrðeno ili odbaèeno otvaranje novog WS
	    	if(new OpenView(model, "workspace", tabbedPane, toolbar,statusBarView, view).showOpenDialog()){   
	    		updateTree();
	    	}
		}
		
		private void openProject() {
			new ProjectOpenVIew(model, "addProjectToWorkspace", tabbedPane, toolbar,statusBarView, view).showOpenDialog();
			updateTree();			
		}
		
		private void openDiagram() {
			new OpenView(model, "diagram", tabbedPane, toolbar,statusBarView, view).showOpenDialog();		
			updateTree();			
		}
		
		@SuppressWarnings("static-access")
		private void updateTree() {
			DocumentTreeView view  = DocumentTreeView.documentTreeView;
			new DocumentTreeModel().clearTree(view);
			view.expandAllNodes(view, 0, view.getRowCount());

		}
}
