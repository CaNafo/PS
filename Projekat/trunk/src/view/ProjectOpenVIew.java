package view;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import controller.OpenController;
import model.WorkspaceModel;

public class ProjectOpenVIew {
	private Object model;
	private String type;
	private JTabbedPane tabbedPane;
	private DiagramToolbar toolbar;
	private StatusBarView statusBarView;
	private MenuBarView barView;
	
	public ProjectOpenVIew(Object model, String type, JTabbedPane tabbedPane, DiagramToolbar toolbar, StatusBarView statusBarView, MenuBarView barView) {
		this.model = model;
		this.barView = barView;
		this.type = type;
		this.tabbedPane=tabbedPane;		
		this.toolbar=toolbar;
		this.statusBarView = statusBarView;
		
	}	
	
	
	public void showOpenDialog() {
		JFrame parentFrame = new JFrame();
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save");   
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		int userSelection = fileChooser.showOpenDialog(parentFrame);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			
		    if(model instanceof WorkspaceModel) {
		    	
		    	new OpenController(model, fileChooser.getSelectedFile().toString(), type,fileChooser.getSelectedFile().getName(), tabbedPane, toolbar,statusBarView, barView);
		    	      
		    	      		    	     		    	
		    }
		    
		}
	}
}
