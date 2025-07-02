package de.demoncore.Farmers2D.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class Resources {

    public static Resources instance;
    public boolean initialized;

    public static BitmapFont debugFont;
    public static FileHandle debugFontFile;
    public static Skin uiSkin;
    public static Skin toggleSwitch;
    public static ArrayList<Drawable> toggleSwitchState;

    public static BitmapFont pixelFont;

    public Resources(){
        instance = this;
    }

    public void init() {
        // Font laden
        debugFontFile = Gdx.files.internal("fonts/0.ttf");
        debugFont = getFontTTF(debugFontFile, 10);
        uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        toggleSwitch = new Skin(Gdx.files.internal("ui/skins/toggleSwitch/toggleSwitch.json"));
        toggleSwitch.add("customFont", getFontTTF(debugFontFile, 10), BitmapFont.class);

        toggleSwitchState = new ArrayList<>();
        toggleSwitchState.add(new TextureRegionDrawable(toggleSwitch.getRegion("toggleButtonOnV2NoBorder")));
        toggleSwitchState.add(new TextureRegionDrawable(toggleSwitch.getRegion("toggleButtonMiddleV2")));
        toggleSwitchState.add(new TextureRegionDrawable(toggleSwitch.getRegion("toggleButtonOffV2NoBorder")));
        toggleSwitchState.add(new TextureRegionDrawable(toggleSwitch.getRegion("toggleButtonOnV2")));
        toggleSwitchState.add(new TextureRegionDrawable(toggleSwitch.getRegion("toggleButtonOffV2")));

        Logger.logInfo("Resources loaded");
        initialized = true;
    }

    public static BitmapFont getFontTTF(FileHandle file, int fontSize) {
        if(file == null) return null;
        Logger.logInfo("file->"+file);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.color = Color.WHITE;

        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }



}