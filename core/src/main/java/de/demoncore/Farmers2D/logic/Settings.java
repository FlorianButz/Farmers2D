package de.demoncore.Farmers2D.logic;

import de.demoncore.Farmers2D.saveFiles.SaveFile;
import de.demoncore.Farmers2D.saveFiles.SaveManager;
import de.demoncore.Farmers2D.utils.enums.Language;

public class Settings {
    public static Settings instance = new Settings();

    public boolean debug = false;
    public Language currentLanguage = Language.ENGLISH;

    public Settings() {}

    public static void load() {
        Settings loaded = SaveManager.loadFromFile(SaveFile.SETTINGS, Settings.class, instance);
        if (loaded != null) {
            instance = loaded;
        }
    }
}
