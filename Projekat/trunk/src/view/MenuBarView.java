package view;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.sun.glass.events.KeyEvent;

import application.Language;

@SuppressWarnings("serial")
public class MenuBarView extends JMenuBar {
	private JMenuItem itemNewWorkspace;
	private JMenuItem itemNewProject;
	private JMenuItem itemCreateDiagram;
	private JMenuItem itemOpenWorkspace;
	private JMenuItem itemOpenProject;
	private JMenuItem itemOpenDiagram;
	private JMenuItem itemSaveAll;
	private JMenuItem itemExit;
	private JMenuItem itemZoomIn;
	private JMenuItem itemZoomOut;
	private JMenuItem itemUndo;
	private JMenuItem itemRedo;
	private Language language = new Language();
	
	public MenuBarView() {

		//FILE
		JMenu menuFile= new JMenu(language.getLanguage("File"));
		this.add(menuFile);
		
		//NEW WORKSPACE
		itemNewWorkspace = new JMenuItem(language.getLanguage("New Workspace"), new ImageIcon("images/new-project.png"));
		itemNewWorkspace.setActionCommand("addNewWorkspace");
		menuFile.add(itemNewWorkspace);
		itemNewWorkspace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,  Event.CTRL_MASK | Event.SHIFT_MASK));
		//NEW PROJECT
		itemNewProject = new JMenuItem(language.getLanguage("New Project"), new ImageIcon("images/new-folder.png"));
		itemNewProject.setActionCommand("addNewProject");
		menuFile.add(itemNewProject);
		itemNewProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,  Event.SHIFT_MASK));
		//NEW DIAGRAM
		itemCreateDiagram = new JMenuItem(language.getLanguage("Create Diagram"), new ImageIcon("images/new-file.png"));
		itemCreateDiagram.setActionCommand("addNewDiagram");
		menuFile.add(itemCreateDiagram);
		itemCreateDiagram.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		// OPEN WORKSPACE
		itemOpenWorkspace = new JMenuItem(language.getLanguage("Open Workspace"),new ImageIcon("images/tree-workspace.png"));
		itemOpenWorkspace .setActionCommand("openWorkspace");
		menuFile.add(itemOpenWorkspace);
		itemOpenWorkspace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK | Event.SHIFT_MASK));
		// OPEN PROJECT
		itemOpenProject = new JMenuItem(language.getLanguage("Open Project"),new ImageIcon("images/folder-open.png"));
		itemOpenProject.setActionCommand("openProject");
		menuFile.add(itemOpenProject);
		itemOpenProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,  Event.SHIFT_MASK));
		//OPEN DIAGRAM
		itemOpenDiagram = new JMenuItem(language.getLanguage("Open Diagram"), new ImageIcon("images/file-icon.png"));
		menuFile.add(itemOpenDiagram);
		itemOpenDiagram .setActionCommand("openDiagram");
		itemOpenDiagram.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		menuFile.addSeparator();
		
		//SAVE ALL
		itemSaveAll = new JMenuItem(language.getLanguage("Save All"),new ImageIcon("images/disks.png"));
		itemSaveAll .setActionCommand("saveAllDiagrams");
		menuFile.add(itemSaveAll);
		itemSaveAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK | Event.SHIFT_MASK));
		menuFile.addSeparator();
		// EXIT
		itemExit = new JMenuItem(language.getLanguage("Exit"), new ImageIcon("images/cross.png"));
		itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
		itemExit.setActionCommand("exitApplication");
		menuFile.add(itemExit);
		
		
		//EDIT
		JMenu menuEdit= new JMenu(language.getLanguage("Edit"));
		this.add(menuEdit);
		
		//UNDO
		itemUndo = new JMenuItem(language.getLanguage("Undo"),  new ImageIcon("images/arrow-curve-180-left.png"));
		itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
		itemUndo .setActionCommand("undoAction");
		menuEdit.add(itemUndo);
		//REDO
		itemRedo = new JMenuItem(language.getLanguage("Redo"), new ImageIcon("images/arrow-curve.png"));
		itemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK));
		itemRedo.setActionCommand("redoAction");
		menuEdit.add(itemRedo);
		menuEdit.addSeparator();		
		
		//VIEW
		JMenu menuView= new JMenu(language.getLanguage("View"));
		this.add(menuView);
		
		//ZOOM IN
		itemZoomIn = new JMenuItem(language.getLanguage("Zoom in") , new ImageIcon("images/magnifier-zoom-in.png"));
		itemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, Event.CTRL_MASK));
		itemZoomIn.setActionCommand("zoomIn");
		menuView.add(itemZoomIn);
		//ZOOM OUT
		itemZoomOut = new JMenuItem(language.getLanguage("Zoom Out"),new ImageIcon("images/magnifier-zoom-out.png"));
		itemZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Event.CTRL_MASK));
		itemZoomOut.setActionCommand("zoomOut");
		menuView.add(itemZoomOut);

		//HELP
		JMenu menuHelp= new JMenu(language.getLanguage("Help"));
		this.add(menuHelp);
		
		//ABOUT
		JMenuItem itemAboutUs = new JMenuItem(language.getLanguage("About Us..."), new ImageIcon("images/question-frame.png"));
		itemAboutUs.setActionCommand("aboutUs");
		menuHelp.add(itemAboutUs);
		
		itemAboutUs.addActionListener(
				new ActionListener() {
	            	public void actionPerformed(ActionEvent e)
	                {
	                	AboutUsView AboutUs = new AboutUsView();
	                	AboutUs.showAboutUsDialog();
	                	}
	            	}
				);
		//setActionCommand
		

	}
	
	public void setActionListener(ActionListener actionListener) {
		itemNewWorkspace.addActionListener(actionListener);
		itemNewProject.addActionListener(actionListener);
		itemCreateDiagram.addActionListener(actionListener);
		itemOpenWorkspace.addActionListener(actionListener);
		itemOpenProject.addActionListener(actionListener);
		itemOpenDiagram.addActionListener(actionListener);
		itemSaveAll.addActionListener(actionListener);
		itemExit.addActionListener(actionListener);
	
	}
	
	public void setEditActionListner(ActionListener actionListener) {
		itemZoomIn.addActionListener(actionListener);
		itemZoomOut.addActionListener(actionListener);
	}
	
	public JMenuItem getItemRedo() {
		return itemRedo;
	}
	public JMenuItem getItemUndo() {
		return itemUndo;
	}
}
