/***********************************************************************
 * Module:  WorkspaceModel.java
 * Author:  Ca
 * Purpose: Defines the Class WorkspaceModel
 ***********************************************************************/

package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class WorkspaceModel extends DocumentAbstract {
	public  Collection<ProjectModel> projekti;
	public  Collection<DiagramModel> diagramModel;
	private static WorkspaceModel workspaceModel;
	
	private transient HashSet<ProjectModel> deletedProjekti = new HashSet<ProjectModel>();
	private transient HashSet<DiagramModel> deletediagramModel = new HashSet<DiagramModel>();
	
	public WorkspaceModel(String workspacePath) {
		// Postavljanje putanje i imena dokumenta
		setDocumentPath(workspacePath);
		Path path = Paths.get(workspacePath);
		String documentName = (path.getName(path.getNameCount() - 1)).toString();
		setDocumentName(documentName);
		WorkspaceModel.workspaceModel = this;

		//
	}

//	/** @param project */
//	public void addProject(ProjectModel project) {
//		// TODO: implement
//	}

	/** @param project */
	public void deleteProject(ProjectModel project) {
		// TODO: implement
	}

	/** @param diagram */
	public void addDiagram(DiagramModel diagram) {
		// TODO: implement
	}

	/** @param diagram */
	public void deleteDiagram(DiagramModel diagram) {
		// TODO: implement
	}

	/** @pdGenerated default getter */
	public java.util.Collection<ProjectModel> getProjekti() {
		if (projekti == null)
			projekti = new java.util.HashSet<ProjectModel>();
		return projekti;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator<ProjectModel> getIteratorProjekti() {
		if (projekti == null)
			projekti = new java.util.HashSet<ProjectModel>();
		return projekti.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newProjekti
	 */
	public void setProjekti(java.util.Collection<ProjectModel> newProjekti) {
		removeAllProjekti();
		for (Iterator<ProjectModel> iter = newProjekti.iterator(); iter.hasNext();)
			addProject((ProjectModel) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newProjectModel
	 */
	public void addProject(ProjectModel projectModel) {
		if (projectModel == null)
			return;
		if (this.projekti == null)
			this.projekti = new java.util.HashSet<ProjectModel>();
		if (!this.projekti.contains(projectModel))
			this.projekti.add(projectModel);
		
		//projectModel.setWs(workspaceModel);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldProjectModel
	 */
	public void removeProjekti(ProjectModel oldProjectModel) {
		if (oldProjectModel == null)
			return;
		if (this.projekti != null)
			if (this.projekti.contains(oldProjectModel))
				this.projekti.remove(oldProjectModel);
	}

	/** @pdGenerated default removeAll */
	public void removeAllProjekti() {
		if (projekti != null)
			projekti.clear();
	}

	/** @pdGenerated default getter */
	public java.util.Collection<DiagramModel> getDiagramModel() {
		if (diagramModel == null)
			diagramModel = new java.util.HashSet<DiagramModel>();
		return diagramModel;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator<DiagramModel> getIteratorDiagramModel() {
		if (diagramModel == null)
			diagramModel = new java.util.HashSet<DiagramModel>();
		return diagramModel.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newDiagramModel
	 */
	public void setDiagramModel(Collection<DiagramModel> newDiagramModel) {
		removeAllDiagramModel();
		for (Iterator<DiagramModel> iter = newDiagramModel.iterator(); iter.hasNext();)
			addDiagramModel((DiagramModel) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newDiagramModel
	 */
	public void addDiagramModel(DiagramModel newDiagramModel) {
		if (newDiagramModel == null)
			return;
		if (this.diagramModel == null)
			this.diagramModel = new java.util.HashSet<DiagramModel>();
		if (!this.diagramModel.contains(newDiagramModel))
			this.diagramModel.add(newDiagramModel);
		
		//newDiagramModel.setWorkspaceModel(workspaceModel);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldDiagramModel
	 */
	public void removeDiagramModel(DiagramModel oldDiagramModel) {
		if (oldDiagramModel == null)
			return;
		if (this.diagramModel != null)
			if (this.diagramModel.contains(oldDiagramModel))
				this.diagramModel.remove(oldDiagramModel);
	}

	/** @pdGenerated default removeAll */
	public void removeAllDiagramModel() {
		if (diagramModel != null)
			diagramModel.clear();
	}

	/**
	 * @param workspacePath -Putanja radnog prostora. Odnosi se na putanju foldera
	 *                      koji predstavlja Radni Prostor, ne na fajl koji ga
	 *                      opisuje
	 */
	@Override
	public void save() {
		String wsFilePath = getDocumentPath() + File.separator.toString() + super.getDocumentName() + ".ws";
		File wsFile = new File(wsFilePath);
		BufferedWriter bufferWritter;

		if (!wsFile.exists()) {
			
			try {
				Files.createDirectories(wsFile.toPath().getParent());
				Files.createFile(wsFile.toPath());
			} catch (IOException e) {
				
			}

			if (wsFile.exists() && wsFile.canWrite()) {
				try {
					bufferWritter = new BufferedWriter(new FileWriter(wsFile));				
					
					
					for (Iterator<DiagramModel> iterator = getIteratorDiagramModel(); iterator.hasNext();) {
						DiagramModel diagramModel = (DiagramModel) iterator.next();
						String path=diagramModel.getDocumentPath();
						if(!(diagramModel.getDocumentPath().contains(".json")))
							 path = diagramModel.getDocumentPath()+".json";
						else if((diagramModel.getDocumentPath().contains(".json")))
							 path = diagramModel.getDocumentPath();

						bufferWritter.write("Diagram:" + path + "\r\n");
						diagramModel.save();
					}

					for (Iterator<ProjectModel> iterator = getIteratorProjekti(); iterator.hasNext();) {
						ProjectModel projectModel = (ProjectModel) iterator.next();
						bufferWritter.write("Project:" + projectModel.getDocumentPath()+"\\"+projectModel.getDocumentName() + "\r\n");
						projectModel.save();
					}
					bufferWritter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}else {
			wsFile.delete();
			try {
				Files.createDirectories(wsFile.toPath().getParent());
				Files.createFile(wsFile.toPath());
				bufferWritter = new BufferedWriter(new FileWriter(wsFile));				
				
				
				for (Iterator<DiagramModel> iterator = getIteratorDiagramModel(); iterator.hasNext();) {
					DiagramModel diagramModel = (DiagramModel) iterator.next();
					String path=diagramModel.getDocumentPath();
					if(!(diagramModel.getDocumentPath().contains(".json")))
						 path = diagramModel.getDocumentPath()+".json";
					else if((diagramModel.getDocumentPath().contains(".json")))
						 path = diagramModel.getDocumentPath();

					bufferWritter.write("Diagram:" + path + "\r\n");
					diagramModel.save();
				}

				for (Iterator<ProjectModel> iterator = getIteratorProjekti(); iterator.hasNext();) {
					ProjectModel projectModel = (ProjectModel) iterator.next();
					bufferWritter.write("Project:" + projectModel.getDocumentPath()+"\\"+projectModel.getDocumentName() + "\r\n");
					projectModel.save();
				}
				bufferWritter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete() {
		for (Iterator<DiagramModel> iterator = this.getIteratorDiagramModel(); iterator.hasNext();) {
			DiagramModel diagramModel = (DiagramModel) iterator.next();
			deletediagramModel.add(diagramModel);
		}
		for (Iterator<ProjectModel> iterator = this.getIteratorProjekti(); iterator.hasNext();) {
			ProjectModel project = (ProjectModel) iterator.next();			
			deletedProjekti.add(project);
		}
		
		for (Iterator<DiagramModel> iterator = deletediagramModel.iterator(); iterator.hasNext();) {
			DiagramModel diagramModel = (DiagramModel) iterator.next();
			diagramModel.delete();			
		}
		
		for (Iterator<ProjectModel> iterator = deletedProjekti.iterator(); iterator.hasNext();) {
			ProjectModel project = (ProjectModel) iterator.next();	
			project.delete();			
		}
		
	}

	@Override
	public void deleteFromDisc() {
		for (Iterator<DiagramModel> iterator = this.getIteratorDiagramModel(); iterator.hasNext();) {
			DiagramModel diagramModel = (DiagramModel) iterator.next();
			deletediagramModel.add(diagramModel);
		}
		for (Iterator<ProjectModel> iterator = this.getIteratorProjekti(); iterator.hasNext();) {
			ProjectModel project = (ProjectModel) iterator.next();			
			deletedProjekti.add(project);
		}
		
		for (Iterator<DiagramModel> iterator = deletediagramModel.iterator(); iterator.hasNext();) {
			DiagramModel diagramModel = (DiagramModel) iterator.next();
			diagramModel.deleteFromDisc();			
		}
		
		for (Iterator<ProjectModel> iterator = deletedProjekti.iterator(); iterator.hasNext();) {
			ProjectModel project = (ProjectModel) iterator.next();	
			project.deleteFromDisc();			
		}
		
		File file = new File(getDocumentPath()+getDocumentName()+".ws");
		file.delete();
	}

	/**
	 * 
	 * @param workspacePath -Putanja radnog prostora. Odnosi se na putanju foldera
	 *                      koji predstavlja Radni Prostor, ne na fajl koji ga
	 *                      opisuje
	 * @return
	 */
	public static void open(String workspacePath) {
		Path wsPath = Paths.get(workspacePath);
		String wsFileName = wsPath.getName(wsPath.getNameCount() - 1).toString();
		String wsFilePath = workspacePath;
		File wsFile = new File(wsFilePath);
		BufferedReader bufferedReader;
		
		String wsNameRemove = wsFileName;
		workspacePath = workspacePath.replaceAll(wsNameRemove, "");
		
		workspaceModel.setDocumentPath(workspacePath);
		workspaceModel.setDocumentName(wsFileName.replaceAll(".ws", ""));
		
		
		//WorkspaceModel workspaceModel = new WorkspaceModel(workspacePath);

		if (wsFile.exists() && wsFile.canRead()) {
			try {
				bufferedReader = new BufferedReader(new FileReader(wsFile));

				while (bufferedReader.ready()) {
					String path = bufferedReader.readLine();
					if (path.contains("Project:")) {
						String projectPah = path.substring(8, path.length());
						ProjectModel project = new ProjectModel();					
						project.setWs(workspaceModel);
						project = ProjectModel.open(projectPah);
						workspaceModel.addProject(project);
						
					} else if (path.contains("Diagram:")) {
						String projectPath = path.substring(8, path.length());
						DiagramModel diagramModel = null;
						
						diagramModel = DiagramModel.open(projectPath);

						diagramModel.setWorkspaceModel(workspaceModel);
						workspaceModel.addDiagramModel(diagramModel);
					}
				}
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}