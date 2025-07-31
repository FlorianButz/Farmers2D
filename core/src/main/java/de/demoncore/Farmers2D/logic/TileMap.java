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

    public TileMap(String path, float scale){
        this.scale = scale;
        map = new TmxMapLoader().load(path);
        mapRenderer = new OrthogonalTiledMapRenderer(map, scale);
        MapObjects objects;
        try {
            objects = map.getLayers().get("collisions").getObjects();
        } catch (NullPointerException e) {
            Logger.logError("no objects available", new NullPointerException());
            return;
        }
        for(MapObject obj : objects){
            RectangleMapObject rectObj = (RectangleMapObject) obj;
            Rectangle rect = rectObj.getRectangle();

            GameObject gameObject = new GameObject(
                    new Vector2(rect.getX() * scale, rect.getY() * scale),
                    new Vector2(rect.width * scale, rect.height * scale),
                    null);

            collisionBoxes.add(gameObject);
        }
    }


}
