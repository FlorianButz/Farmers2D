package de.farmers2d.tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Function;
import java.util.function.Supplier;

import de.demoncore.utils.Vector3;
import de.farmers2d.tiles.core.Tile;
import de.farmers2d.tiles.core.Tileset;

public class TestTile extends Tile {	

	public TestTile(Tileset tileset) {
		super(tileset);
	}
	
	@Override
	public void draw(Graphics2D g2d, Vector3 screenPos, Vector3 size) {
		g2d.setColor(new Color(
				(byte)(getTileset().getRandom().nextFloat() * 100),
				(byte)(getTileset().getRandom().nextFloat() * 100),
				(byte)(getTileset().getRandom().nextFloat() * 100),
				255));
		
		g2d.fillRect((int)screenPos.x, (int)screenPos.y, (int)size.x, (int)size.y);
	}
}
