/***********************************************************************
 * Module:  ProjectModel.java
 * Author:  Ca
 * Purpose: Defines the Class ProjectModel
 ***********************************************************************/

package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class ProjectModel extends DocumentAbstract {

	public Collection<DiagramModel> dijagrami;
	private static WorkspaceModel ws;
	private transient HashSet<DiagramModel> deletediagramModel = new HashSet<DiagramModel>();
	
	public void setWs(WorkspaceModel ws) {
		ProjectModel.ws = ws;
	}
	
	public ProjectModel() {
		setDocumentName("New Project");
	}
	
	public ProjectModel(String documentPath) {
		setDocumentPath(documentPath);
	}

	/** @pdGenerated default getter */
	public Collection<DiagramModel> getDijagrami() {
		if (dijagrami == null)
			dijagrami = new HashSet<DiagramModel>();
		return dijagrami;
	}

	/** @pdGenerated default iterator getter */
	public Iterator<DiagramModel> getIteratorDijagrami() {
		if (dijagrami == null)
			dijagrami = new HashSet<DiagramModel>();
		return dijagrami.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newDijagrami
	 */
	public void setDijagrami(Collection<DiagramModel> newDijagrami) {
		removeAllDijagrami();
		for (Iterator<DiagramModel> iter = newDijagrami.iterator(); iter.hasNext();)
			addDiagram((DiagramModel) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newDiagramModel
	 */
	public void addDiagram(DiagramModel newDiagramModel) {
		if (newDiagramModel == null)
			return;
		if (this.dijagrami == null)
			this.dijagrami = new HashSet<DiagramModel>();
		if (!this.dijagrami.contains(newDiagramModel))
			this.dijagrami.add(newDiagramModel);
		
		newDiagramModel.setProjectModel(this);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldDiagramModel
	 */
	public void removeDijagrami(DiagramModel oldDiagramModel) {
		if (oldDiagramModel == null)
			return;
		if (this.dijagrami != null)
			if (this.dijagrami.contains(oldDiagramModel))
				this.dijagrami.remove(oldDiagramModel);
	}

	/** @pdGenerated default removeAll */
	public void removeAllDijagrami() {
		if (dijagrami != null)
			dijagrami.clear();
	}

	@Override
	public void save() {
		
		File file = new File(getDocumentPath()+"\\"+getDocumentName());

		if (getDocumentPath().length() > 0) {
			if (!file.exists()) {
				try {
					Files.createDirectories(file.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (file.exists() && file.canWrite()) {
				for (Iterator<DiagramModel> iterator = getIteratorDijagrami(); iterator.hasNext();) {
					DiagramModel diagramModel = (DiagramModel) iterator.next();
					diagramModel.save();
				}

			}
		}
	}

	@Override
	public void delete() {
		for (Iterator<DiagramModel> iterator = dijagrami.iterator(); iterator.hasNext();) {
			DiagramModel diagram = (DiagramModel) iterator.next();
			deletediagramModel.add(diagram);	
		}
		for (Iterator<DiagramModel> iterator = deletediagramModel.iterator(); iterator.hasNext();) {
			DiagramModel diagramModel = (DiagramModel) iterator.next();
			diagramModel.delete();			
		}
		if(ws != null)
			ws.removeProjekti(this);
	}

	@Override
	public void deleteFromDisc() {
		for (Iterator<DiagramModel> iterator = dijagrami.iterator(); iterator.hasNext();) {
			DiagramModel diagram = (DiagramModel) iterator.next();
			deletediagramModel.add(diagram);	
		}
		for (Iterator<DiagramModel> iterator = deletediagramModel.iterator(); iterator.hasNext();) {
			DiagramModel diagramModel = (DiagramModel) iterator.next();
			diagramModel.deleteFromDisc();			
		}
		if(ws != null)
			ws.removeProjekti(this);
		
		File file = new File(getDocumentPath()+"\\"+getDocumentName());
		file.delete();
		ws.save();
	}

	/**
	 * 
	 * @param projectPath
	 * @return
	 */
	public static ProjectModel open(String projectPath) {
		File folder = new File(projectPath);
		File[] filesList = folder.listFiles();
		ProjectModel project = new ProjectModel(projectPath.replace("\\"+folder.getName(), ""));
		project.setDocumentName(folder.getName());
		
		if (filesList!=null) {
			for (int i = 0; i < filesList.length; i++) {
				File file = filesList[i];
				if (file.isFile() && file.canRead()) {
					DiagramModel diagramModel = DiagramModel.open(file.getPath());
					
					diagramModel.setWorkspaceModel(project.getWs());
					
					project.addDiagram(diagramModel);	
					
				}
			}
		}
		return project;
	}
	 public WorkspaceModel getWs() {
		return ws;
	}
}