package de.demoncore.Farmers2D.time;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.logic.GameState;

import static de.demoncore.Farmers2D.utils.Resources.debugFont;

public class TimeManager extends GameObject {

    public Time currentTime;

    public float speed = 1;

    public TimeManager() {
        super(Vector2.Zero.cpy(), Vector2.Zero.cpy(), Color.BLACK);

        currentTime = new Time();
    }

    double lastTime = 0;

    @Override
    public void update() {
        super.update();

        double currTime = System.nanoTime() / 1000000000.0 * (speed);

        if((currTime - lastTime) >= 1) {
            currentTime.advanceTime();
            lastTime = currTime;
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

    @Override
    public void drawHUD(SpriteBatch sb) {
        super.drawHUD(sb);
        if(!Game.instance.getScreen().screenObjects.contains(this)) return;
        debugFont.setColor(Color.WHITE);
        debugFont.draw(sb, getCurrentTime(), 0, Gdx.graphics.getHeight() - debugFont.getLineHeight());
    }

    @Override
    public void onCreation() {
        currentTime.setTime(GameState.instance.currentTime);
        super.onCreation();
    }

    @Override
    public void onDestroy() {
        GameState.instance.currentTime = currentTime.getTime();
        super.onDestroy();
    }
}
