package de.demoncore.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.demoncore.main.Main;

public class SaveManager {

	public static <T> T LoadSave(String fileName){
		// Speicherdatei laden

		T deserializedObj = null;

		String dirPath = System.getenv("APPDATA") + "\\" + Main.gameName.replaceAll(" ", "");
		String filePath = dirPath + "\\" + fileName + ".dmcs";
		
		try (FileInputStream fileIn = new FileInputStream(filePath);
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
				deserializedObj = (T) in.readObject();
				
				return deserializedObj;

		} catch (IOException i) {
			
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
		
		return null;
	}
	
	public static <T> void SaveToFile(String fileName, T toSave) {
		// Speicherdatei erstellen
		
		String dirPath = System.getenv("APPDATA") + "\\" + Main.gameName.replaceAll(" ", "");
		String filePath = dirPath + "\\" + fileName + ".dmcs";
		
		new File(dirPath).mkdirs();
		
		try (FileOutputStream fileOut = new FileOutputStream(filePath);
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
				out.writeObject(toSave);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
}
