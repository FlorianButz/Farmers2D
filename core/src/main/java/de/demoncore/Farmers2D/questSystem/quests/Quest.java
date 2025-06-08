package de.demoncore.Farmers2D.questSystem.quests;

import de.demoncore.Farmers2D.questSystem.QuestManager;
import de.demoncore.Farmers2D.utils.enums.QuestType;
import de.demoncore.Farmers2D.utils.GameActionListener;

public abstract class Quest {

    public int id = -1;
    public String title;
    public String description;
    public QuestType type;
    public float progress;
    public boolean isCompleted;
    public int difficulty;
    public int rank;
    public Runnable questReward;
    public transient GameActionListener listener;

    public Quest(){}

    public Quest(String title, String description, int difficulty, int rank, QuestType type) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.rank = rank;
        this.type = type;
        id = QuestManager.instance.getCurrentId();
    }

    public abstract void updateProgress();

    public void forceComplete(){
        progress = 1;
        isCompleted = true;
        triggerReward();
    }

    public void triggerReward(){
        if(questReward != null) questReward.run();
    };

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " id:" + id + " -> T:" + title + " d:" + description + " dif:" + difficulty + " r:" + rank + " prog:" + progress;
    }
}
