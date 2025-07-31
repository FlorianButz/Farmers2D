package de.demoncore.Farmers2D.logic;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.demoncore.Farmers2D.gameObjects.GameObject;
import de.demoncore.Farmers2D.utils.Logger;

import java.util.ArrayList;

public class TileMap {

    public TiledMap map;
    public TiledMapRenderer mapRenderer;
    public float scale;

    public ArrayList<GameObject> collisionBoxes = new ArrayList<>();
    public float tileSize = 16;
    public String tilesetID;
    private final int[] behindPlayerLayer;
    private final int[] beforePlayerLayer;
    private final int playerLayer;

    public TileMap(String path, float scale, int maxLayers, int playerLayer){
        this.scale = scale;
        map = new TmxMapLoader().load(path);
        mapRenderer = new OrthogonalTiledMapRenderer(map, scale);
        MapObjects objects;
        try {
            objects = map.getLayers().get("collisions").getObjects();
            for(MapObject obj : objects){
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Rectangle rect = rectObj.getRectangle();

                GameObject gameObject = new GameObject(
                        new Vector2(rect.getX() * scale, rect.getY() * scale),
                        new Vector2(rect.width * scale, rect.height * scale),
                        null);

                collisionBoxes.add(gameObject);
            }
        } catch (NullPointerException e) {
            Logger.logError("no objects available", new NullPointerException());
        }
        if(playerLayer >= maxLayers) playerLayer--;
        if(playerLayer == 0) playerLayer++;
        behindPlayerLayer = new int[playerLayer];
        this.playerLayer = playerLayer;
        beforePlayerLayer = new int[maxLayers - playerLayer - 1];

        for (int i = 0; i < behindPlayerLayer.length; i++) {
            behindPlayerLayer[i] = i;
        }

        for (int i = 0; i < beforePlayerLayer.length; i++) {
            beforePlayerLayer[i] = playerLayer + 1 + i;
        }

    }

    public void renderBehindPlayer(){
        if(behindPlayerLayer == null) return;
        mapRenderer.render(behindPlayerLayer);
    }

    public void renderBeforePlayer(){
        if(beforePlayerLayer == null) return;
        mapRenderer.render(beforePlayerLayer);
    }

    public void renderPlayerLayer(){
        mapRenderer.render(new int[]{playerLayer});
    }


}
