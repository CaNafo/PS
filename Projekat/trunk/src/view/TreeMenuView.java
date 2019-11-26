package view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import application.CustomJTabbedPane;
import application.Language;
import controller.TreeMenuController;

@SuppressWarnings("serial")
public class TreeMenuView extends JPopupMenu{
	private JMenuItem addNewProject;
	private JMenuItem addExistingProject;
	private JMenuItem addNewDiagram;
	private JMenuItem addExistingScheme;
	private JMenuItem rename;
	private JMenuItem delete;	
	private JMenuItem removeFromDisk;	
	private JMenuItem save;	
	private JMenuItem properties;	
	private JMenuItem showInSystemExplorer;	
	Language language = new Language();

	public TreeMenuView(CustomJTabbedPane tabbedPane, DiagramToolbar toolbar, StatusBarView statusBarView,MenuBarView barView) {		
		this.addNewProject = new JMenuItem();
		this.addExistingProject = new JMenuItem();
		this.addNewDiagram=new JMenuItem();
		this.addExistingScheme=new JMenuItem();
		this.rename = new JMenuItem();
		this.delete = new JMenuItem();
		this.removeFromDisk = new JMenuItem();
		this.save = new JMenuItem();
		this.properties = new JMenuItem();
		this.showInSystemExplorer = new JMenuItem();
		
		this.setUpMenu();
		new TreeMenuController(this, tabbedPane, toolbar, statusBarView, barView);
	}
	
	public void setUpMenu() {
		//ResourceBundle resourceBundle = ResourceBundle.getBundle("treeMenu");
		this.addNewProject.setText(language.getLanguage("Add new project"));
		this.addExistingProject.setText(language.getLanguage("Add existing project"));
		this.addNewDiagram.setText(language.getLanguage("Add new diagram"));
		this.addExistingScheme.setText(language.getLanguage("Add existing diagram"));
		this.properties.setText(language.getLanguage("Properties"));
		this.save.setText(language.getLanguage("Save"));
		this.rename.setText(language.getLanguage("Rename"));
		this.showInSystemExplorer.setText(language.getLanguage("Open file in system explorer"));
		this.delete.setText(language.getLanguage("Close"));
		this.removeFromDisk.setText(language.getLanguage("Delete from disk"));

		this.addNewProject.setActionCommand("Add new project");
		this.addExistingProject.setActionCommand("Add existing project");
		this.addNewDiagram.setActionCommand("Add new diagram");
		this.addExistingScheme.setActionCommand("Add existing diagram");
		this.properties.setActionCommand("Properties");
		this.delete.setActionCommand("Save");
		this.rename.setActionCommand("Rename");
		this.showInSystemExplorer.setActionCommand("Open file in system explorer");
		this.delete.setActionCommand("Delete");
		this.removeFromDisk.setActionCommand("Remove from disk");
		
		this.add(addNewProject);
		this.add(addNewDiagram);
		this.addSeparator();
		this.add(addExistingProject);
		this.add(addExistingScheme);
		this.addSeparator();
		this.add(properties);
		this.add(save);
		this.add(rename);
		this.add(showInSystemExplorer);
		this.addSeparator();
		this.add(delete);
		this.add(removeFromDisk);
	}

	public JMenuItem getAddNewProject() {
		return addNewProject;
	}

	public JMenuItem getAddExistingProject() {
		return addExistingProject;
	}

	public JMenuItem getaddNewDiagram() {
		return addNewDiagram;
	}

	public JMenuItem getAddExistingScheme() {
		return addExistingScheme;
	}

	public JMenuItem getRename() {
		return rename;
	}

	public JMenuItem getDelete() {
		return delete;
	}

	public JMenuItem getSave() {
		return save;
	}
	
	public JMenuItem getProperties() {
		return properties;
	}
	
	public JMenuItem getRemoveFromDisk() {
		return removeFromDisk;
	}
	
	public JMenuItem getShowInSystemExplorer() {
		return showInSystemExplorer;
	}
}
