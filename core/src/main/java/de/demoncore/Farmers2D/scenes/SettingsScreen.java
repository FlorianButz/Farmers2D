package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.logic.Settings;
import de.demoncore.Farmers2D.ui.DTextButton;
import de.demoncore.Farmers2D.ui.ToggleSwitch;
import de.demoncore.Farmers2D.utils.GameActionListener;
import de.demoncore.Farmers2D.utils.KeyHandler;
import de.demoncore.Farmers2D.utils.Resources;
import de.demoncore.Farmers2D.utils.Translation;

import static com.badlogic.gdx.scenes.scene2d.ui.Value.percentHeight;
import static com.badlogic.gdx.scenes.scene2d.ui.Value.percentWidth;

public class SettingsScreen extends GUIScreen{

    Label.LabelStyle customStyle;
    GameActionListener listener;

    public SettingsScreen(){}

    @Override
    public void initialize() {
        super.initialize();
        customStyle = new Label.LabelStyle();
        customStyle.font = Resources.debugFont; //da ich die .ttf deiner Schriftart nicht habe temporär erstmal diese hier
        customStyle.fontColor = Color.WHITE;
    }

    @Override
    public void show() {
        super.show();

        KeyHandler.instance.add(listener = new GameActionListener() {
            @Override
            public void onEscapePressed() {
                Game.instance.switchScreenBack();
            }
        },"SettingsScreen");

        Table innerTable = new Table();
        addComponent(innerTable, 60, 100);

        Label l1 = new Label(Translation.get("settings.debug"), customStyle);
        ToggleSwitch tS = new ToggleSwitch();
        tS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.instance.debug = tS.isChecked();
            }
        });
        tS.setChecked(Settings.instance.debug);

        addComponentToTable(l1, innerTable, 40, 5);
        addComponentToTable(tS, innerTable, 10, 5);
        innerTable.row();

        Label l2 = new Label(Translation.get("settings.language"), customStyle);
        DTextButton dB = new DTextButton(Translation.get("component.button.language"));
        dB.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Settings.instance.changeLanguage();
                hide();
                show();
            }
        });

        addComponentToTable(l2, innerTable, 40, 5);
        addComponentToTable(dB, innerTable, 30, 5);
        innerTable.row();


    }

    private <T extends Actor> void addComponentToTable(T component, Table table, float widthPercent, float heightPercent){
        table.add(component)
                .width(percentWidth(widthPercent / 100f, table))
                .height(percentHeight(heightPercent / 100f, table))
                .padBottom(20);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        customStyle.font = Resources.getFontTTF(Resources.debugFontFile, (int) (height / 100f * 3)); //da ich die .ttf deiner Schriftart nicht habe temporär erstmal diese hier
        hide();
        show();
    }

    @Override
    public void hide() {
        super.hide();
        KeyHandler.instance.remove(listener, "SettingsScreen");
    }
}
