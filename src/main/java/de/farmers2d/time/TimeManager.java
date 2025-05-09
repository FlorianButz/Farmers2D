package de.farmers2d.time;

import java.awt.Color;

import de.demoncore.game.GameObject;
import de.demoncore.game.SceneManager;
import de.demoncore.game.Translation;
import de.demoncore.gui.GUIAlignment;
import de.demoncore.gui.GUIText;
import de.demoncore.gui.TextAlignment;
import de.demoncore.utils.Resources;

public class TimeManager extends GameObject {

	public Time currentTime;
	public GUIText timeText;
	
	public float speed = 1;
	
	public TimeManager() {
		super(0, 0, 0, 0);
		
		currentTime = new Time();
		timeText = new GUIText(35, 50, Translation.literal("--:--"), Resources.uiFont.deriveFont(25F), Color.white);
		timeText.alignment = GUIAlignment.TopLeft;
		timeText.SetTextAlignment(TextAlignment.Left);
		SceneManager.getActiveScene().addObject(timeText);
	}
	
	double lastTime = 0;
	
	@Override
	public void update() {
		super.update();
		
		double currTime = System.nanoTime() / 1000000000.0 * (speed);
		
		if((currTime - lastTime) >= 1) {
			currentTime.advanceTime();
			lastTime = currTime;
			timeText.SetText(Translation.literal(getCurrentTime()));
		}
	}

	public String getCurrentTime() {
		StringBuilder builder = new StringBuilder();
		
		// TODO Add Translations
		
		switch((int)currentTime.currentDay) {
			case 0:
				builder.append("Monday");
				break;
			case 1:
				builder.append("Tuesday");
				break;
			case 2:
				builder.append("Wednsday");
				break;
			case 3:
				builder.append("Thursday");
				break;
			case 4:
				builder.append("Friday");
				break;
			case 5:
				builder.append("Saturday");
				break;
			case 6:
				builder.append("Sunday");
				break;
		}
		
		builder.append(", ");
		
		switch((int)currentTime.currentMonth) {
			case 0:
				builder.append("Jan");
				break;
			case 1:
				builder.append("Feb");
				break;
			case 2:
				builder.append("Mar");
				break;
			case 3:
				builder.append("Apr");
				break;
			case 4:
				builder.append("May");
				break;
			case 5:
				builder.append("Jun");
				break;
			case 6:
				builder.append("Jul");
				break;
			case 7:
				builder.append("Aug");
				break;
			case 8:
				builder.append("Sep");
				break;
			case 9:
				builder.append("Okt");
				break;
			case 10:
				builder.append("Nov");
				break;
			case 11:
				builder.append("Dec");
				break;
		}

		builder.append(" "); // Space
		
		int currentHour = 6 + (int)currentTime.currentHour;

		builder.append(currentHour <= 9 ? "0" + currentHour : currentHour);
		builder.append(":");
		builder.append(currentTime.currentMinute <= 9 ? "0" + currentTime.currentMinute : currentTime.currentMinute);
		
		return builder.toString();
	}
}