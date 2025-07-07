package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.gameObjects.InteractableObject;
import de.demoncore.Farmers2D.logic.Game;
import de.demoncore.Farmers2D.logic.GameState;
import de.demoncore.Farmers2D.logic.Settings;
import de.demoncore.Farmers2D.logic.TileMap;
import de.demoncore.Farmers2D.utils.*;

import java.util.ArrayList;

public class BaseScreen implements Screen {

    Vector2 windowSize = new Vector2(1200, 800);
    protected ShapeRenderer srFilled;
    protected ShapeRenderer srLine;
    protected SpriteBatch sb;

    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected GameObject cameraFollowObject;

    public ArrayList<GameObject> screenObjects = new ArrayList<>();

    protected TileMap map;

    /**
     * Adds a GameObject to the screen without immediately attaching a visual representation.
     * @param g The GameObject to add.
     */
    public void addObject(GameObject g){
        screenObjects.add(g);
    }

    public BaseScreen(){
        initialize();
    }

    /**
     * Draws all sprites added to the screen using the SpriteBatch.
     */
    public void drawSprites(){
        sb.begin();
        for(RenderListener rL : new ArrayList<>(Game.instance.renderListeners)) rL.onRenderBatch(sb);
        sb.end();
    }

    private void drawInteractableText() {
        sb.begin();

        String interaction = Translation.get("action.interaction");
        float width = new GlyphLayout(Resources.debugFont, interaction).width;
        for (GameObject gO : screenObjects){
            if(!(gO instanceof InteractableObject)) continue;
            InteractableObject object = (InteractableObject) gO;
            if(!object.canInteract()) continue;

            Vector2 pos = gO.pos;
            Resources.debugFont.setColor(Color.WHITE);
            Resources.debugFont.draw(sb, interaction, pos.x + gO.size.y / 2 - width / 2, pos.y + gO.size.y + 12);
        }
        sb.end();
    }

    /**
     * Initializes core rendering components like ShapeRenderer, SpriteBatch, camera and viewport.
     */
    public void initialize(){
        srFilled = new ShapeRenderer();
        srLine = new ShapeRenderer();
        sb = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(windowSize.x, windowSize.y, camera);
        viewport.apply();

        camera.position.set(0,0,0);
        camera.update();
    }

    @Override
    public void show() {
        if(map != null) {
            map.mapRenderer.setView(camera);
            for (GameObject gO : map.collisionBoxes) {
                addObject(gO);
            }
        }
        
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
            camera.zoom = GameState.instance.cameraZoom;

            camera.update();
            srFilled.setProjectionMatrix(camera.combined);
            srLine.setProjectionMatrix(camera.combined);
            sb.setProjectionMatrix(camera.combined);

            if(map != null){
                map.mapRenderer.setView(camera);
            }

        }
        Rectangle viewportRect = calcViewport();

        if(map != null) map.mapRenderer.render(new int[]{0, 1});

        for(GameObject g : screenObjects){
            if(g == null) continue;
            g.update();

            g.isDistanceCulled = !g.checkDistanceCulled(viewportRect);
        }
        srFilled.begin(ShapeType.Filled);
        srLine.begin(ShapeType.Line);
        for(RenderListener rL : new ArrayList<>(Game.instance.renderListeners)) rL.onRenderShapes(srLine, srFilled);
        drawSprites();
        drawInteractableText();
        srLine.end();
        srFilled.end();

        if(map != null) map.mapRenderer.render(new int[]{3, 4});

        if(Settings.instance.debug){
            srLine.begin(ShapeType.Line);
            sb.begin();
            for(RenderListener rl : new ArrayList<>(Game.instance.renderListeners)) rl.onRenderDebug(srLine, sb);
            sb.end();
            srLine.end();
        }

        Matrix4 oldMatrix = sb.getProjectionMatrix();
        Matrix4 oldMatrix1 = srFilled.getProjectionMatrix();
        Matrix4 oldMatrix2 = srLine.getProjectionMatrix();
        sb.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        srFilled.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        srLine.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        sb.begin();
        srFilled.begin(ShapeType.Filled);
        srLine.begin(ShapeType.Line);
        for(RenderListener rl : new ArrayList<>(Game.instance.renderListeners)) rl.onRenderHUD(sb, srLine, srFilled);
        sb.end();
        srFilled.end();
        srLine.end();

        sb.setProjectionMatrix(oldMatrix);
        srFilled.setProjectionMatrix(oldMatrix1);
        srLine.setProjectionMatrix(oldMatrix2);


    }

    /**
     * Calculates the visible world area based on the camera's current position and viewport size.
     * @return A Rectangle representing the camera's visible area in world coordinates.
     */
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
        //screenObjects.clear();
    }

    @Override
    public void dispose() {}

    /**
     * Returns a TextureRegion of the current Screen for use as Background
     * @return TextureRegion
     */
    public TextureRegion  takeScreenshotTexture() {
        Pixmap pixmap = Pixmap.createFromFrameBuffer(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());

        TextureRegion region = new TextureRegion(new Texture(pixmap));
        region.flip(false, true);
        pixmap.dispose();

        return region;
    }

}
