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
import de.demoncore.gameObjects.QuestBoard;
import de.demoncore.gui.GUIAlignment;
import de.demoncore.gui.GUIButton;
import de.demoncore.gui.GUIButtonClickEvent;
import de.demoncore.gui.dialog.Dialog;
import de.demoncore.utils.GameMath;
import de.demoncore.utils.Logger;
import de.demoncore.utils.Resources;
import de.demoncore.utils.Vector3;
import de.farmers2d.quests.QuestManager;

public class TestScene extends BaseScene{
	
	private GameObject cameraFollow;
	
	public TestScene() {}
	
	
	@Override
	public void initializeScene() {
		super.initializeScene();
		
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
		
		QuestBoard qb = new QuestBoard(100, 100, 150, 150);
		qb.color = new Color(0, 0, 0, 0);
		qb.textColor = Color.white;
		addObject(qb);
		
		GameObject gO = new GameObject(100, 100, 50, 50);
		gO.color = Color.gray;
		addObject(gO);
		
		QuestManager q = new QuestManager();
		addObject(q);
		
		
		
	}
	
	@Override
	public void updateScene() {
		super.updateScene();
		
		if(Player.getInstance() != null) {
			cameraPosition = Vector3.lerp(cameraPosition, cameraFollow.getRawPosition(), 0.065f);
			
		}
		
	}
	
}
