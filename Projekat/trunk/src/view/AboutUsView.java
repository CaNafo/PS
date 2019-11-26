package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class AboutUsView {
	
	public static final String  DIALOG_TEXT = "Use Case Editor (UCE) je open source softver kojeg je razvijala \n "
		                	 		+ "Grupa II, IV godine Elektrotehnickog fakulteta \n"
		                	 		+ " u Istocnom Sarajevu za potrebe predmeta Projektovanje Softvera pod "
		                	 		+ "mentorskom palicom profesora Branka Perisica i profesora Vladimira Vujovica. "
		                	 		+ "<br> ";
		                	 	
	public static final String  DIALOG_TITLE = "O Use-Case Editoru";
	
	JPanel Panel = new JPanel();
	JLabel ONamaTekstLabela = new JLabel("<html><div style='text-align:justify; padding-top:10px;'>" + DIALOG_TEXT + "</div></html>");
	JLabel podnaslovLabela = new JLabel("<html><div style='font-size:15px; font-weight:bold; margin-top:10px;'> Clanovi tima: </div></html>");
	
	JLabel borisSlika = new JLabel(new ImageIcon("images/boris.jpg"));
	JLabel BorisTekst = new JLabel("<html>Boris</html>");
	
	JLabel draganaSlika = new JLabel(new ImageIcon("images/dragana.jpg"));
	JLabel DraganaTekst = new JLabel("<html>Dragana</html>");
	
	JLabel DejanSlika = new JLabel(new ImageIcon("images/dejan.jpg"));
	JLabel DejanTekst = new JLabel("<html>Dejan</html>");
	
	JLabel NemanjaSlika = new JLabel(new ImageIcon("images/nemanja.jpg"));
	JLabel NemanjaTekst = new JLabel("<html>Nemanja</html>");
	
	private void setPanelProperties() {
		this.Panel.setBackground(new Color(0xffffff));
		this.Panel.setSize(new Dimension(500, 400));
		this.Panel.setLayout(null);
	}
	
	private void setBorisLabels() {
		this.borisSlika.setBounds(-185, 120, 550, 80);
		this.Panel.add(this.borisSlika);
		this.BorisTekst.setBounds(75, 170, 550, 80);
		this.Panel.add(this.BorisTekst);
	}
	
	private void setDraganaLabels() {
		this.draganaSlika.setBounds(50, 120, 550, 80);
		this.DraganaTekst.setBounds(300, 170, 550, 80);
		this.Panel.add(this.DraganaTekst);
		this.Panel.add(this.draganaSlika);
	}
	
	private void setDejanLabels() {
		this.DejanSlika.setBounds(-185, 240, 550, 80);
		this.DejanTekst.setBounds(75, 290, 550, 80);
		this.Panel.add(this.DejanTekst);
		this.Panel.add(this.DejanSlika);
	}
	
	private void setNemanjaLabels() {
		this.NemanjaSlika.setBounds(50, 240, 550, 80);
		this.NemanjaTekst.setBounds(300, 290, 550, 80);
		this.Panel.add(this.NemanjaTekst);
		this.Panel.add(this.NemanjaSlika);
	}
	
	
	private void setONamaTekstLabel() {
		this.ONamaTekstLabela.setBounds(10, 10, 400, 70);		
		this.ONamaTekstLabela.setFont(new Font("Arial", Font.BOLD, 11));
		this.ONamaTekstLabela.setHorizontalAlignment(SwingConstants.CENTER);
		this.Panel.add(this.ONamaTekstLabela);
	}
	
	private void setPodnaslovLabel() {
		this.podnaslovLabela.setBounds(150, 50, 470, 75);
		this.Panel.add(this.podnaslovLabela);
	}
	
	private void setAllDialogLabels() {
		this.setPodnaslovLabel();
		this.setONamaTekstLabel();
		this.setBorisLabels();
		this.setDraganaLabels();	
		this.setDejanLabels();
		this.setNemanjaLabels();
	}
	
	public void showAboutUsDialog() {
		this.setPanelProperties();
		this.setAllDialogLabels();
		UIManager.put("OptionPane.minimumSize",new Dimension(450, 440));        
		JOptionPane.showMessageDialog(null, Panel, DIALOG_TITLE, JOptionPane.PLAIN_MESSAGE);
	}
}
