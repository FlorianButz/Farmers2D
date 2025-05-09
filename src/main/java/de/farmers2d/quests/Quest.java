package de.farmers2d.quests;

public class Quest {
	
	private String description = "";
	private String title = "";
	private boolean completed = false;
	
	public QuestCondition questCondition;
	public QuestReward questReward;
	
	
	public Quest() {}
	
	public void onUpdate() {
		if(completed) {
			questReward.onCompletion();
			questReward = null;
			return;
		}
		
		checkCondition();
		
	}
	
	/**
	 * Must set the variable "completed" to true to trigger the reward.
	 *
	 */
	public void checkCondition(QuestCondition condition) {
	    condition.onCheck();
	}

	
	public void checkCondition() {
		questCondition.onCheck();
	}
	
	
	
	
}
