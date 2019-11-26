package controller;

import javax.swing.JTabbedPane;

import model.DiagramModel;
import model.ProjectModel;
import model.WorkspaceModel;
import view.DiagramToolbar;
import view.DiagramView;
import view.MenuBarView;
import view.StatusBarView;

public class OpenController {
	
	
	@SuppressWarnings("static-access")
	public OpenController(Object model, String path, String type, String name, JTabbedPane tabbedPane,
			DiagramToolbar toolbar, StatusBarView statusBarView, MenuBarView barView) {
		
		if (type == "diagram") {
			if (model instanceof WorkspaceModel) {
				
				DiagramModel diagramModel = DiagramModel.open(path);
				diagramModel.setDocumentPath(path);
				DiagramView diagramView = new DiagramView(diagramModel,toolbar,statusBarView, barView);
				diagramModel.addObserver(diagramView);
				//new DiagramEditController(diagramModel, diagramView, toolbar);
				tabbedPane.addTab(diagramModel.getDocumentName(), diagramView);
				
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getTitleAt(i) == ((DiagramModel) diagramModel).getDocumentName()){
						tabbedPane.setSelectedIndex(i);
						break;
					}
				}
				((WorkspaceModel) model).addDiagramModel(diagramModel);

			} else if (model instanceof ProjectModel) {
				DiagramModel diagramModel = DiagramModel.open(path);
				diagramModel.setDocumentPath(((ProjectModel) model).getDocumentPath()+"\\"+((ProjectModel) model).getDocumentName()+"\\"+diagramModel.getDocumentName());
				DiagramView diagramView = new DiagramView(diagramModel,toolbar,statusBarView, barView);
				//new DiagramEditController(diagramModel, diagramView, toolbar);
				diagramModel.addObserver(diagramView);
				
				((ProjectModel) model).addDiagram(diagramModel);
				tabbedPane.addTab(diagramModel.getDocumentName(), diagramView);
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getTitleAt(i) == ((DiagramModel) diagramModel).getDocumentName()){
						tabbedPane.setSelectedIndex(i);
						break;
					}
				}
			}
		}else if (type == "workspace"){
			((WorkspaceModel) model).delete();
			tabbedPane.removeAll();
			((WorkspaceModel)model).open(path);			
			
			
		}else if (type == "addProjectToWorkspace"){
			ProjectModel project = ProjectModel.open(path);
			((WorkspaceModel)model).addProject(project);
			
		}else {
			ProjectModel project = new ProjectModel(path);
			project.setDocumentName(name);
			project.addDiagram(new DiagramModel().open(path));
			project.setDocumentPath(path);
		}
		
	}

}
