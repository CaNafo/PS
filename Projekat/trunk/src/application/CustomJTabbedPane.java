package application;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import model.DiagramModel;
import view.DiagramView;

@SuppressWarnings("serial")
public class CustomJTabbedPane extends JTabbedPane {
	String title;
	private DiagramView view;
	@Override
	public void addTab(String title, Component component) {
		this.title = title;
		this.view = (DiagramView) component;
		
	  	JScrollPane scrollPane=new JScrollPane();
		scrollPane.setViewportView(component);
		super.addTab(title, scrollPane);
		this.setTabComponentAt(getTabCount() - 1, new CloseButtonTab(title, scrollPane));	
	}

	@Override
	public void addTab(String title, Icon icon, Component component) {
		this.title = title;
		JScrollPane scrollPane=new JScrollPane();
		scrollPane.setViewportView(component);
		super.addTab(title, icon, scrollPane);
		this.setTabComponentAt(getTabCount() - 1, new CloseButtonTab(title, icon, scrollPane));
	}

	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		this.title = title;
		JScrollPane scrollPane=new JScrollPane();
		scrollPane.setViewportView(component);
		super.addTab(title, icon, scrollPane, tip);
		this.setTabComponentAt(getTabCount() - 1, new CloseButtonTab(title, icon, scrollPane, tip));
	}

	public String getTitle() {
		return title;
	}
	
	/**
	 * Panel koji se smješta u jezièak kartice.
	 * 
	 *  
	 * @author Boris
	 *
	 */
	class CloseButtonTab extends JPanel {
		private JLabel title;
		private ImageIcon closeButtonIcon;
		private Component tab;
		private Icon tabIcon;
		private String toolTipText;

		/**
		 * 
		 * @param title
		 *            Naslov kartice
		 * @param tab
		 *            Komponenta koja se smjesta u karticu
		 */
		public CloseButtonTab(String title, Component tab) {
			this.tab = tab;
			this.title = new JLabel(title);
			Image image = getToolkit().getImage("images" + File.separator + "tab-close-black.png");
			image = image.getScaledInstance(14, 14, Image.SCALE_AREA_AVERAGING);
			this.closeButtonIcon = new ImageIcon(image);
			this.setUp();

		}

		/**
		 * 
		 * @param title
		 *            Naslov kartice
		 * @param icon
		 *            Ikonica koja se smjesta lijevo od naslova kartice (ikonica taba)
		 * @param tab
		 *            Komponenta koja se smjesta u karticu
		 */
		public CloseButtonTab(String title, Icon icon, Component tab) {
			this.tab = tab;
			this.title = new JLabel(title);
			Image image = getToolkit().getImage("images" + File.separator + "tab-close-black.png");
			image = image.getScaledInstance(14, 14, Image.SCALE_AREA_AVERAGING);
			this.closeButtonIcon = new ImageIcon(image);
			this.tabIcon = icon;
			this.setUp();
		}

		/**
		 * 
		 * @param title Naslov kartice
		 * @param icon Ikonica koja se smjesta lijevo od naslova kartice (ikonica taba)
		 * @param tab Komponenta koja se smjesta u karticu
		 * @param tip Tooltip na jeticku kartice
		 */
		public CloseButtonTab(String title, Icon icon, Component tab, String tip) {
			this.tab = tab;
			this.title = new JLabel(title);
			Image image = getToolkit().getImage("images" + File.separator + "tab-close-black.png");
			image = image.getScaledInstance(14, 14, Image.SCALE_AREA_AVERAGING);
			this.closeButtonIcon = new ImageIcon(image);
			this.tabIcon = icon;
			this.toolTipText = tip;
			this.setUp();
		}

		/**
		 * Metoda postavlja komponente na panel
		 */
		private void setUp() {
			this.setLayout(new FlowLayout(FlowLayout.RIGHT));
			this.setOpaque(false);
			if (tabIcon != null) {
				this.add(new JLabel(tabIcon));
			}
			this.add(title);
			JLabel closeLabel = new JLabel(closeButtonIcon);
			closeLabel.addMouseListener(new CloseButtonListener(tab));
			this.add(closeLabel);
			if (toolTipText != null)
				this.setToolTipText(toolTipText);
		}

		public ImageIcon getIcon() {
			return closeButtonIcon;
		}
	}

	/**
	 * Listener mijenja ikonicu labele u zavisnosti od toga da li je na njoj pokazivac misa.
	 * Takodje zatvara tab klikom na labelu.
	 * 
	 * @author Boris Boskovic
	 *
	 */
	class CloseButtonListener implements MouseListener {
		private Component tab;

		public CloseButtonListener(Component tab) {
			this.tab = tab;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel clickedLabel = (JLabel) e.getSource();
			JTabbedPane tabbedPane = (JTabbedPane) clickedLabel.getParent().getParent().getParent();
			tabbedPane.remove(tab);
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Image image = getToolkit().getImage("images" + File.separator + "tab-close-red.png");
			image = image.getScaledInstance(14, 14, Image.SCALE_AREA_AVERAGING);
			ImageIcon icon = new ImageIcon(image);
			((JLabel) e.getSource()).setIcon(icon);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Image image = getToolkit().getImage("images" + File.separator + "tab-close-black.png");
			image = image.getScaledInstance(14, 14, Image.SCALE_AREA_AVERAGING);
			ImageIcon icon = new ImageIcon(image);
			((JLabel) e.getSource()).setIcon(icon);
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}
	
	public DiagramModel getDiagramModel() {
		
		return view.getModel();
	}
}
