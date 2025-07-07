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

import java.util.ArrayList;

public class TileMap {

    public TiledMap map;
    public TiledMapRenderer mapRenderer;
    public float scale;

    public ArrayList<GameObject> collisionBoxes = new ArrayList<>();

    public TileMap(String path, float scale){
        this.scale = scale;
        map = new TmxMapLoader().load(path);
        mapRenderer = new OrthogonalTiledMapRenderer(map, scale);

        MapObjects objects = map.getLayers().get("collisions").getObjects();

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
