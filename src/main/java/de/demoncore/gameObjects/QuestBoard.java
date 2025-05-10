package de.demoncore.gameObjects;

import de.farmers2d.quests.QuestManager;

public class QuestBoard extends InteractableObject {

	public QuestBoard(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		event = new InteractableEvent() {
			@Override
			public void onInteract() {
				super.onInteract();

				if (QuestManager.getInstance().currentQuest == null	|| QuestManager.getInstance().currentQuest.questReward.collected) {
					QuestManager.getInstance().createNewRandomQuest(1, 5);
				} else {
					QuestManager.getInstance().triggerUpdate();
				}
			}
		};
	}

}
