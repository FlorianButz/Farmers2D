package de.farmers2d.quests;

import de.demoncore.game.GameObject;

public class QuestManager extends GameObject{

	private QuestManager instance;
	
	public QuestManager() {
		super(0, 0, 0, 0);
		instance = this;
	}
	
	public QuestManager getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * @param category used to decide which difficulty the Quest should have depending on the difficulty Amounts and rarity vary
	 * 
	 */
	public void createNewQuest(int category) {	
		//TODO Quest creation
		//TODO set active Quest
		//TODO display current Quest

	}
	
	
	
	
	
	
}
