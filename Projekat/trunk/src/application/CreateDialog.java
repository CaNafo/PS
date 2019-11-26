package application;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import model.DiagramModel;
import model.DocumentTreeModel;
import model.ProjectModel;
import model.WorkspaceModel;
import view.DiagramToolbar;
import view.DiagramView;
import view.DocumentTreeView;
import view.MenuBarView;
import view.StatusBarView;

public class CreateDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private Object obj;
	private JTextField fileName = new JTextField(10);
	private JFrame parent;
	private String path, type;
	private JButton button;
	private DiagramToolbar diagramToolbar;
	private JTabbedPane tabbedPane;
	private StatusBarView statusBarView;
	private MenuBarView barView;
	
	public CreateDialog(JFrame parent, Object obj, String type, DiagramToolbar diagramToolbar, JTabbedPane tabbedPane, StatusBarView statusBarView, MenuBarView barView) {
		super(parent, "Create");
		this.barView = barView;
		this.tabbedPane = tabbedPane;
		this.obj = obj;		
		this.parent = parent;
		this.type = type;
		this.diagramToolbar = diagramToolbar;
		this.statusBarView = statusBarView;
		parent.setAlwaysOnTop(true); 

		Point p = new Point(400, 400);
		setLocation(p.x, p.y);

		JPanel messagePane = new JPanel();

		fileName.setText("Set name");
		messagePane.add(fileName);
		fileName.select(0, fileName.getText().length());
		getContentPane().add(messagePane);

		JPanel buttonPane = new JPanel();
		button = new JButton("Save");
		buttonPane.add(button);
		
		if(!(obj instanceof ProjectModel)) {
		JButton buttonSelect = new JButton("Select path");
		buttonPane.add(buttonSelect);
		buttonSelect.addActionListener(new SelectPathActionListener());
		buttonSelect.addKeyListener(keyListener);
		button.setEnabled(false);
		}
		
		fileName.addKeyListener(keyListener);
		
		
		button.addActionListener(new SaveActionListener());
		getContentPane().add(buttonPane, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}

	KeyListener keyListener = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER && button.isEnabled()) {
				createNew();
			}
		
		}
	};

	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action action = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", action);
		return rootPane;
	}

	class SaveActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			createNew();
		}
	}
	
	class SelectPathActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showSaveDialog(parent);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
			    @SuppressWarnings("unused")
				File yourFolder = fc.getSelectedFile();
			    path = fc.getSelectedFile().toString();
			    button.setEnabled(true);			    
			}			
		}
	}
	
	@SuppressWarnings("static-access")
	private void createNew() {
		if(obj instanceof WorkspaceModel) {
			String objPath = path+File.separator+fileName.getText();
			boolean fileExistOnLocation = false;
			
			if(type == "project") {
				for (@SuppressWarnings("rawtypes")
				Iterator iterator = ((WorkspaceModel) obj).getIteratorProjekti(); iterator.hasNext();) {
					ProjectModel project = (ProjectModel) iterator.next();
					String projectPath = project.getDocumentPath()+File.separator+project.getDocumentName();
					
					if (projectPath.equals(objPath)) {
						fileExistOnLocation = true;
					}
				}
				
				if(!fileExistOnLocation) {
					ProjectModel projectModel = new ProjectModel(path);
					projectModel.setDocumentName(fileName.getText());
					projectModel.save();
					((WorkspaceModel) obj).addProject(projectModel);
					((WorkspaceModel) obj).save();
				}else {
					JOptionPane.showMessageDialog(null,
			    		    "Project with same name exist on location: \nPlease choose different name or path!",
			    		    "Warning!",
			    		    JOptionPane.WARNING_MESSAGE);
				}
				
			}else if(type == "diagram"){
				
				for (@SuppressWarnings("rawtypes")
				Iterator iterator = ((WorkspaceModel) obj).getIteratorDiagramModel(); iterator.hasNext();) {
					DiagramModel diagramModel = (DiagramModel) iterator.next();
					String diagramPath = diagramModel.getDocumentPath();
					
					if (diagramPath.equals(objPath+".json")) {
						fileExistOnLocation = true;
					}
				}
				
				if(!fileExistOnLocation) {
					DiagramModel diagramModel = new DiagramModel(fileName.getText());
					diagramModel.setDocumentPath(path+"\\"+fileName.getText());
					DiagramView view = new DiagramView(diagramModel,diagramToolbar,statusBarView, barView);
					diagramModel.addObserver(view);		
					diagramModel.setWorkspaceModel((WorkspaceModel) obj);
					diagramModel.save();
					((WorkspaceModel) obj).addDiagramModel(diagramModel);
					((WorkspaceModel) obj).save();
					tabbedPane.addTab(diagramModel.getDocumentName(), view);
	        		
	        		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
						if(tabbedPane.getTitleAt(i) == diagramModel.getDocumentName()){
							tabbedPane.setSelectedIndex(i);
							}
	        		}
        		}else {
        			JOptionPane.showMessageDialog(null,
			    		    "Diagram with same name exist on location: \nPlease choose different name or path!",
			    		    "Warning!",
			    		    JOptionPane.WARNING_MESSAGE);
        		}
			}else {
				((WorkspaceModel) obj).setDocumentName(fileName.getText());
				((WorkspaceModel) obj).setDocumentPath(path);
				((WorkspaceModel) obj).delete();
				tabbedPane.removeAll();
				((WorkspaceModel) obj).save();
			}
		}else if(obj instanceof ProjectModel){
			boolean fileExistOnLocation = false;
			
			for (@SuppressWarnings("rawtypes")
			Iterator iterator =((ProjectModel) obj).getIteratorDijagrami(); iterator.hasNext();) {
				DiagramModel diagramModel = (DiagramModel) iterator.next();
				
				if(diagramModel.getDocumentName().equals(fileName.getText())) {
					fileExistOnLocation = true;
				}
			}			
			
			if(!fileExistOnLocation) {
				DiagramModel diagramModel = new DiagramModel(fileName.getText());
				diagramModel.setDocumentPath(((ProjectModel) obj).getDocumentPath()+"\\"+((ProjectModel) obj).getDocumentName()+"\\"+ fileName.getText());				
				
				DiagramView view = new DiagramView(diagramModel,diagramToolbar,statusBarView,barView);
				diagramModel.addObserver(view);
				((ProjectModel) obj).addDiagram(diagramModel);
				diagramModel.save();
				tabbedPane.addTab(diagramModel.getDocumentName(), view);
	    		
	    		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getTitleAt(i) == diagramModel.getDocumentName()){
						tabbedPane.setSelectedIndex(i);
						}
				}
			}else {
				JOptionPane.showMessageDialog(null,
		    		    "Diagram with same name exist in project: \nPlease choose different name or project!",
		    		    "Warning!",
		    		    JOptionPane.WARNING_MESSAGE);
			}
		}
		
		DocumentTreeView view = DocumentTreeView.documentTreeView;
		new DocumentTreeModel().clearTree(view);		
		view.expandAllNodes(view, 0, view.getRowCount());
		
		setVisible(false);
		dispose();
	}
}