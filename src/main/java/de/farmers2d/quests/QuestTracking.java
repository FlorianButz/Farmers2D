package de.farmers2d.quests;

import java.util.ArrayList;
import java.util.HashMap;

import de.demoncore.actions.GameActionListener;
import de.demoncore.actions.KeyHandler;
import de.demoncore.game.Translation;
import de.demoncore.gameObjects.Player;
import de.demoncore.utils.GameMath;
import de.demoncore.utils.Logger;
import de.demoncore.utils.Vector3;
import de.farmers2d.quests.core.QuestCondition;
import de.farmers2d.quests.core.QuestReward;
import de.farmers2d.quests.core.QuestTask;
import de.farmers2d.quests.core.QuestType;

public class QuestTracking {
	
	private static QuestTracking instance;
	
	public QuestTask<Integer> spacePressed;
	public int spacePressedTracked = 0;
	
	public QuestTask<Vector3> location;
	public Vector3 locationTracked;
	
	private ArrayList<QuestTask<?>> taskList = new ArrayList<>();		//TODO: separate Task for manual creation and variable creation
	private HashMap<QuestTask<?>, String> nameMap = new HashMap<>();

	
	public QuestTracking() {
		spacePressed = new QuestTask<>(QuestType.Action, 1, Translation.get("trackable.space").Get(), new QuestCondition() {	
			@Override
			public void onCheck() {
				if(QuestTracking.instance.spacePressed.getValue() == spacePressedTracked) {
					completed = true;
					Logger.logInfo("Completed the Quest");
				}
				
			}
		}, Integer.class);
		spacePressed.reward = new QuestReward() {
			@Override
			public void onCompletion() {
				collected = true;
				Logger.logInfo("rewarded ->"+(5 + spacePressed.getRewardValue()));
			}
		};
		registerTask(spacePressed, "spacePressed");

		location = new QuestTask<>(QuestType.Location, new Vector3(2000, 2000), Translation.get("trackable.location").Get(), new QuestCondition() {
			
			@Override
			public void onCheck() {
				if(locationTracked == null) return;
				if(Math.abs(Vector3.distance(locationTracked, location.getValue())) <= 50) {
					completed = true;
					Logger.logInfo("Completed the Quest at position-> " + locationTracked.ToString());
				}
				
			}
		}, Vector3.class);
		location.reward = new QuestReward() {
			@Override
			public void onCompletion() {
				collected = true;
				Logger.logInfo("rewarded ->"+(10 + location.getRewardValue()));
			}
		};
		registerTask(location, "location");

		
		instance = this;
	}
	
	public <T> void registerTask(QuestTask<T> task, String name) {
	    taskList.add(task);
	    nameMap.put(task, name);
	}

	
	
	public <T> QuestTask<T> getRandomQuestTask(QuestType type, Class<T> clazz) {
	    ArrayList<QuestTask<T>> filtered = new ArrayList<>();

	    for (QuestTask<?> task : taskList) {
	        if (task.getType() == type && clazz.isInstance(task.getValue())) {
	            @SuppressWarnings("unchecked")
	            QuestTask<T> typedTask = (QuestTask<T>) task;
	            filtered.add(typedTask);
	        }
	    }

	    if (filtered.isEmpty()) return null;

	    return filtered.get(GameMath.RandomRange(0, filtered.size()));
	}


	
	public void addSpacePresses(int value) {
		spacePressed.setValue(spacePressed.getValue() + value);
	}
	
	public void setSpacePresses(int value) {
		spacePressed.setValue(value);
	}
	
	public void addLocation(Vector3 vec) {
		location.setValue(location.getValue().add(vec));
	}
	
	public static QuestTracking getInstance() {
		return instance;
	}
	
}
