package de.demoncore.Farmers2D.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import de.demoncore.Farmers2D.utils.Resources;

import java.util.ArrayList;

public class ToggleSwitch extends CheckBox {

    private ArrayList<Drawable> states;
    private int currentState = 0;
    private int targetState = 0;
    private int frameTimer;

    public ToggleSwitch() {
        super("", Resources.toggleSwitch);
        states = Resources.toggleSwitchState;
        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(currentState == 0) targetState = 2;
                if(currentState == 2) targetState = 0;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        states.get(currentState).draw(batch, getX(), getY(), getWidth(), getHeight());
        frameTimer++;
        if(frameTimer >= 2){
            if(currentState == targetState) return;
            if(currentState < targetState) currentState++;
            if(currentState > targetState) currentState--;
            frameTimer = 0;
        }
    }
}
