package de.demoncore.Farmers2D.questSystem.quests;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.Player;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.enums.QuestType;

public class PositionQuest extends Quest{

    private Vector2 startPos;
    private Vector2 position;

    public PositionQuest(String title, String description, int difficulty, int rank, Vector2 position){
        super(title, description, difficulty, rank, QuestType.POSITION);
        this.position = position;
        startPos = Player.instance.pos.cpy();
    }

    public PositionQuest(){}

    @Override
    public void updateProgress() {
        if(isCompleted) return;
        float dist = Player.instance.pos.dst(position);
        if(dist <= 50){
            triggerReward();
            isCompleted = true;
            Logger.logInfo("completed Quest");
            return;
        }

        progress = 1 - (dist / startPos.dst(position));
        progress = MathUtils.clamp(progress, 0f, 1f);
    }

    @Override
    public String toString() {
        return super.toString() + " targetPos:" + position;
    }
}
