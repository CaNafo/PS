package view;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import application.Language;

@SuppressWarnings("serial")
public class MainToolbarView  extends JToolBar{
	
	
	private JButton btnNewWorkspace;
	private JButton btnNewProject;
	private JButton btnCreateDiagram;
	private JButton btnOpenWorkspace;
	private JButton btnOpenProject;
	private JButton btnOpenDiagram;
	private JButton btnSaveAll;
	private JButton btnLanguage;
	
	private Language language = new Language();

	public MainToolbarView() {
		setOrientation(JToolBar.HORIZONTAL);
		setFloatable(false);
		addButtons(20);
	}

	private void addButtons(int iconSize) {
		Toolkit tk = Toolkit.getDefaultToolkit();
	
		Image itemNewWorkspaceImage = tk.getImage("images/new-project.png");
		itemNewWorkspaceImage = itemNewWorkspaceImage.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnNewWorkspace = new JButton(new ImageIcon(itemNewWorkspaceImage));
		btnNewWorkspace.setActionCommand("addNewWorkspace");
		btnNewWorkspace.setToolTipText(language.getLanguage("New Workspace"));

		Image newProjectIcon = tk.getImage("images/new-folder.png");
		newProjectIcon = newProjectIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnNewProject = new JButton(new ImageIcon(newProjectIcon));
		btnNewProject.setActionCommand("addNewProject");
		btnNewProject.setToolTipText(language.getLanguage("New Project"));

		Image createDiagraIcon = tk.getImage("images/new-file.png");
		createDiagraIcon = createDiagraIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnCreateDiagram = new JButton(new ImageIcon(createDiagraIcon));
		btnCreateDiagram.setActionCommand("addNewDiagram");
		btnCreateDiagram.setToolTipText(language.getLanguage("Create Diagram"));

		Image openWorkspaceIcon = tk.getImage("images/tree-workspace.png");
		openWorkspaceIcon = openWorkspaceIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnOpenWorkspace = new JButton(new ImageIcon(openWorkspaceIcon));
		btnOpenWorkspace.setActionCommand("openWorkspace");
		btnOpenWorkspace.setToolTipText(language.getLanguage("Open Workspace"));

		Image openProjectIcon = tk.getImage("images/folder-open.png");
		openProjectIcon = openProjectIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnOpenProject = new JButton(new ImageIcon(openProjectIcon));
		btnOpenProject.setActionCommand("openProject");
		btnOpenProject.setToolTipText(language.getLanguage("Open Project"));

		Image openDiagramIcon = tk.getImage("images/file-icon.png");
		openDiagramIcon = openDiagramIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnOpenDiagram = new JButton(new ImageIcon(openDiagramIcon));
		btnOpenDiagram.setActionCommand("openDiagram");
		btnOpenDiagram.setToolTipText(language.getLanguage("Open Diagram"));

		Image saveAllIcon = tk.getImage("images/disks.png");
		saveAllIcon = saveAllIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnSaveAll = new JButton(new ImageIcon(saveAllIcon));
		btnSaveAll.setActionCommand("saveAllDiagrams");
		btnSaveAll.setToolTipText(language.getLanguage("Save All"));
		
		Image language = this.language.getLanguageImage();
		language = language.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnLanguage = new JButton(new ImageIcon(language));
		btnLanguage.setActionCommand("changeLanguage");
		btnLanguage.setToolTipText(this.language.getLanguage("Change Language"));
		
		Border border = this.getBorder();
		Border margin = new EmptyBorder(0,0,0,43);
		
		add(btnNewWorkspace);
		add(btnNewProject);
		add(btnCreateDiagram);
		addSeparator();
		add(btnOpenWorkspace);
		add(btnOpenProject);
		add(btnOpenDiagram);
		addSeparator();
		add(btnSaveAll);
		addSeparator();
		add(Box.createHorizontalGlue());
		this.setBorder(new CompoundBorder(border, margin));
		add(btnLanguage);
	}

	public void setActionListener(ActionListener actionListener) {
		btnNewWorkspace.addActionListener(actionListener);
		btnNewProject.addActionListener(actionListener);
		btnCreateDiagram.addActionListener(actionListener);
		btnOpenWorkspace.addActionListener(actionListener);
		btnOpenProject.addActionListener(actionListener);
		btnOpenDiagram.addActionListener(actionListener);
		btnSaveAll.addActionListener(actionListener);
		btnLanguage.addActionListener(actionListener);
	}
	
}
