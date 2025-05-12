package de.farmers2d.scenes;

import de.demoncore.gameObjects.PauseMenu;
import de.demoncore.gameObjects.Player;
import de.demoncore.scenes.BaseScene;
import de.demoncore.utils.Vector3;
import de.farmers2d.tiles.TestTile;
import de.farmers2d.tiles.core.TilePosition;
import de.farmers2d.tiles.core.Tileset;
import de.farmers2d.time.TimeManager;

public class FarmersBaseScene extends BaseScene {

	@Override
	public void initializeScene() {
		super.initializeScene();
	
		Player player = new Player(0, 0, 30, 30);
		addObject(player);
		
		TimeManager time = new TimeManager();
		addObject(time);
		
		PauseMenu menu = new PauseMenu();
		menu.inAnimationFromPosition = new Vector3(0, 2500);
		menu.outAnimationToPosition = new Vector3(0, 2500);
		addObject(menu);
		
		Tileset t = new Tileset();
		addObject(t);

		int size = 10;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				t.setTile(new TilePosition(i - size/2, j - size/2), new TestTile(t));
			}
		}
	}
	
}
