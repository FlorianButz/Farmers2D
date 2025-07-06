package de.demoncore.Farmers2D.questSystem;

import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.itemSystem.Item;
import de.demoncore.Farmers2D.logic.Settings;
import de.demoncore.Farmers2D.questSystem.quests.*;
import de.demoncore.Farmers2D.saveFiles.SaveFile;
import de.demoncore.Farmers2D.saveFiles.SaveManager;
import de.demoncore.Farmers2D.utils.GameActionListener;
import de.demoncore.Farmers2D.utils.KeyHandler;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.enums.QuestType;

import java.util.ArrayList;
import java.util.Iterator;

public class QuestManager {
    public static QuestManager instance;

    public int maxQuestCapacity = 30;
    public ArrayList<Quest> currentQuests = new ArrayList<>();

    public int lastId = -1;

    public QuestManager(){
        instance = this;
    }

    public void load(){
        QuestManager loaded = SaveManager.loadFromFile(SaveFile.QUESTMANAGER, QuestManager.class, instance);
        if (loaded != null) instance = loaded;
    }

    public boolean addNewQuest(Quest quest, boolean force){
        if((!force && currentQuests.size() >= maxQuestCapacity) || quest == null) return false;
        currentQuests.add(quest);
        return true;
    }

    /**
     * Creates a new quest based on the given QuestType.
     *
     * @param type        The type of the quest (e.g. SELL, COLLECT, POSITION).
     * @param title       The title of the quest.
     * @param description A short description of the quest.
     * @param difficulty  The difficulty level of the quest.
     * @param rank        The rank required or rewarded for the quest.
     * @param items       A list of items needed or involved in the quest.
     *                    Can be null if the quest type doesn't use items (e.g. POSITION).
     * @param pos         The target position for the quest (e.g. for POSITION quests).
     *                    Can be null if the quest type doesn't use a position (e.g. SELL, COLLECT).
     * @return A new Quest object matching the given type, or null if the type is invalid.
     */
    public Quest getNewQuest(QuestType type, String title, String description, int difficulty, int rank, ArrayList<Item> items, Vector2 pos){
        Quest quest;
        switch (type){
            case SELL:
                quest = new SellQuest(title, description, difficulty, rank, items);
                break;
            case COLLECT:
                quest = new CollectQuest(title, description, difficulty, rank, items);
                break;
            case POSITION:
                quest = new PositionQuest(title, description, difficulty, rank, pos);
                break;
            default:
                Logger.logError("invalid QuestType->"+type, new IllegalArgumentException());
                return null;
        }
        return quest;
    }

    public TimedQuest getnewTimedQuest(Quest quest, String title, String description, int difficulty, int rank){
        return new TimedQuest(title, description, difficulty, rank, quest);
    }

    public boolean removeQuest(Quest quest) {
        return currentQuests.remove(quest);
    }

    public boolean removeQuest(int id){
        Iterator<Quest> iterator = currentQuests.iterator();
        while (iterator.hasNext()) {
            Quest q = iterator.next();
            if (q.id == id) {
                iterator.remove();
                return true;
            }
        }
        return false;

    }

    public boolean forceCompleteQuest(int id){
        for(Quest q : currentQuests){
            if(q.id == id) {
                q.forceComplete();
                return true;
            }
        }
        return false;
    }

    public void updateAll(){
        for(Quest q : currentQuests) q.updateProgress();
    }

    public Quest getQuestByID(int id){
        for(Quest q : currentQuests){
            if(q.id == id) return q;
        }
        return null;
    }

    public int getCurrentId() {
        return ++lastId;
    }

    public void loadListeners() {
        //Logger.logInfo("size->"+currentQuests.size());

        for(Quest q : currentQuests){

            if(q.type == QuestType.COLLECT){
                q.listener = new GameActionListener() {
                    @Override
                    public void onItemCollect(Item item) {
                        super.onItemCollect(item);
                        ((CollectQuest) q).refreshItems(item);
                    }
                };
                KeyHandler.instance.add(q.listener, "Quest ->" + q.id);
                continue;
            }

            if(q.listener != null){
                KeyHandler.instance.add(q.listener, "Quest ->" + q.id);
            }
        }
    }
}
