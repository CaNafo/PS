package application;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class Language {
	private String language;
	
	public Language() {
		File file = new File("language/language.txt");
		
		if(file.exists() && file.canRead()) {
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				this.language = bufferedReader.readLine();
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				Files.createDirectories(file.toPath().getParent());
				Files.createFile(file.toPath());
				setLanguage("en_us");
				this.language = "en_us";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setLanguage(String language) {
		
			File file = new File("language/language.txt");
			if(file.exists()) {
				file.delete();
			}
			
			try {
				Files.createDirectories(file.toPath().getParent());
				Files.createFile(file.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(file.exists() && file.canWrite()) {
				try {
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
					bufferedWriter.write(language);
					bufferedWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
	}	
	
	public String getLanguage(String item_name) {
		
		if(language.contains("srb_rs")) {
			switch (item_name) {
			case "File":
				return "Fajl";				
			case "Edit":
				return "Uredi";			
			case "View":
				return "Pogled";			
			case "Help":
				return "Pomoæ";			
			case "New Workspace":
				return "Novi radni prostor";			
			case "New Project":
				return "Novi projekat";			
			case "Add new project":
				return "Dodaj novi projekat";
			case "Create Diagram":
				return "Novi dijagram";		
			case "Add new diagram":
				return "Dodaj novi dijagram";	
			case "Open Workspace":
				return "Otvori radni prostor";
			case "Open Project":
				return "Otvori projekat";
			case "Add existing project":
				return "Dodaj postojeæi projekat";
			case "Open Diagram":
				return "Otvori dijagram";
			case "Add existing diagram":
				return "Dodaj postojeæi dijagram";
			case "Save All":
				return "Saèuvaj sve";
			case "Save":
				return "Saèuvaj";
			case "Properties":
				return "Opcije";
			case "Rename":
				return "Preimenuj";
			case "Open file in system explorer":
				return "Otvori datoteku u sistemskom pretraživaèu";
			case "Delete":
				return "Obriši";
			case "Delete from disk":
				return "Obriši sa diska";
			case "Exit":
				return "Izaði";
			case "Undo":
				return "Vrati original";
			case "Redo":
				return "Vrati izmjenu";
			case "Zoom in":
				return "Uveæaj";
			case "Zoom Out":
				return "Umanji";
			case "About Us...":
				return "O nama...";
			case "Change Language":
				return "Promjeni jezik";
			case "Arrow Selection Tool":
				return "Selekcija strjelicom";
			case "Rectangular Selection Tool":
				return "Selekcija kvadratom";
			case "Actor":
				return "Uèesnik";
			case "Use Case":
				return "Sluèaj korištenja";
			case "Generalization":
				return "Nasljeðivanje";
			case "Dependency":
				return "Zavisnost";
			case "Association":
				return "Asocijacija";
			case "Name":
				return "Naziv";
			case "Comment":
				return "Komentar";
			case "Pre-Conditions":
				return "Preduslovi";
			case "Action steps":
				return "Koraci";
			case "Extension Points":
				return "Proširenja";
			case "Exceptions":
				return "Izuzeci";
			case "Post-Conditions":
				return "Rezultati";
			case "Line Color":
				return "Boja linije";
			case "Background Color":
				return "Pozadina";
			case "Line Width":
				return "Debljina linije";
			case "Specification":
				return "Specifikacija";
			case "Cancel":
				return "Prekid";
			case "Stereotype":
				return "Stereotip";
			case "Would You like to perform automatic restart ?\n All unsaved work will be lost.":
				return "Da li želite da automatksi restartujete program?\n Sve nesaèuvane promjene æe biti izgubljene.";
			case "Warning":
				return "Upozorenje";
			case "Copy":
				return "Kopiranje";
			case "Close":
				return "Zatvori";
			default:
				return "No name";
			}
		}else if(this.language.contains("en_us")) {
			switch (item_name) {
			case "File":
				return item_name;				
			case "Edit":
				return item_name;				
			case "View":
				return item_name;				
			case "Help":
				return item_name;				
			case "New Workspace":
				return item_name;				
			case "New Project":
				return item_name;				
			case "Add new project":
				return item_name;				
			case "Create Diagram":
				return item_name;				
			case "Add new diagram":
				return item_name;				
			case "Open Workspace":
				return item_name;				
			case "Open Project":
				return item_name;				
			case "Add existing project":
				return item_name;				
			case "Open Diagram":
				return item_name;				
			case "Add existing diagram":
				return item_name;				
			case "Save All":
				return item_name;				
			case "Save":
				return item_name;	
			case "Properties":
				return item_name;	
			case "Rename":
				return item_name;				
			case "Open file in system explorer":
				return item_name;				
			case "Delete":
				return item_name;				
			case "Delete from disk":
				return item_name;				
			case "Exit":
				return item_name;				
			case "Undo":
				return item_name;				
			case "Redo":
				return item_name;				
			case "Zoom in":
				return item_name;				
			case "Zoom Out":
				return item_name;				
			case "About Us...":
				return item_name;				
			case "Change Language":
				return item_name;	
			case "Arrow Selection Tool":
				return item_name;	
			case "Rectangular Selection Tool":
				return item_name;	
			case "Actor":
				return item_name;	
			case "Use Case":
				return item_name;	
			case "Generalization":
				return item_name;	
			case "Dependency":
				return item_name;	
			case "Association":
				return item_name;	
			case "Name":
				return item_name;	
			case "Comment":
				return item_name;	
			case "Pre-Conditions":
				return item_name;	
			case "Action steps":
				return item_name;	
			case "Extension Points":
				return item_name;	
			case "Exceptions":
				return item_name;	
			case "Post-Conditions":
				return item_name;	
			case "Line Color":
				return item_name;	
			case "Background Color":
				return item_name;	
			case "Line Width":
				return item_name;	
			case "Specification":
				return item_name;
			case "Cancel":
				return item_name;	
			case "Stereotype":
				return item_name;
			case "Would You like to perform automatic restart ?\n All unsaved work will be lost.":
				return item_name;
			case "Warning":
				return item_name;
			case "Copy":
				return item_name;
			case "Close":
				return item_name;
			default:
				return "No name";
			}
		}else {
			return "No name for this language";
		}
	}
	
	public Image getLanguageImage() {
		Image language;
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		switch (this.language) {
		case "srb_rs":
			language = tk.getImage("images/srb.png");
			return language;
			
		case "en_us":
			language = tk.getImage("images/us.png");
			return language;

		default:
			language = tk.getImage("images/srb.png");
			return language;
		}
		
	}

	public String getLanguage() {
		return language;
	}
		
}
