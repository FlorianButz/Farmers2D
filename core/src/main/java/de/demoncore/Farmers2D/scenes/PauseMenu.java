package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.demoncore.Farmers2D.Game;
import de.demoncore.Farmers2D.utils.Logger;

public class PauseMenu extends GUIScreen{

    Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

    @Override
    public void show() {
        super.show();

        TextButton backToGame = new TextButton("back to game", skin);
        backToGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.instance.switchScreens(0);
                super.clicked(event, x, y);
            }
        });
        //backToGame.getLabel().setFontScale(1.5f);   //<-disabled lässt Font verschwimmen
        addComponent(backToGame, 0.3f, 0.075f);


        TextButton settings = new TextButton("settings", skin);
        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.logInfo("settings");
                super.clicked(event, x, y);
            }
        });
        //settings.getLabel().setFontScale(1.5f);   //<-disabled lässt Font verschwimmen
        addComponent(settings, 0.3f, 0.075f);




    }
}
