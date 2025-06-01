package de.demoncore.Farmers2D.questSystem.quests;

import de.demoncore.Farmers2D.itemSystem.Item;
import de.demoncore.Farmers2D.utils.enums.QuestType;

import java.util.ArrayList;

public class CollectQuest extends Quest{

    private ArrayList<Item> items;

    public CollectQuest(String title, String description, int difficulty, int rank, ArrayList<Item> items){
        super(title, description, difficulty, rank, QuestType.COLLECT);
        this.items = items;
    }

    public CollectQuest(){}


    @Override
    public void updateProgress() {

    }
}
