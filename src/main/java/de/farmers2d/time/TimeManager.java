package de.farmers2d.time;

import de.demoncore.game.GameObject;

public class TimeManager extends GameObject {

	public Time currentTime;
	
	public TimeManager() {
		super(0, 0, 0, 0);
		
		currentTime = new Time();
	}
	
	double lastTime = 0;
	
	@Override
	public void update() {
		super.update();
		
		double currTime = System.nanoTime() / 1000000000.0;
		
		if((currTime - lastTime) >= 1) {
			currentTime.advanceTime();
			lastTime = currTime;
		}
	}

	public String getCurrentTime() {
		// TODO Day
		// TODO Month
		// TODO Hour, Minute

		// TODO Konvertieren von Tagen zu name, genau so mit monat
		// TODO Die current hours + 6 rechnen, sodass der tag von 6-18 uhr geht.
		
		return "Monday, May 10:24";
	}
}