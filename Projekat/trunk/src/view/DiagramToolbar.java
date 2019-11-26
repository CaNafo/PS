package view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import application.CustomButton;
import application.Language;

@SuppressWarnings("serial")
public class DiagramToolbar extends JToolBar {
	private JTabbedPane tabbedPane;

	private CustomButton btnSelectArrow;
	private CustomButton btnSelectRect;
	private CustomButton btnActor;
	private CustomButton btnUseCase;
	private CustomButton btnGeneralization;
	private CustomButton btnDependency;
	private CustomButton btnAssociation;
	private CustomButton btnDelete;
	private CustomButton btnCopy;
	private JButton btnUndo;
	private JButton btnRedo;
	private Language language = new Language();

	private ActionListener oneButtonAtAtime = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton) (e.getSource());

			Component[] components = DiagramToolbar.this.getComponents();
			for (Component component : components) {
				if (component instanceof CustomButton) {
					if (button != component) {
						((CustomButton) component).setButtonPresseed(false);
					}
				}
			}

			
			// Postavljanje custom kursora miša
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			switch (e.getActionCommand()) {
			case "select-arrow":
				setMouseCursor(Cursor.getDefaultCursor());
				break;
			case "select-rect":
				setMouseCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				break;
			case "actor":
				Image actor = toolkit.getImage("images/actor.png");
				actor = actor.getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);
				Cursor actorCursor = toolkit.createCustomCursor(actor, new Point(16, 16), "actor");
				setMouseCursor(actorCursor);
				break;
			case "use-case":
				Image useCase = toolkit.getImage("images/use-case.png");
				useCase = useCase.getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);
				Cursor useCaseCursor = toolkit.createCustomCursor(useCase, new Point(16, 16), "use-case");
				setMouseCursor(useCaseCursor);
				break;
			case "generalization":
				Image generalization=toolkit.getImage("images/generalization-cursor.png");
				Cursor generalizationCursor=toolkit.createCustomCursor(generalization, new Point(0, 16), "generalization");
				setMouseCursor(generalizationCursor);
				break;
			case "dependency":
				Image dependency=toolkit.getImage("images/dependency-cursor.png");
				Cursor dependencyCursor=toolkit.createCustomCursor(dependency, new Point(0, 16), "dependency");
				setMouseCursor(dependencyCursor);
				break;
			case "association":
				Image association=toolkit.getImage("images/association-cursor.png");
				Cursor associationCursor=toolkit.createCustomCursor(association, new Point(0, 16), "association");
				setMouseCursor(associationCursor);
				break;
			case "delete":
				Image delete = toolkit.getImage("images/delete.png");
				Cursor deleteCurosor=toolkit.createCustomCursor(delete, new Point(16, 16), "delete");
				setMouseCursor(deleteCurosor);
				break;
			case "copy":
				Image copy = toolkit.getImage("images/copy.png");
				Cursor copyCursor=toolkit.createCustomCursor(copy, new Point(16, 16), "paste");
				setMouseCursor(copyCursor);
				break;
			}
		}
	};

	public void setMouseCursor(Cursor cursor) {
		for(int i=0; i<tabbedPane.getTabCount(); i++) {
			JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(i);
			JPanel panel = (JPanel) scrollPane.getViewport().getView();
			panel.setCursor(cursor);
		}
	}
	
	public DiagramToolbar(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
		setOrientation(JToolBar.VERTICAL);
		setFloatable(false);
		addButtons(32);
		btnSelectArrow.setButtonPresseed(true);
		Component[] components = this.getComponents();
		for (Component component : components) {
			if (component instanceof CustomButton) {
				((CustomButton) component).addActionListener(oneButtonAtAtime);
			}
		}
	}

	private void addButtons(int iconSize) {
		Toolkit tk = Toolkit.getDefaultToolkit();

		Image selectArrowIcon = tk.getImage("images/select-arrow.png");
		selectArrowIcon = selectArrowIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnSelectArrow = new CustomButton(new ImageIcon(selectArrowIcon));
		btnSelectArrow.setActionCommand("select-arrow");
		btnSelectArrow.setToolTipText(language.getLanguage("Arrow Selection Tool"));

		Image selectRectIcon = tk.getImage("images/select-rect.png");
		selectRectIcon = selectRectIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnSelectRect = new CustomButton(new ImageIcon(selectRectIcon));
		btnSelectRect.setActionCommand("select-rect");
		btnSelectRect.setToolTipText(language.getLanguage("Rectangular Selection Tool"));

		Image actorIcon = tk.getImage("images/actor.png");
		actorIcon = actorIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnActor = new CustomButton(new ImageIcon(actorIcon));
		btnActor.setActionCommand("actor");
		btnActor.setToolTipText(language.getLanguage("Actor"));

		Image useCaseIcon = tk.getImage("images/use-case.png");
		useCaseIcon = useCaseIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnUseCase = new CustomButton(new ImageIcon(useCaseIcon));
		btnUseCase.setActionCommand("use-case");
		btnUseCase.setToolTipText(language.getLanguage("Use Case"));

		Image generalizationIcon = tk.getImage("images/generalization.png");
		generalizationIcon = generalizationIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnGeneralization = new CustomButton(new ImageIcon(generalizationIcon));
		btnGeneralization.setActionCommand("generalization");
		btnGeneralization.setToolTipText(language.getLanguage("Generalization"));

		Image dependencyIcon = tk.getImage("images/dependency.png");
		dependencyIcon = dependencyIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnDependency = new CustomButton(new ImageIcon(dependencyIcon));
		btnDependency.setActionCommand("dependency");
		btnDependency.setToolTipText(language.getLanguage("Dependency"));

		Image associationIcon = tk.getImage("images/association.png");
		associationIcon = associationIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnAssociation = new CustomButton(new ImageIcon(associationIcon));
		btnAssociation.setActionCommand("association");
		btnAssociation.setToolTipText(language.getLanguage("Association"));

		Image deleteIcon = tk.getImage("images/delete.png");
		deleteIcon = deleteIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnDelete = new CustomButton(new ImageIcon(deleteIcon));
		btnDelete.setActionCommand("delete");
		btnDelete.setToolTipText(language.getLanguage("Delete"));
		
		Image copyIcon = tk.getImage("images/copy.png");
		copyIcon = copyIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnCopy = new CustomButton(new ImageIcon(copyIcon));
		btnCopy.setActionCommand("copy");
		btnCopy.setToolTipText(language.getLanguage("Copy"));

		Image undoIcon = tk.getImage("images/undo.png");
		undoIcon = undoIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnUndo = new JButton(new ImageIcon(undoIcon));
		btnUndo.setActionCommand("undo");
		btnUndo.setToolTipText(language.getLanguage("Undo"));
		btnUndo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		Image redoIcon = tk.getImage("images/redo.png");
		redoIcon = redoIcon.getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING);
		btnRedo = new JButton(new ImageIcon(redoIcon));
		btnRedo.setActionCommand("redo");
		btnRedo.setToolTipText(language.getLanguage("Redo"));
		btnRedo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		add(btnSelectArrow);
		add(btnSelectRect);
		addSeparator();
		add(btnActor);
		add(btnUseCase);
		addSeparator();
		add(btnGeneralization);
		add(btnDependency);
		add(btnAssociation);
		addSeparator();
		add(btnDelete);
		add(btnCopy);
		addSeparator();
		add(btnUndo);
		add(btnRedo);
	}

	public CustomButton getBtnSelectArrow() {
		return btnSelectArrow;
	}

	public CustomButton getBtnSelectRect() {
		return btnSelectRect;
	}

	public CustomButton getBtnActor() {
		return btnActor;
	}

	public CustomButton getBtnUseCase() {
		return btnUseCase;
	}

	public CustomButton getBtnGeneralization() {
		return btnGeneralization;
	}

	public CustomButton getBtnDependency() {
		return btnDependency;
	}

	public CustomButton getBtnAssociation() {
		return btnAssociation;
	}

	public CustomButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public CustomButton getBtnCopy() {
		return btnCopy;
	}
}
