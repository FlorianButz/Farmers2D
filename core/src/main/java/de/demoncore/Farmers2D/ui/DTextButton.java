package de.demoncore.Farmers2D.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.demoncore.Farmers2D.utils.Resources;

public class DTextButton extends TextButton {

    public DTextButton(String text) {
        super(text, Resources.uiSkin);

        TextButtonStyle style = getStyle();
        style.downFontColor = new Color(0.5f, 0.5f, 0.8f, 1f);
        style.overFontColor = new Color(0.8f, 0.8f, 1f, 1f);
        style.down = style.up;
        style.pressedOffsetY = -2;
        setStyle(style);

        setColor(Color.GRAY);

        this.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                addAction(Actions.color(Color.WHITE, 0.3f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                addAction(Actions.color(Color.GRAY, 0.3f));
            }
        });
    }
}
