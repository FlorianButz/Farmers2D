package de.farmers2d.quests;

import de.demoncore.actions.GameActionListener;
import de.demoncore.actions.KeyHandler;
import de.demoncore.game.GameObject;
import de.demoncore.game.Translation;
import de.demoncore.gameObjects.Player;
import de.demoncore.utils.GameMath;
import de.demoncore.utils.Logger;
import de.demoncore.utils.Vector3;
import de.farmers2d.quests.core.QuestTask;
import de.farmers2d.quests.core.QuestType;

public class QuestManager extends GameObject {

	private static QuestManager instance;

	public Quest currentQuest;
	private QuestTracking questTracking;

	private GameActionListener listener;

	public QuestManager() {
		super(100, 0, 0, 0);
		instance = this;
		questTracking = new QuestTracking();
		listener = new GameActionListener() {
			@Override
			public void onSpaceKeyPressed() {
				super.onSpaceKeyPressed();
				questTracking.spacePressedTracked++;
			}

			@Override
			public void onPlayerMovement() {
				super.onPlayerMovement();
				questTracking.locationTracked = Player.getInstance().getPosition();
			}
		};
		KeyHandler.listeners.add(listener);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		KeyHandler.listeners.remove(listener);
	}

	public static QuestManager getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param category   used to decide which additional difficulty the Quest should
	 *                   have depending on the difficulty Amounts and rarity vary
	 * @param difficulty used to set default difficulty
	 * 
	 */
	public void createNewRandomQuest(int category, int difficulty) {
		if (currentQuest != null) {
			if (!currentQuest.questReward.collected) {
				Logger.logInfo("currently doing a other Quest!1");
				return;
			}
		}
		questTracking = new QuestTracking();

		// TODO Quest creation
		// TODO display current Quest

		QuestType type = QuestType.values()[GameMath.RandomRange(0, QuestType.values().length)];

		createQuestType(difficulty + GameMath.RandomRange(category, category + 10), type);
	}

	private void createQuestType(int difficulty, QuestType type) {
		switch (type) {
		case Action:
			currentQuest = createActionQuest(difficulty);
			break;
		case Gather:
			currentQuest = createActionQuest(difficulty); // used since nothing to gather yet
//			currentQuest = createGatherQuest(difficulty);
			break;
		case Kill:
			currentQuest = createActionQuest(difficulty); // used since nothing to kill yet
//			currentQuest = createKillQuest(difficulty);
			break;
		case Location:
			currentQuest = createLocationQuest(difficulty);
			break;
		default:
			break;
		}
	}

	private Quest createActionQuest(int difficulty) {

		StringBuilder s = new StringBuilder();
		s.append(Translation.get("quest.description.action").Get());

		QuestTask<Integer> task = (QuestTask<Integer>) questTracking.getRandomQuestTask(QuestType.Action, Integer.class);
		task.setValue(task.getValue() + difficulty);
		task.setRewardValue((int) (task.getRewardValue() + difficulty * 0.5));

		String description = s.toString().replace("{x}", task.getValue() + "").replace("{action}",
				task.getTrackable() + "");

		Quest quest = new Quest(QuestType.Action, Translation.get("quest.title.action").Get(), description);

		quest.setTask(task);

		Logger.logInfo("created New Quest->" + quest.toString());
		return quest;
	}

	private Quest createLocationQuest(int difficulty) {

		StringBuilder s = new StringBuilder();
		s.append(Translation.get("quest.description.location").Get());

		QuestTask<Vector3> task = (QuestTask<Vector3>) questTracking.getRandomQuestTask(QuestType.Location, Vector3.class);
		task.setRewardValue((int) (task.getRewardValue() + difficulty * 0.5));

		String description = s.toString().replace("{action}", task.getValue().ToString() + "");

		Quest quest = new Quest(QuestType.Location, Translation.get("quest.title.location").Get(), description);

		quest.setTask(task);

		Logger.logInfo("created New Quest->" + quest.toString());
		return quest;
	}

	private Quest createGatherQuest(int difficulty) {

		StringBuilder s = new StringBuilder();
		s.append(Translation.get("quest.description.gather").Get());

		QuestTask<String> task = (QuestTask<String>) questTracking.getRandomQuestTask(QuestType.Gather, String.class);
		task.setRewardValue((int) (task.getRewardValue() + difficulty * 0.5));

		String description = s.toString().replace("{action}", task.getValue());

		Quest quest = new Quest(QuestType.Gather, Translation.get("quest.title.gather").Get(), description);

		quest.setTask(task);

		Logger.logInfo("created New Quest->" + quest.toString());
		return quest;
	}

	private Quest createKillQuest(int difficulty) {

		StringBuilder s = new StringBuilder();
		s.append(Translation.get("quest.description.kill").Get());

		QuestTask<Integer> task = (QuestTask<Integer>) questTracking.getRandomQuestTask(QuestType.Kill, Integer.class);
		task.setRewardValue((int) (task.getRewardValue() + difficulty * 0.5));

		String description = s.toString().replace("{x}", task.getValue() + "").replace("{action}",
				task.getTrackable() + "");

		Quest quest = new Quest(QuestType.Kill, Translation.get("quest.title.kill").Get(), description);

		quest.setTask(task);

		Logger.logInfo("created New Quest->" + quest.toString());
		return quest;
	}

	public void triggerUpdate() {
		if (!(currentQuest.questType == QuestType.Location))
			Logger.logInfo("updated Quest progress");
		currentQuest.onUpdate();
	}

	@Override
	public void update() {
		super.update();
		if (currentQuest == null)
			return;

		if (currentQuest.questType == QuestType.Location) {
			triggerUpdate();
		}
	}

}
