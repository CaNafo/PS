package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import command.CommandInterface;
import command.DeleteElementCommand;
import command.DeleteRelationCommand;
import controller.ActorPropertiesController;
import controller.RelationPropertiesController;
import controller.UseCasePropertiesController;
import model.ActorModel;
import model.DiagramModel;
import model.DocumentTreeModel;
import model.Element;
import model.RelationModel;
import model.SelectedElement;
import model.UseCaseModel;
import view.ActorPropertiesView;
import view.DiagramView;
import view.DocumentTreeView;
import view.RelationPropertiesView;
import view.UseCasePropertiesView;

@SuppressWarnings("serial")
public class CustomJPopoupMenu extends JPopupMenu {
	private Element element = null;
	private RelationModel relationModel = null;
	private DiagramView view;
	private Language language = new Language();
	
	private JMenuItem delete = new JMenuItem(language.getLanguage("Delete"));
	private JMenuItem properties = new JMenuItem(language.getLanguage("Properties"));
	
	private CustomJPopoupMenu jPopoupMenu;
	
	public CustomJPopoupMenu(Element elemet, DiagramView view) {
		this.element = elemet;
		this.view = view;
		this.jPopoupMenu = this;
		view.setComponentPopupMenu(this.jPopoupMenu);
		
		delete.addActionListener(actionListener);
		delete.setActionCommand("delete");
		add(delete);
		
		properties.addActionListener(actionListener);
		properties.setActionCommand("properties");
		add(properties);
	}
	
	public CustomJPopoupMenu(RelationModel relationModel, DiagramView view) {
		this.relationModel = relationModel;
		this.view = view;
		this.jPopoupMenu = this;
		view.setComponentPopupMenu(this.jPopoupMenu);
		
		delete.addActionListener(actionListener);
		delete.setActionCommand("delete");
		add(delete);
		
		properties.addActionListener(actionListener);
		properties.setActionCommand("properties");
		add(properties);
	}
	
	ActionListener actionListener = new ActionListener() {
		
		@SuppressWarnings("static-access")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "delete") {
				if(element != null) {
					Element temp = ((SelectedElement) element).getElement();
					DeleteElementCommand command = new DeleteElementCommand(view.getModel(), temp);
					command.execute(new String[] {"from-tree"});
					view.getModel().getUndoStack().push(command);

				}else if(relationModel != null) {
					DiagramModel model = relationModel.getDiagramModel();
					
					CommandInterface command = new DeleteRelationCommand(model,relationModel);		
					command.execute(null);
					model.getUndoStack().push(command);
				}
				DocumentTreeView view = DocumentTreeView.documentTreeView;
				new DocumentTreeModel().clearTree(view);
				view.expandAllNodes(view, 0, view.getRowCount());
				
			}else if(e.getActionCommand() == "properties") {
				if(element != null) {
					if(((SelectedElement) element).getElement() instanceof UseCaseModel) {
						UseCaseModel temp =  (UseCaseModel) ((SelectedElement) element).getElement();
						UseCasePropertiesView view = new UseCasePropertiesView(temp);
						new UseCasePropertiesController(view, temp);

					}else {
						ActorModel temp =  (ActorModel) ((SelectedElement) element).getElement();
						ActorPropertiesView view = new ActorPropertiesView(temp);
						new ActorPropertiesController(view, temp);
					}
				}else if(relationModel != null) {
					RelationPropertiesView view = new RelationPropertiesView(relationModel);
					new RelationPropertiesController(view, relationModel);
					
				}
			}
			
		}
	};
}
