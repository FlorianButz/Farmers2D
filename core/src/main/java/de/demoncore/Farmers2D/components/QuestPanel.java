package de.demoncore.Farmers2D.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.demoncore.Farmers2D.utils.Logger;

public class QuestPanel extends Table {
    private Label titleLabel;
    private Label descriptionLabel;
    private ProgressBar progressBar;

    public QuestPanel(Skin skin, String title, String description, float progress) {
        super(skin);

        titleLabel = new Label(title, skin);
        descriptionLabel = new Label(description, skin);
        descriptionLabel.setWrap(true);

        ProgressBar.ProgressBarStyle progressStyle = new ProgressBar.ProgressBarStyle();
        progressStyle.background = skin.newDrawable("white", Color.DARK_GRAY);
        progressStyle.knobBefore = skin.newDrawable("white", Color.GREEN);
        progressStyle.knob = skin.newDrawable("white", Color.CLEAR);
        progressStyle.knobBefore.setMinHeight(10f);

        progressBar = new ProgressBar(0f, 100f, 1f, false, progressStyle);
        progressBar.setValue(progress);

        add(titleLabel).left().expandX().fillX().row();
        add(descriptionLabel).left().expandX().fillX().padTop(5).row();
        add(progressBar).expandX().fillX().padTop(10).height(20);

        setBackground(skin.newDrawable("white", new Color(1, 1, 1, 0.1f)));

        pad(10);
        top();
    }

    public void setProgress(float progress) {
        progressBar.setValue(progress);
    }

}
