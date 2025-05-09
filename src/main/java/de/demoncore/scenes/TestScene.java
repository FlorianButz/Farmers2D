package de.demoncore.scenes;

import de.demoncore.game.GameObject;
import de.demoncore.game.SceneManager;
import de.demoncore.game.Translation;
import de.demoncore.gameObjects.PauseMenu;
import de.demoncore.gameObjects.Player;
import de.demoncore.gui.GUIAlignment;
import de.demoncore.gui.GUIButton;
import de.demoncore.gui.GUIButtonClickEvent;
import de.demoncore.gui.dialog.Dialog;
import de.demoncore.utils.GameMath;
import de.demoncore.utils.Resources;
import de.demoncore.utils.Vector3;

public class TestScene extends BaseScene{
	
	private GameObject cameraFollow;
	
	public TestScene() {}
	
	
	@Override
	public void initializeScene() {
		super.initializeScene();
		
		GUIButton returnButton = new GUIButton(-250, 75, 400, 50, Translation.get("pausemenu.backtomainmenu"), Resources.uiFont.deriveFont(25f), new GUIButtonClickEvent() {
		@Override
		public void ButtonClick() {
			super.ButtonClick();
			SceneManager.loadScene(new MainMenu());
			
		}
		
		});
		
		returnButton.alignment = GUIAlignment.TopRight;
		
		addObject(returnButton);
		
		cameraFollow = Player.getInstance();
		
		if(cameraFollow == null) cameraFollow = new Player(0, 0, 50, 50);
		
		addObject(cameraFollow);
		
		for(int i = 0; i<100;i++) {
			GameObject gameObject = new GameObject(GameMath.RandomRange(-2000, 2000), GameMath.RandomRange(-2000, 2000), 50, 50);
			addObject(gameObject);
		}
		
		PauseMenu m = new PauseMenu();
		addObject(m);
	}
	
	@Override
	public void updateScene() {
		super.updateScene();
		
		if(Player.getInstance() != null) {
			cameraPosition = Vector3.lerp(cameraPosition, cameraFollow.getRawPosition(), 0.065f);
			
		}
		
	}
	
}
