package de.farmers2d.quests.core;

public abstract class QuestCondition {
	public boolean completed = false;
	
	/**
	 * Must set the variable "completed" to true to trigger the reward.
	 *
	 */
	public abstract void onCheck();
}
