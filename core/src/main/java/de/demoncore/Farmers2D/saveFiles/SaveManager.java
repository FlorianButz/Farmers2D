package de.demoncore.Farmers2D.saveFiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import de.demoncore.Farmers2D.logic.GameState;
import de.demoncore.Farmers2D.logic.Settings;
import de.demoncore.Farmers2D.questSystem.QuestManager;
import de.demoncore.Farmers2D.utils.Logger;

public class SaveManager {

    public static <T> void saveToFile(T object, String name){
        Json json = new Json();
        FileHandle file = Gdx.files.local(name + ".json");
        file.writeString(json.toJson(object), false);
        Logger.logInfo("saved File-> "+name);
    }

    public static <T> T loadFromFile(String name, Class<T> clazz, T defaultValue){
        FileHandle file = Gdx.files.local(name + ".json");
        if(file.exists()){
            Json json = new Json();
            return json.fromJson(clazz, file);
        }
        Logger.logError("invalid File to load-> "+ name + ".json", new IllegalAccessException());
        return defaultValue;
    }

    public static void saveAll(){
        saveToFile(Settings.instance, SaveFile.SETTINGS);
        saveToFile(QuestManager.instance, SaveFile.QUESTMANAGER);
        saveToFile(GameState.instance, SaveFile.GAMESTATE);
    }


}
