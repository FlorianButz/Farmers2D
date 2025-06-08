package de.demoncore.Farmers2D.questSystem.quests;

import com.badlogic.gdx.math.MathUtils;
import de.demoncore.Farmers2D.gameObjects.Player;
import de.demoncore.Farmers2D.itemSystem.Item;
import de.demoncore.Farmers2D.utils.GameActionListener;
import de.demoncore.Farmers2D.utils.KeyHandler;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.enums.QuestType;

import java.util.ArrayList;
import java.util.Iterator;

public class CollectQuest extends Quest{

    public ArrayList<Item> items;
    public int startSize = 0;

    public CollectQuest(String title, String description, int difficulty, int rank, ArrayList<Item> items){
        super(title, description, difficulty, rank, QuestType.COLLECT);
        this.items = items;
        for (int i = 0; i < items.size(); i++) {
            startSize+= items.get(i).stackSize;
        }
        listener = new GameActionListener() {
            @Override
            public void onItemCollect(Item item) {
                super.onItemCollect(item);
                refreshItems(item);
            }
        };
        KeyHandler.instance.add(listener, "Quest ->" + id);

    }

    public CollectQuest(){
        super("","", 0, 0, QuestType.COLLECT);
    }

    @Override
    public void updateProgress() {
        if(isCompleted) return;
        if (items == null || items.isEmpty()) {
            triggerReward();
            isCompleted = true;
            progress = 1f;
            Logger.logInfo("completed Quest");
            return;
        }

        int remaining = 0;
        for (Item item : items) {
            remaining += item.stackSize;
        }

        progress = 1f - ((float) remaining / startSize);
        progress = MathUtils.clamp(progress, 0f, 1f);
    }

    public void refreshItems(Item i){
        if(items == null || items.isEmpty()) return;
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.id == i.id) {
                item.stackSize--;
                if (item.stackSize <= 0) {
                    iterator.remove();
                }
            }
        }
    }
}
