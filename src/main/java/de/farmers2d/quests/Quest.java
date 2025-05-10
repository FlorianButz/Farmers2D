package de.farmers2d.quests;

import de.farmers2d.quests.core.QuestCondition;
import de.farmers2d.quests.core.QuestReward;
import de.farmers2d.quests.core.QuestTask;
import de.farmers2d.quests.core.QuestType;

public class Quest {
	
	public String description = "";
	public String title = "";
	
	public QuestCondition questCondition;
	public QuestReward questReward;
	public QuestType questType;
	private QuestTask<?> task;
	
	
	public Quest() {}
	
	public Quest(QuestType questType, String title, String description) {
		this.questType = questType;
		this.title = title;
		this.description = description;
		
	}
	
	public void onUpdate() {
		if(questReward.collected) return;

		checkCondition();
		
		if(questCondition.completed) {
			questReward.onCompletion();
			return;
		}
		
	}
	
	public void checkCondition(QuestCondition condition) {
	    condition.onCheck();
	}

	
	public void checkCondition() {
		questCondition.onCheck();
	}
	
	public void setTask(QuestTask<?> task) {
		this.task = task.clone();
		questCondition = this.task.getCondition();
		questReward = this.task.reward;
	}
	
	public QuestTask<?> getTask() {
		return task;
	}
	
	@Override
	public String toString() {
		return title + " : " + description + " => " + questType.toString();
	}
	
	
	
	
}
