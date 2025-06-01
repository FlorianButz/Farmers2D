package de.demoncore.Farmers2D.questSystem.quests;

import de.demoncore.Farmers2D.itemSystem.Item;
import de.demoncore.Farmers2D.utils.enums.QuestType;

import java.util.ArrayList;

public class SellQuest extends Quest{

    private ArrayList<Item> item;

    public SellQuest(String title, String description, int difficulty, int rank, ArrayList<Item> item){
        super(title, description, difficulty, rank, QuestType.SELL);
        this.item = item;
    }

    public SellQuest(){}

    @Override
    public void updateProgress() {

    }
}
