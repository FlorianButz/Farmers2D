package de.demoncore.Farmers2D.utils;

import de.demoncore.Farmers2D.logic.Settings;

import java.util.HashMap;

public class Translation {

    private static HashMap<String, String> german = new HashMap<>();
    private static HashMap<String, String> english = new HashMap<>();

    public Translation(){
        init();
    }

    public void init(){
        //action
        german.put("action.interaction", "[E] Interagieren");
        english.put("action.interaction", "[E] Interact");

        //component text
        german.put("component.button.btg", "Zurück zum Spiel");
        english.put("component.button.btg", "Return to Game");

        german.put("component.button.settings", "Einstellungen");
        english.put("component.button.settings", "Settings");

        german.put("component.button.play", "Spielen");
        english.put("component.button.play", "Play");

        Logger.logInfo("complete Loading translations");
    }

    public static String get(String s) {
        String value;
        switch (Settings.instance.currentLanguage){
            case GERMAN:
                value = german.get(s);
                break;
            case ENGLISH:
                value = english.get(s);
                break;
            default:
                Logger.logError("invalid Language-> "+ Settings.instance.currentLanguage, new IllegalArgumentException());
                return "Error";
        }
        if(value == null) return "Error";
        return value;
    }


}
