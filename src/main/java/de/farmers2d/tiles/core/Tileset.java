package de.farmers2d.tiles.core;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Random;

import de.demoncore.game.GameObject;
import de.demoncore.game.SceneManager;
import de.demoncore.utils.GameMath;
import de.demoncore.utils.Vector3;

public class Tileset extends GameObject {
	
	public int tileSize = 35;
	private HashMap<TilePosition, Tile> _tiles;
	
	private Random _random = new Random();
	
	public Tileset() {
		super(0, 0, 0, 0);
		
		this.distanceCulling = false;
		
		_tiles = new HashMap<TilePosition, Tile>();
	}
	
	public Random getRandom() {
		return _random;
	}
	
	public void setTile(TilePosition pos, Tile tile) {
		_tiles.put(pos, tile);
		tile.setTilePosition(pos);
	}
	
	public Tile getTileAtPosition(TilePosition pos) {
		return _tiles.get(pos);
	}
	
	@Override
	public void update() {
		super.update();
		
		isDistanceCulled = false;

		for (Tile t : _tiles.values()) {
			t.tick();
			
			// Random tick, best for plant growth or other stuff that doesn't need to happen every tick
			// Percentage is defined by the tile, not globally. Good for different speeds of plant growths
			if(_random.nextFloat() < t.getRandomTickPercentage())
				t.randomTick();
		}
	}
	
	@Override
	public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
		super.draw(g2d, screenWidth, screenHeight);
		
		for (Tile t : _tiles.values()) {
			TilePosition tPos = t.getTilePosition();
			
			Vector3 sPos = new Vector3(tPos.x * tileSize, tPos.y * tileSize);
			Vector3 sSize = new Vector3(tileSize + 1, tileSize + 1); // + 1 to avoid holes with bad screen resolution
			
			Rectangle r1 = new Rectangle((int)sPos.x, (int)sPos.y, (int)sSize.x, (int)sSize.y);
			Rectangle r2 = SceneManager.getActiveScene().getCameraViewport();
			
			if(r1.intersects(r2))
				t.draw(g2d, sPos, sSize);
		}
	}
}
