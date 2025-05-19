package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.GameObjects;
import de.demoncore.Farmers2D.scenes.utils.ShapeEntry;
import de.demoncore.Farmers2D.scenes.utils.SpriteEntry;

import java.util.ArrayList;

public class BaseScene implements Screen {

    Vector2 windowSize = new Vector2(1200, 800);
    protected ShapeRenderer sr;
    protected SpriteBatch sb;

    ArrayList<ShapeEntry> filledShapes = new ArrayList<>();
    ArrayList<ShapeEntry> lineShapes = new ArrayList<>();
    ArrayList<SpriteEntry> sprites = new ArrayList<>();

    ArrayList<GameObjects> sceneObjects = new ArrayList<>();


    public void addObject(GameObjects g){
        sceneObjects.add(g);
    }

    public void drawFilledShape(){
        sr.begin(ShapeType.Filled);
        for (ShapeEntry entry : filledShapes) {
            sr.setColor(entry.getColor());
            switch (entry.getShape()) {
                case Rectangle:
                    sr.rect(entry.getPos().x, entry.getPos().y, entry.getSize().x, entry.getSize().y);
                    break;
                case Oval:
                    sr.ellipse(entry.getPos().x, entry.getPos().y, entry.getSize().x, entry.getSize().y);
                    break;
                case Point:
                    sr.point(entry.getPos().x, entry.getPos().y, 0);
                    break;
                default:
                    throw new IllegalStateException("unknown Shape: " + entry.getShape());
            }
        }
        sr.end();
    }

    public void drawLineShape(){
        sr.begin(ShapeType.Line);
        for (ShapeEntry entry : filledShapes) {
            switch (entry.getShape()) {
                case Rectangle:
                    sr.rect(entry.getPos().x, entry.getPos().y, entry.getSize().x, entry.getSize().y);
                    break;
                case Oval:
                    sr.ellipse(entry.getPos().x, entry.getPos().y, entry.getSize().x, entry.getSize().y);
                    break;
                case Point:
                    sr.point(entry.getPos().x, entry.getPos().y, 0);
                    break;
                default:
                    throw new IllegalStateException("unknown Shape: " + entry.getShape());
            }
        }
        sr.end();
    }
    public void drawSprites(){
        sb.begin();
        for(SpriteEntry s : sprites) sb.draw(s.getSprite(), s.getVector().x, s.getVector().y);
        sb.end();
    }

    public void addFillShape(ShapeEntry se){
        filledShapes.add(se);
    }

    public void addFillShape(GameObjects g){
        addFillShape(g.getShapeEntry());
        sceneObjects.add(g);
    }

    public void addLineShape(ShapeEntry se){
        lineShapes.add(se);
    }

    public void addLineShape(GameObjects g){
        addLineShape(g.getShapeEntry());
        sceneObjects.add(g);
    }

    public void addSprite(SpriteEntry se){
        sprites.add(se);
    }


    @Override
    public void show() {
        sr = new ShapeRenderer();
        sb = new SpriteBatch();
        for(GameObjects gO : sceneObjects){
            gO.onCreation();
        }
    }

    @Override
    public void render(float delta) {
        for(GameObjects g : sceneObjects){
            g.update();
        }
        drawFilledShape();
        drawLineShape();
        drawSprites();
    }

    @Override
    public void resize(int width, int height) {
        windowSize = new Vector2(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        for(GameObjects gO : sceneObjects){
            gO.onDestroy();
        }
    }

    @Override
    public void dispose() {

    }
}
