package de.farmers2d.quests;

public class QuestTask <T> implements Cloneable{
	
	private QuestType type;
	private T value;
	private String trackable;
	private QuestCondition questCondition;
	public QuestReward reward;
	private int rewardValue;
	
	public QuestTask(QuestType type, T value, String trackable,QuestCondition questCondition) {
		this.type = type;
		this.value = value;
		this.trackable = trackable;
		this.questCondition = questCondition;
	}
	
	public QuestType getType() {
		return type;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setType(QuestType type) {
		this.type = type;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public String getTrackable() {
		return trackable;
	}
	
	public void setTrackable(String trackable) {
		this.trackable = trackable;
	}

	public QuestCondition getCondition() {
		return questCondition;
	}

	public int getRewardValue() {
		return rewardValue;
	}

	public void setRewardValue(int rewardValue) {
		this.rewardValue = rewardValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected QuestTask<T> clone() {
		try {
			return (QuestTask<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
