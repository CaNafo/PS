/***********************************************************************
 * Module:  DijagramParser.java
 * Author:  Boris
 * Purpose: Defines the Class DijagramParser
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
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DiagramSerializerDeserializer {

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static DiagramModel readFromFile(String path) {
		DiagramModel dijagram = new DiagramModel();
		File file = new File(path);
		BufferedReader reader;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Element.class, new ElementAdapter<Element>());
		Gson gson = gsonBuilder.create();
		
		if (file.exists() && file.canRead()) {
			try {
				reader = new BufferedReader(new FileReader(file));
				dijagram = gson.fromJson(reader, DiagramModel.class);
				for (Iterator<RelationModel> iterator = dijagram.getIteratorRelationModel(); iterator.hasNext();) {
					RelationModel relation = (RelationModel) iterator.next();
					
					relation.setStartElement(dijagram.getElementById(relation.getStartElementID()));
					relation.setEndElement(dijagram.getElementById(relation.getEndElementID()));
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO: handle exception
			}
		}
		dijagram.setDocumentPath(path);
		return dijagram;
	}

	/**
	 * 
	 * @param dijagram
	 * @param filePath
	 */
	public static void writeToFile(DiagramModel dijagram, String filePath) {
		File file = new File(filePath);
		BufferedWriter writer;

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Element.class, new ElementAdapter<Element>());
		Gson gson = gsonBuilder.create();

		if (!file.exists()) {

			Path path = Paths.get(filePath);
			try {
				Files.createDirectories(path.subpath(0, path.getNameCount() - 1));
				Files.createFile(path);
				writer = new BufferedWriter(new FileWriter(new File(filePath)));
				gson.toJson(dijagram, writer);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
		}else {
			file.delete();
			Path path = Paths.get(filePath);
			try {
				Files.createDirectories(path.subpath(0, path.getNameCount() - 1));
				Files.createFile(path);
				writer = new BufferedWriter(new FileWriter(new File(filePath)));
				gson.toJson(dijagram, writer);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}

}