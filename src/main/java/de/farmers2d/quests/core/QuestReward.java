package de.farmers2d.quests.core;

public abstract class QuestReward {
	public String description = "";
	public boolean collected = false;
	
	/**
	 * Must set the variable "collected" to true to prevent multiple collecting of the reward.
	 *
	 */
	public abstract void onCompletion();

}
