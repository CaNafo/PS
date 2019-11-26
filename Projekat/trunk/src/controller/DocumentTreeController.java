package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import model.ActorModel;
import model.DiagramModel;
import model.DocumentTreeModel;
import model.ProjectModel;
import model.RelationModel;
import model.UseCaseModel;
import model.WorkspaceModel;
import view.DocumentTreeView;


public class DocumentTreeController implements ActionListener, MouseListener {
	private DocumentTreeModel model;
	private DocumentTreeView view;
	
	

	public DocumentTreeController(DocumentTreeModel model, DocumentTreeView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			int row = view.getClosestRowForLocation(e.getX(), e.getY());
			view.setSelectionRow(row);

			try {
				Object object = ((DefaultMutableTreeNode) view.getSelectionPath().getLastPathComponent()).getUserObject();

				if (object instanceof WorkspaceModel) {
					model.getContextMenu().getAddNewProject().setVisible(true);
					model.getContextMenu().getAddExistingProject().setVisible(true);
					model.getContextMenu().getaddNewDiagram().setVisible(true);
					model.getContextMenu().getAddExistingScheme().setVisible(true);
					model.getContextMenu().getDelete().setVisible(true);
					model.getContextMenu().getRemoveFromDisk().setVisible(true);
					model.getContextMenu().getRename().setVisible(true);
					model.getContextMenu().getShowInSystemExplorer().setVisible(true);
					model.getContextMenu().getSave().setVisible(true);
					model.getContextMenu().getProperties().setVisible(false);

				}
				else if (object instanceof ProjectModel) {
					model.getContextMenu().getAddNewProject().setVisible(false);
					model.getContextMenu().getAddExistingProject().setVisible(false);
					model.getContextMenu().getaddNewDiagram().setVisible(true);
					model.getContextMenu().getAddExistingScheme().setVisible(true);
					model.getContextMenu().getDelete().setVisible(true);
					model.getContextMenu().getRemoveFromDisk().setVisible(true);
					model.getContextMenu().getRename().setVisible(true);
					model.getContextMenu().getShowInSystemExplorer().setVisible(true);
					model.getContextMenu().getSave().setVisible(true);
					model.getContextMenu().getProperties().setVisible(false);

				}
				else if(object instanceof DiagramModel) {
					model.getContextMenu().getAddNewProject().setVisible(false);
					model.getContextMenu().getAddExistingProject().setVisible(false);
					model.getContextMenu().getaddNewDiagram().setVisible(false);
					model.getContextMenu().getAddExistingScheme().setVisible(false);
					model.getContextMenu().getDelete().setVisible(true);
					model.getContextMenu().getRemoveFromDisk().setVisible(true);
					model.getContextMenu().getRename().setVisible(true);
					model.getContextMenu().getShowInSystemExplorer().setVisible(true);
					model.getContextMenu().getSave().setVisible(true);
					model.getContextMenu().getProperties().setVisible(false);

					
				}else if(object instanceof UseCaseModel) {
					model.getContextMenu().getAddNewProject().setVisible(false);
					model.getContextMenu().getAddExistingProject().setVisible(false);
					model.getContextMenu().getaddNewDiagram().setVisible(false);
					model.getContextMenu().getAddExistingScheme().setVisible(false);
					model.getContextMenu().getDelete().setVisible(true);
					model.getContextMenu().getRemoveFromDisk().setVisible(false);
					model.getContextMenu().getRename().setVisible(true);
					model.getContextMenu().getShowInSystemExplorer().setVisible(false);
					model.getContextMenu().getSave().setVisible(false);
					model.getContextMenu().getProperties().setVisible(true);

				}else if(object instanceof ActorModel) {
					model.getContextMenu().getAddNewProject().setVisible(false);
					model.getContextMenu().getAddExistingProject().setVisible(false);
					model.getContextMenu().getaddNewDiagram().setVisible(false);
					model.getContextMenu().getAddExistingScheme().setVisible(false);
					model.getContextMenu().getDelete().setVisible(true);
					model.getContextMenu().getRemoveFromDisk().setVisible(false);
					model.getContextMenu().getRename().setVisible(true);
					model.getContextMenu().getShowInSystemExplorer().setVisible(false);
					model.getContextMenu().getSave().setVisible(false);
					model.getContextMenu().getProperties().setVisible(true);

					
				}else if(object instanceof RelationModel) {
					model.getContextMenu().getAddNewProject().setVisible(false);
					model.getContextMenu().getAddExistingProject().setVisible(false);
					model.getContextMenu().getaddNewDiagram().setVisible(false);
					model.getContextMenu().getAddExistingScheme().setVisible(false);
					model.getContextMenu().getDelete().setVisible(true);
					model.getContextMenu().getRemoveFromDisk().setVisible(false);
					model.getContextMenu().getRename().setVisible(false);
					model.getContextMenu().getShowInSystemExplorer().setVisible(false);
					model.getContextMenu().getSave().setVisible(false);
					model.getContextMenu().getProperties().setVisible(true);

					
				}else if(object == "Elements" || object == "Relations") {
					model.getContextMenu().getAddNewProject().setVisible(false);
					model.getContextMenu().getAddExistingProject().setVisible(false);
					model.getContextMenu().getaddNewDiagram().setVisible(false);
					model.getContextMenu().getAddExistingScheme().setVisible(false);
					model.getContextMenu().getDelete().setVisible(false);
					model.getContextMenu().getRemoveFromDisk().setVisible(false);
					model.getContextMenu().getRename().setVisible(false);
					model.getContextMenu().getShowInSystemExplorer().setVisible(false);
					model.getContextMenu().getSave().setVisible(false);
					model.getContextMenu().getProperties().setVisible(false);

				}

				model.getContextMenu().show(e.getComponent(), e.getX(), e.getY());
			
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

}
