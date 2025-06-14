package de.demoncore.Farmers2D.time;

import com.badlogic.gdx.utils.StringBuilder;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.TimeListener;

import java.util.ArrayList;

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
        for(TimeListener tL : new ArrayList<>(Game.instance.timeListeners)) tL.onNewMinute();

        if(currentMinute >= (minutesInOneHour)) {
            currentMinute = 0;
            currentHour++;
            for(TimeListener tL : new ArrayList<>(Game.instance.timeListeners)) tL.onNewHour();
        }

        if(currentHour >= (hoursInOneDay)) {
            currentHour = 0;
            currentDay++;
            for(TimeListener tL : new ArrayList<>(Game.instance.timeListeners)) tL.onNewDay();
        }

        if(currentDay >= (daysInOneWeek)) {
            currentDay = 0;
            currentWeek++;
            for(TimeListener tL : new ArrayList<>(Game.instance.timeListeners)) tL.onNewWeek();
        }

        if(currentWeek >= (weeksInOneMonth)) {
            currentWeek = 0;
            currentMonth++;
            for(TimeListener tL : new ArrayList<>(Game.instance.timeListeners)) tL.onNewMonth();
        }

        if(currentMonth >= (monthsInOneYear)) {
            currentMonth = 0;
            currentYear++;
            for(TimeListener tL : new ArrayList<>(Game.instance.timeListeners)) tL.onNewYear();
        }

//		Logger.logMessage(currentHour + ":" + currentMinute + ", " + currentDay + ", " + currentWeek + ", " + currentMonth + ", " + currentYear);
    }

    public String getTime() {
        return String.format("%02d:%02d:%02d:%02d:%02d:%04d",
                currentMinute, currentHour, currentDay, currentWeek, currentMonth, currentYear);
    }

    public void setTime(String time) {
        if(time.isEmpty()) return;
        String[] split = time.split(":");
        if (split.length == 6) {
            currentMinute = Integer.parseInt(split[0]);
            currentHour = Integer.parseInt(split[1]);
            currentDay = Integer.parseInt(split[2]);
            currentWeek = Integer.parseInt(split[3]);
            currentMonth = Integer.parseInt(split[4]);
            currentYear = Integer.parseInt(split[5]);
        }else{
            Logger.logError("invalid Time to decode->\""+ time +"\"", new IllegalArgumentException());
        }
    }

}
