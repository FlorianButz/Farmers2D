package de.farmers2d.time;

import de.demoncore.utils.Logger;

public class Time {

	public final int monthsInOneYear = 12;
	public final int weeksInOneMonth = 2;
	public final int daysInOneWeek = 7;
	public final int hoursInOneDay = 12; // 12 Stunden Tag
	public final int minutesInOneHour = 60;

	public long currentYear = 0;
	public long currentMonth = 0;
	public long currentWeek = 0; // Braucht man leider wegen berechnung der monate
	public long currentDay = 0;
	public long currentHour = 0;
	public long currentMinute = 0;
	
	public void advanceTime() {
		currentMinute++;
		
		if(currentMinute >= (minutesInOneHour)) {
			currentMinute = 0;
			currentHour++;
		}
		
		if(currentHour >= (hoursInOneDay)) {
			currentHour = 0;
			currentDay++;
		}
		
		if(currentDay >= (daysInOneWeek)) {
			currentDay = 0;
			currentWeek++;
		}
		
		if(currentWeek >= (weeksInOneMonth)) {
			currentWeek = 0;
			currentMonth++;
		}
		
		if(currentMonth >= (monthsInOneYear)) {
			currentMonth = 0;
			currentYear++;
		}
		
		Logger.logMessage(currentHour + ":" + currentMinute + ", " + currentDay + ", " + currentWeek + ", " + currentMonth + ", " + currentYear);
	}
}
