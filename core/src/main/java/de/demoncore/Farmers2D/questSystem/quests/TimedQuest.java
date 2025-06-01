package de.demoncore.Farmers2D.questSystem.quests;

import de.demoncore.Farmers2D.utils.enums.QuestType;

public class TimedQuest extends Quest{

    private Quest quest;

    public TimedQuest(String title, String description, int difficulty, int rank, Quest quest) {
        super(title, description, difficulty, rank, QuestType.TIMED);
        this.quest = quest;
    }

    public TimedQuest(){}


    @Override
    public void updateProgress() {

    }
}
