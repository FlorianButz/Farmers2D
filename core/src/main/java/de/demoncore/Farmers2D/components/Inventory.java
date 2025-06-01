package de.demoncore.Farmers2D.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.questSystem.QuestManager;
import de.demoncore.Farmers2D.questSystem.quests.Quest;
import de.demoncore.Farmers2D.utils.Logger;

import java.util.ArrayList;

public class Inventory extends Window {
    private Skin skin;
    private Stack contentStack;
    ArrayList<Table> tabs = new ArrayList<>();

    private ScrollPane scrollPane;

    public Inventory(Skin skin) {
        super("Inventory", skin);
        this.skin = skin;
        setMovable(false);
        setVisible(false);
        init();
    }

    private void init() {
        Table tabBar = new Table();

        TextButton tab1 = new TextButton("Tab 1", skin);
        TextButton tab2 = new TextButton("Tab 2", skin);
        TextButton tab3 = new TextButton("Quests", skin);

        tabBar.add(tab1).expandX().fillX();
        tabBar.add(tab2).expandX().fillX();
        tabBar.add(tab3).expandX().fillX();

        contentStack = new Stack();
        contentStack.setFillParent(true);

        Table content1 = new Table();
        content1.add(new Label("Tab 1", skin));
        content1.row();
        content1.add(new TextButton("do something", skin));
        content1.setVisible(true);

        Table content2 = new Table();
        content2.add(new Label("Tab 2", skin));
        content2.setVisible(false);

        Table content3 = new Table();

        Table questContainer = new Table();
        questContainer.top().left();

        ArrayList<Quest> quests = new ArrayList<>(QuestManager.instance.currentQuests);
        for(Quest q : quests) {
            QuestPanel panel = new QuestPanel(skin, q.title, q.description, q.progress * 100);
            questContainer.add(panel).width(300).padBottom(10).row();
            Logger.logInfo("panel added");
        }
        scrollPane = new ScrollPane(questContainer, skin);
        scrollPane.setFadeScrollBars(true);
        scrollPane.setSmoothScrolling(true);
        scrollPane.setForceScroll(false, true);
        scrollPane.setScrollingDisabled(true, false);

        Table scrollWrapper = new Table();
        scrollWrapper.add(scrollPane).height(getHeight() * 0.8f).expandX().fillX();

        content3.add(scrollWrapper).expand().fill().pad(5);

        content3.setVisible(false);

        tabs.add(content1);
        tabs.add(content2);
        tabs.add(content3);

        for(Table t : tabs) contentStack.add(t);

        tab1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setVisibleTab(tabs , 0);
            }
        });

        tab2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setVisibleTab(tabs , 1);
            }
        });

        tab3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setVisibleTab(tabs , 2);
            }
        });

        add(tabBar).expandX().fillX().pad(5).row();
        add(contentStack).expand().fill().pad(10);
        setSize(600, 500);
        setKeepWithinStage(true);
    }

    public void setVisibleTab(ArrayList<Table> tabs, int index){
        for(Table t : tabs) t.setVisible(false);
        tabs.get(index).setVisible(true);

        if (index == 2 && scrollPane != null && scrollPane.getStage() != null) {
            scrollPane.getStage().setScrollFocus(scrollPane); // ← das ist der relevante Aufruf
        }
    }

    public void show(){
        refresh();
        setVisible(true);
        Game.instance.isPaused = true;
    }

    public void hide(){
        setVisible(false);
        Game.instance.isPaused = false;
    }

    public void refresh() {
        QuestManager.instance.updateAll();
        Table content3 = tabs.get(2);
        content3.clear();

        Table questContainer = new Table();
        questContainer.top().left();

        ArrayList<Quest> quests = new ArrayList<>(QuestManager.instance.currentQuests);
        for (Quest q : quests) {
            QuestPanel panel = new QuestPanel(skin, q.title, q.description, q.progress * 100);
            questContainer.add(panel).width(300).padBottom(10).row();
        }

        scrollPane = new ScrollPane(questContainer, skin);
        scrollPane.setFadeScrollBars(true);
        scrollPane.setSmoothScrolling(true);
        scrollPane.setForceScroll(false, true);
        scrollPane.setScrollingDisabled(true, false);

        Table scrollWrapper = new Table();
        scrollWrapper.add(scrollPane).height(getHeight() * 0.8f).expandX().fillX();

        content3.add(scrollWrapper).expand().fill().pad(5);
    }


}
