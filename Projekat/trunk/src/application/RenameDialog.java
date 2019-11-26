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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import model.ActorModel;
import model.DiagramModel;
import model.ProjectModel;
import model.UseCaseModel;
import model.WorkspaceModel;

public class RenameDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	Object obj;
	private JTextField fileName = new JTextField(10);
	
	public RenameDialog(JFrame parent, Object obj) {
		super(parent, "Rename");
		this.obj = obj;		
		parent.setAlwaysOnTop(true);
		
		Point p = new Point(400, 400);
		setLocation(p.x, p.y);

		JPanel messagePane = new JPanel();
		if(obj instanceof WorkspaceModel) {
			fileName.setText(((WorkspaceModel) obj).getDocumentName());
		}else if(obj instanceof ProjectModel) {
			fileName.setText(((ProjectModel) obj).getDocumentName());
		}else if(obj instanceof DiagramModel) {
			fileName.setText(((DiagramModel) obj).getDocumentName());
		}else if(obj instanceof UseCaseModel) {
			fileName.setText(((UseCaseModel) obj).getName());
		}else if(obj instanceof ActorModel) {
			fileName.setText(((ActorModel) obj).getName());
		}
		messagePane.add(fileName);
		fileName.addKeyListener(keyListener);
		getContentPane().add(messagePane);

		JPanel buttonPane = new JPanel();
		JButton button = new JButton("Save");
		buttonPane.add(button);
		fileName.select(0, fileName.getText().length());
		button.addActionListener(new MyActionListener());
		getContentPane().add(buttonPane, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}



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

	class MyActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			rename();
		}
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
		
		@SuppressWarnings("static-access")
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == e.VK_ENTER) {
				rename();
			}
			
		}
	};
	
	private void rename() {
		if(obj instanceof WorkspaceModel) {
			((WorkspaceModel) obj).setDocumentName(fileName.getText());
		}else if(obj instanceof ProjectModel) {
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = ((ProjectModel) obj).getIteratorDijagrami(); iterator.hasNext();) {
				DiagramModel diagramModel = (DiagramModel) iterator.next();
				diagramModel.setDocumentPath(((ProjectModel) obj).getDocumentPath()+"\\"+fileName.getText()+"\\"+diagramModel.getDocumentName());
			}
							
			 File file = new File(((ProjectModel) obj).getDocumentPath()+"\\"+((ProjectModel) obj).getDocumentName());
			 file.renameTo(new File(((ProjectModel) obj).getDocumentPath()+"\\"+fileName.getText()));			
			 
			((ProjectModel) obj).setDocumentName(fileName.getText());

			((ProjectModel) obj).getWs().save();
		}else if(obj instanceof DiagramModel) {
			
			File file = new File(((DiagramModel) obj).getDocumentPath());
			file.delete();
			
			String newPath = ((DiagramModel) obj).getDocumentPath().
										replaceAll(((DiagramModel) obj).getDocumentName()
																	+".json", fileName.getText());
			((DiagramModel) obj).setDocumentName(fileName.getText());
			((DiagramModel) obj).setDocumentPath(newPath);
			((DiagramModel) obj).save();
			
			((DiagramModel) obj).getWorkspaceModel().save();
		}else if(obj instanceof UseCaseModel) {
			((UseCaseModel) obj).setName(fileName.getText());
		}else if(obj instanceof ActorModel) {
			((ActorModel) obj).setName(fileName.getText());
		}
		setVisible(false);
		dispose();
	}
}