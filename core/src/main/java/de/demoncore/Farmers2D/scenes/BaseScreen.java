package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.gameObjects.Player;
import de.demoncore.Farmers2D.scenes.utils.ShapeEntry;
import de.demoncore.Farmers2D.scenes.utils.SpriteEntry;

import java.util.ArrayList;

public class BaseScreen implements Screen {

    Vector2 windowSize = new Vector2(1200, 800);
    protected ShapeRenderer sr;
    protected SpriteBatch sb;

    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected GameObject cameraFollowObject;

    ArrayList<ShapeEntry> filledShapes = new ArrayList<>();
    ArrayList<ShapeEntry> lineShapes = new ArrayList<>();
    ArrayList<SpriteEntry> sprites = new ArrayList<>();

    public ArrayList<GameObject> screenObjects = new ArrayList<>();
    protected Stage stage;


    public void addObject(GameObject g){
        screenObjects.add(g);
    }

    public void drawFilledShape(){
        sr.begin(ShapeType.Filled);
        for (ShapeEntry entry : filledShapes) {
            GameObject obj = entry.getGameObject();
            if (obj != null && obj.isDistanceCulled) continue;

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

    public void addFillShape(GameObject g){
        addFillShape(g.getShapeEntry());
        screenObjects.add(g);
    }

    public void addLineShape(ShapeEntry se){
        lineShapes.add(se);
    }

    public void addLineShape(GameObject g){
        addLineShape(g.getShapeEntry());
        screenObjects.add(g);
    }

    public void addSprite(SpriteEntry se){
        sprites.add(se);
    }


    @Override
    public void show() {
        sr = new ShapeRenderer();
        sb = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(windowSize.x, windowSize.y, camera);
        viewport.apply();

        camera.position.set(windowSize.x / 2f, windowSize.y / 2f, 0);
        camera.update();

        for(GameObject gO : screenObjects){
            gO.onCreation();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(cameraFollowObject != null) {
            camera.position.lerp(new Vector3(cameraFollowObject.pos.x, cameraFollowObject.pos.y, 0), 0.1f);

            camera.update();
            sr.setProjectionMatrix(camera.combined);
            sb.setProjectionMatrix(camera.combined);
        }
        Rectangle viewportRect = calcViewport();

        for(GameObject g : screenObjects){
            if(g == null) continue;
            g.update();

            if(g.checkDistanceCulled(viewportRect)) {
                g.isDistanceCulled = false;
            }else {
                g.isDistanceCulled = true;
            }
        }
        drawFilledShape();
        drawLineShape();
        drawSprites();
    }

    private Rectangle calcViewport() {
        return new Rectangle(
                camera.position.x - camera.viewportWidth / 2,
                camera.position.y - camera.viewportHeight / 2,
                camera.viewportWidth,
                camera.viewportHeight
        );
    }


    @Override
    public void resize(int width, int height) {
        windowSize.set(width, height);
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        for(GameObject gO : screenObjects){
            gO.onDestroy();
        }
    }

    @Override
    public void dispose() {}

    public Stage getStage() {
        return stage;
    }
}
