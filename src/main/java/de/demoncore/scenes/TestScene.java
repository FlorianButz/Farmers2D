package de.demoncore.scenes;

import java.awt.Color;

import de.demoncore.actions.GameActionListener;
import de.demoncore.game.GameObject;
import de.demoncore.game.SceneManager;
import de.demoncore.game.Translation;
import de.demoncore.gameObjects.InteractableEvent;
import de.demoncore.gameObjects.InteractableObject;
import de.demoncore.gameObjects.ParticleSystem;
import de.demoncore.gameObjects.PauseMenu;
import de.demoncore.gameObjects.Player;
import de.demoncore.gui.GUIAlignment;
import de.demoncore.gui.GUIButton;
import de.demoncore.gui.GUIButtonClickEvent;
import de.demoncore.gui.dialog.Dialog;
import de.demoncore.utils.GameMath;
import de.demoncore.utils.Logger;
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
		
		ParticleSystem p = new ParticleSystem(0, 0);
		p.emitLoop = true;
		p.emitChunk = 10;
		p.initialParticleSize = 15;
		p.particleColorFirst = Color.white;
		p.particleGravity = 0.075f;
		p.initialParticleSpeedMax = new Vector3(25, 25);
		p.initialParticleSpeedMin = new Vector3(-25, -25);
		// p.Init(); start
		addObject(p);
		
		InteractableObject g = new InteractableObject(100, 100, 150, 150);
		g.color = new Color(0, 0, 0, 0);
		g.textColor = Color.white;
		g.event = new InteractableEvent() {
		@Override
		public void onInteract() {
			super.onInteract();
				
			
			}
		};
		addObject(g);
		
		GameObject gO = new GameObject(100, 100, 50, 50);
		gO.color = Color.gray;
		addObject(gO);
		
		
		
	}
	
	@Override
	public void updateScene() {
		super.updateScene();
		
		if(Player.getInstance() != null) {
			cameraPosition = Vector3.lerp(cameraPosition, cameraFollow.getRawPosition(), 0.065f);
			
		}
		
	}
	
}
