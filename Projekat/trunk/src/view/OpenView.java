package view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileFilter;

import controller.OpenController;

public class OpenView {

	private Object model;
	private String type;
	private JTabbedPane tabbedPane;
	private DiagramToolbar toolbar;
	private static StatusBarView statusBarView;
	private MenuBarView menuBarView;
	
	public OpenView(Object model, String type, JTabbedPane tabbedPane, DiagramToolbar toolbar,StatusBarView statusBarView, MenuBarView menuBarView) {
		this.model = model;
		this.menuBarView = menuBarView;
		this.type = type;
		this.tabbedPane=tabbedPane;		
		this.toolbar=toolbar;
		OpenView.statusBarView = statusBarView;
		
	}	
	
	
	public boolean showOpenDialog() {
		JFrame parentFrame = new JFrame();
		 FileFilter filter = new FileFilter() {	
				
				public String getDescription() {
					if(type != "workspace")
						return "Json Documents (*.json)";
					else
						return "Json Documents (*.ws)";
			    }
				
			    public boolean accept(File f) {
			        if (f.isDirectory()) {
			            return true;
			        } else {
			        	if(type != "workspace")
			        		return f.getName().toLowerCase().endsWith(".json");
			        	else
			        		return f.getName().toLowerCase().endsWith(".ws");
			        }
			    }
			};
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open");   
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileFilter(filter);
		
		int userSelection = fileChooser.showOpenDialog(parentFrame);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToOpen = fileChooser.getSelectedFile();
		    if(fileToOpen.getName().contains(".json") && type != "workspace"){		 

		    	new OpenController(model,fileToOpen.getAbsolutePath(),type,
				    	fileToOpen.getParentFile().getName(), tabbedPane, toolbar,statusBarView,menuBarView);
		    			    
		    }else if( fileToOpen.getName().contains(".ws") && type == "workspace") {
		    	new OpenController(model,fileToOpen.getAbsolutePath(),type,
		    			fileToOpen.getParentFile().getName(), tabbedPane, toolbar,statusBarView,menuBarView);
		    }   
		    else {
		    	if(type != "workspace") {
		    	JOptionPane.showMessageDialog(parentFrame,
		    		    "Please choose .json file!",
		    		    "Warning",
		    		    JOptionPane.WARNING_MESSAGE);
		    	showOpenDialog();
		    	}else {
		    		JOptionPane.showMessageDialog(parentFrame,
			    		    "Please choose .ws file!",
			    		    "Warning",
			    		    JOptionPane.WARNING_MESSAGE);
			    	showOpenDialog();
		    	}
		    }
		    
		    return true;
		}
		return false;
	}

}
