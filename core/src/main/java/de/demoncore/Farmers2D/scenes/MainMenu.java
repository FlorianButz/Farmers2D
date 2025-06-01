package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.Resources;
import de.demoncore.Farmers2D.utils.Translation;

public class MainMenu extends GUIScreen{

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void show() {
        super.show();

        TextButton play = new TextButton(Translation.get("component.button.play"), Resources.uiSkin);
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Logger.logInfo("pressed play");
                Game.instance.switchScreens("default");
            }
        });
        addComponent(play, 30f, 7.5f);

        TextButton settings = new TextButton(Translation.get("component.button.settings"), Resources.uiSkin);
        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Logger.logInfo("pressed settings");
            }
        });
        addComponent(settings, 30f, 7.5f);


    }
}
