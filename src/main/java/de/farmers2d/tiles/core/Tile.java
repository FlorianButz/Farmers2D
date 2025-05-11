package de.farmers2d.tiles.core;

import java.awt.Graphics2D;

import de.demoncore.utils.Vector3;

public abstract class Tile {

	private Tileset _onTileset;
	private TilePosition _position;

	protected float randomTickPercentage = 0.1f;

	public Tile(Tileset tileset) {
		_onTileset = tileset;
	}
	
	public Tileset getTileset() {
		return _onTileset;
	}
	
	public float getRandomTickPercentage() {
		return randomTickPercentage;
	}
	
	public TilePosition getTilePosition() {
		return _position;
	}
	
	public void setTilePosition(TilePosition pos) {
		_position = pos;
	}
	
	public abstract void draw(Graphics2D g2d, Vector3 screenPos, Vector3 size);
	
	public void randomTick() {
		
	}
	
	public void tick() {
		
	}
}
