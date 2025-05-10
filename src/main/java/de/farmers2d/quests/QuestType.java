package de.farmers2d.quests;

public enum QuestType {
	Action("action"),
	Gather("gather"),
	Kill("kill"),
	Location("location");
	
	private String displayName;
	
	private QuestType(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public String toString() {
		return displayName;	
	}
}
