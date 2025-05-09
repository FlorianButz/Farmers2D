package de.farmers2d.scenes;

import de.demoncore.gameObjects.PauseMenu;
import de.demoncore.gameObjects.Player;
import de.demoncore.scenes.BaseScene;
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
		addObject(menu);
	}
	
}
