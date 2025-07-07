package de.demoncore.Farmers2D.logic;

import de.demoncore.Farmers2D.itemSystem.Item;
import de.demoncore.Farmers2D.saveFiles.SaveFile;
import de.demoncore.Farmers2D.saveFiles.SaveManager;
import de.demoncore.Farmers2D.utils.Logger;

import java.util.ArrayList;

public class GameState {
    public static GameState instance;

    public ArrayList<Item> invItems = new ArrayList<>();
    public String currentTime = "";
    public float cameraZoom = 0.5f;

    public GameState(){
        instance = this;
    }

    public static void load() {
        GameState state = SaveManager.loadFromFile(SaveFile.GAMESTATE, GameState.class, instance);
        if(state != null) instance = state;
    }

    public void addItem(Item i){
        for(Item item : invItems){
            if(item.id == i.id){
                Logger.logInfo("increased Stack Size");
                item.stackSize++;
                Game.instance.keyHandler.triggerOnItemCollect(i);
                return;
            }
        }
        Logger.logInfo("added New Item");
        invItems.add(i);
        Game.instance.keyHandler.triggerOnItemCollect(i);
    }

}
