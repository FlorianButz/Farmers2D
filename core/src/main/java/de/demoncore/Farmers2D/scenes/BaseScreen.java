package de.demoncore.Farmers2D.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.gameObjects.InteractableObject;
import de.demoncore.Farmers2D.itemSystem.Item;
import de.demoncore.Farmers2D.logic.Settings;
import de.demoncore.Farmers2D.scenes.utils.ShapeEntry;
import de.demoncore.Farmers2D.scenes.utils.SpriteEntry;
import de.demoncore.Farmers2D.utils.Logger;
import de.demoncore.Farmers2D.utils.Resources;
import de.demoncore.Farmers2D.utils.Translation;
import de.demoncore.Farmers2D.utils.UtilityMethods;

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

    private BitmapFont debugFont;

    public ArrayList<GameObject> screenObjects = new ArrayList<>();

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
     * Renders all filled shapes currently added to the screen.
     * Uses the ShapeRenderer with ShapeType.Filled.
     */
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

    /**
     * Renders all line-based shapes currently added to the screen.
     * Uses the ShapeRenderer with ShapeType.Line.
     */
    public void drawLineShape(){
        sr.begin(ShapeType.Line);

        for (ShapeEntry entry : lineShapes) {
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

    /**
     * Draws all sprites added to the screen using the SpriteBatch.
     */
    public void drawSprites(){
        sb.begin();
        for(SpriteEntry s : sprites) sb.draw(s.getSprite(), s.getVector().x, s.getVector().y);
        sb.end();
    }

    private void drawDebug(ShapeEntry entry){
        GameObject obj = entry.getGameObject();
        if (obj != null && obj.isDistanceCulled) return;

        sr.setColor(entry.getGameObject().collisionEnabled ? Color.LIME : Color.RED);

        float padding = 2f;
        float posX = entry.getPos().x - padding;
        float posY = entry.getPos().y - padding;
        float sizeX = entry.getSize().x + padding * 2;
        float sizeY = entry.getSize().y + padding * 2;

        switch (entry.getShape()) {
            case Rectangle:
                sr.rect(posX, posY, sizeX, sizeY);
                break;
            case Oval:
                sr.ellipse(posX, posY, sizeX ,sizeY);
                break;
            case Point:
                sr.point(posX, posY, 0);
                break;
            default:
                throw new IllegalStateException("unknown Shape: " + entry.getShape());
        }
    }

    private void drawAllDebugShapes() {
        sr.begin(ShapeType.Line);
        for (ShapeEntry entry : filledShapes) {
            drawDebug(entry);
        }
        for (ShapeEntry entry : lineShapes) {
            drawDebug(entry);
        }
        sr.end();
    }

    private void drawDebugText() {
        sb.begin();
        for (ShapeEntry entry : filledShapes) drawDebugLabel(entry);
        for (ShapeEntry entry : lineShapes) drawDebugLabel(entry);
        sb.end();
    }

    private void drawDebugLabel(ShapeEntry entry) {
        GameObject obj = entry.getGameObject();
        if (obj == null || obj.isDistanceCulled) return;

        String className = obj.getClass().getSimpleName();
        Vector2 pos = entry.getPos();
        if(debugFont != null) {
            debugFont.setColor(entry.getGameObject().collisionEnabled ? Color.LIME : Color.RED);
            debugFont.draw(sb, className, pos.x, pos.y + entry.getSize().y + 12); // 12px über dem Shape
            debugFont.draw(sb, UtilityMethods.formatVector(pos, 2), pos.x, pos.y + entry.getSize().y + 24);
        }
    }

    private void drawInteractableRange() {
        sr.begin(ShapeType.Line);
        sr.setColor(Color.RED);
        for (ShapeEntry entry : filledShapes){
            if(!(entry.getGameObject() instanceof InteractableObject)) continue;
            InteractableObject object = (InteractableObject) entry.getGameObject();
            sr.circle(entry.getPos().x + entry.getSize().x / 2, entry.getPos().y + entry.getSize().y / 2, object.interactionRange);
        }
        for (ShapeEntry entry : lineShapes){
            if(!(entry.getGameObject() instanceof InteractableObject)) continue;
            InteractableObject object = (InteractableObject) entry.getGameObject();
            sr.circle(entry.getPos().x + entry.getSize().x / 2, entry.getPos().y + entry.getSize().y / 2, object.interactionRange);
        }
        sr.end();
    }

    private void drawInteractableText() {
        if(debugFont == null) return;
        sb.begin();

        String interaction = Translation.get("action.interaction");
        float width = new GlyphLayout(debugFont, interaction).width;
        for (ShapeEntry entry : filledShapes){
            if(!(entry.getGameObject() instanceof InteractableObject)) continue;
            InteractableObject object = (InteractableObject) entry.getGameObject();
            if(!object.canInteract()) continue;
            Vector2 pos = entry.getPos();
            debugFont.setColor(Color.WHITE);
            debugFont.draw(sb, interaction, pos.x + entry.getSize().y / 2 - width / 2, pos.y + entry.getSize().y + 12);
        };
        for (ShapeEntry entry : lineShapes) {
            if(!(entry.getGameObject() instanceof InteractableObject)) continue;
            InteractableObject object = (InteractableObject) entry.getGameObject();
            if(!object.canInteract()) continue;
            Vector2 pos = entry.getPos();
            debugFont.setColor(Color.WHITE);
            debugFont.draw(sb, interaction, pos.x + entry.getSize().y / 2 - width / 2, pos.y + entry.getSize().y + 12);
        }
        sb.end();
    }

    /**
     * Adds a filled shape entry to be rendered on the screen.
     * @param se The ShapeEntry to add.
     */
    public void addFillShape(ShapeEntry se){
        filledShapes.add(se);
    }

    /**
     * Adds the shape representation of a GameObject as a filled shape.
     * Also adds the GameObject to the screenObjects list.
     * @param g The GameObject whose shape is to be added.
     */
    public void addFillShape(GameObject g){
        addFillShape(g.getShapeEntry());
        screenObjects.add(g);
    }

    /**
     * Adds a line shape entry to be rendered on the screen.
     * @param se The ShapeEntry to add.
     */
    public void addLineShape(ShapeEntry se){
        lineShapes.add(se);
    }

    /**
     * Adds the shape representation of a GameObject as a line shape.
     * Also adds the GameObject to the screenObjects list.
     * @param g The GameObject whose shape is to be added.
     */
    public void addLineShape(GameObject g){
        addLineShape(g.getShapeEntry());
        screenObjects.add(g);
    }

    /**
     * Adds a sprite to be rendered on the screen at a given position.
     * @param se The SpriteEntry to add.
     */
    public void addSprite(SpriteEntry se){
        sprites.add(se);
    }

    /**
     * Initializes core rendering components like ShapeRenderer, SpriteBatch, camera and viewport.
     */
    public void initialize(){
        sr = new ShapeRenderer();
        sb = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(windowSize.x, windowSize.y, camera);
        viewport.apply();

        camera.position.set(windowSize.x / 2f, windowSize.y / 2f, 0);
        camera.update();
    }

    @Override
    public void show() {
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
        drawInteractableText();
        if(Settings.instance.debug){
            drawAllDebugShapes();

            drawDebugText();
            drawInteractableRange();
        }

        if(debugFont == null && Resources.instance.initialized){
            debugFont = Resources.getFontTTF(Resources.debugFont, 10);
            Logger.logWarning("loaded debugFont");
        }
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
