package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import application.Language;
import model.RelationModel;

public class RelationPropertiesView extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private Color lineColor;
	private JButton okButton;
	private JButton cancelButon;
	private JTextArea txtAreaComment;
	JButton buttonLineColor;
	RelationModel model;
	private Language language = new Language();
	
	JComboBox<String> cb, cbsteretypes;
	
	public RelationPropertiesView(RelationModel model) {
		setTitle("Relation properties");
		this.model = model;

		//Dugmad Ok i Cancel
		JPanel buttonPanel = new JPanel();

	    okButton = new JButton("OK");
	    okButton.setPreferredSize(new Dimension(100, 30));
	    buttonPanel.add(okButton);

	    cancelButon = new JButton(language.getLanguage("Cancel"));
	    cancelButon.setPreferredSize(new Dimension(100, 30));
	    buttonPanel.add(cancelButon);

	    add(buttonPanel, BorderLayout.SOUTH);
	    
	   
	    
	    JPanel topPanel = new JPanel( new FlowLayout(FlowLayout.LEFT) );
	    JPanel bottomPanel = new JPanel(new FlowLayout());
	   		   
		JLabel lblComment = new JLabel(language.getLanguage("Comment")+":");
		lblComment.setHorizontalAlignment(SwingConstants.LEFT);//za horizontalno poravnanje
			
		//Text Area za komentar
		txtAreaComment = new JTextArea(6,46);
		txtAreaComment.setText(model.getComment());
		txtAreaComment.setLineWrap(true);
		txtAreaComment.setWrapStyleWord(true);
		JScrollPane scrollPaneComment= new JScrollPane(txtAreaComment);
		
		
		JLabel lblLineColor = new JLabel(language.getLanguage("Line Color")+":");	
		lineColor= model.getLineColor();
		
		//Button LineColor
		buttonLineColor = new JButton(new ImageIcon("images/colorchange.png"));
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Color initialBackground = model.getLineColor();
		        Color background = JColorChooser.showDialog(null, "Change Button Background",initialBackground);
		        if (background != null) {
		        	lineColor = background;
		        }
		      }
		    };
			
		buttonLineColor.addActionListener(actionListener);
		
		    
		JLabel lblLineWidth = new JLabel(language.getLanguage("Line Width")+":");
		lblLineWidth.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		//ComboBox
		String[] choices = { "1","2", "3","4","5"};
		cb = new JComboBox<String>(choices);
		
		switch (model.getLineWidth()) {
		case 1:
			cb.setSelectedIndex(0);
			break;
		case 2:
			cb.setSelectedIndex(1);
			break;
		case 3:
			cb.setSelectedIndex(2);
			break;
		case 4:
			cb.setSelectedIndex(3);
			break;
		case 5:
			cb.setSelectedIndex(4);
			break;

		default:
			cb.setSelectedIndex(0);
			break;
		}
		
		if(model.getRelationType() != 2) {
			JLabel lblPrototype = new JLabel(language.getLanguage("Stereotype")+":");
			lblPrototype.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			
				if(model.getRelationType() == 1) {
					
					String[] steretypes = { "","access", "implementation","import","merge"};
					cbsteretypes = new JComboBox<String>(steretypes);
					
					switch (model.getStereoType()) {
					case "":
						cbsteretypes.setSelectedIndex(0);
						break;
					case "access":
						cbsteretypes.setSelectedIndex(1);
						break;
					case "implementation":
						cbsteretypes.setSelectedIndex(2);
						break;
					case "import":
						cbsteretypes.setSelectedIndex(3);
						break;
					case "merge":
						cbsteretypes.setSelectedIndex(4);
						break;
					}
				}else {
					String[] steretypes = { "","include","import", "access","bind","call","derive","ejb-ref","extend","friend","instantiate","merge","refine","sameFile","service-ref","trace","use"};
					cbsteretypes = new JComboBox<String>(steretypes);
					
					switch (model.getStereoType()) {
					case "":
						cbsteretypes.setSelectedIndex(0);
						break;
					case "include":
						cbsteretypes.setSelectedIndex(1);
						break;
					case "import":
						cbsteretypes.setSelectedIndex(2);
						break;
					case "access":
						cbsteretypes.setSelectedIndex(3);
						break;
					case "bind":
						cbsteretypes.setSelectedIndex(4);
						break;
					case "call":
						cbsteretypes.setSelectedIndex(5);
						break;
					case "derive":
						cbsteretypes.setSelectedIndex(6);
						break;
					case "ejb-ref":
						cbsteretypes.setSelectedIndex(7);
						break;
					case "extend":
						cbsteretypes.setSelectedIndex(8);
						break;
					case "friend":
						cbsteretypes.setSelectedIndex(9);
						break;
					case "instantiate":
						cbsteretypes.setSelectedIndex(10);
						break;
					case "merge":
						cbsteretypes.setSelectedIndex(11);
						break;
					case "refine":
						cbsteretypes.setSelectedIndex(12);
						break;
					case "sameFile":
						cbsteretypes.setSelectedIndex(13);
						break;
					case "service-ref":
						cbsteretypes.setSelectedIndex(14);
						break;
					case "trace":
						cbsteretypes.setSelectedIndex(15);
						break;
					case "use":
						cbsteretypes.setSelectedIndex(16);
						break;
						
					}
				}
				
				bottomPanel.add(lblPrototype);
				bottomPanel.add(cbsteretypes);
		}else {

			String[] steretypes = {""};
			cbsteretypes = new JComboBox<String>(steretypes);
			cbsteretypes.setSelectedIndex(0);
		}
		
		
		
		
		//Dodavnje u donji FlowPanel
		bottomPanel.add(lblLineColor);
		bottomPanel.add(buttonLineColor);
		bottomPanel.add(lblLineWidth);
		bottomPanel.add(cb);
		
			
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(topPanel);
		panel.add(lblComment);
		panel.add(scrollPaneComment);
		panel.add(bottomPanel); 
		panel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
		
		add(panel, BorderLayout.CENTER);
		   
		setSize(580, 450);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	
	public void setOkListener(ActionListener actionListener) {
		okButton.addActionListener(actionListener);
	}
	public void setCancel(ActionListener actionListener) {
		cancelButon.addActionListener(actionListener);
	}
	
	
	public String getTxtAreaComment() {
		return txtAreaComment.getText();
	}
	
	public int getCb() {
		return Integer.parseInt(cb.getSelectedItem().toString());
	}
	
	public Color getLineColor() {
		return lineColor;
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
	
	public void setKeyListeners(KeyListener keyListener) {
		txtAreaComment.addKeyListener(keyListener);
		cb.addKeyListener(keyListener);
		
		if(model.getRelationType() != 2)
			cbsteretypes.addKeyListener(keyListener);
		
		buttonLineColor.addKeyListener(keyListener);
	}
	
	public String getPrototype() {
		return cbsteretypes.getSelectedItem().toString();
	}
}
