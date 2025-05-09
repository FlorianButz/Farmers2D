package de.demoncore.gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

import de.demoncore.actions.GameActionListener;
import de.demoncore.actions.KeyHandler;
import de.demoncore.utils.Logger;

;

public class Player extends RigidBody{
	
	private static Player instance;
	
	private GameActionListener listener;

	private float playerAcceleration = 5f;

	public Player(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		instance = this;
		
		friction = 0.8f;
		
		listener = new GameActionListener() {
			@Override
			public void onSpaceKeyPressed() {
				super.onSpaceKeyPressed();
				Logger.logInfo("pressed Space");
				
			}
			
		};
	}
	
	@Override
	public void onAddToScene() {
		super.onAddToScene();
		KeyHandler.listeners.add(listener);
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KeyHandler.listeners.remove(listener);
	}
	
	
	public static Player getInstance() {
		return instance;
	}
	
	@Override
	public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
//		super.draw(g2d, screenWidth, screenHeight);
		
		g2d.setColor(Color.white);
		g2d.fillRoundRect(position.getX() - size.getX() / 2, position.getY() - size.getY() / 2, size.getX(), size.getY(), 20, 20);
		
		
	}
	
	@Override
	public void update() {
		super.update();
		
		addForce(KeyHandler.playerInput1.normalized().multiply(playerAcceleration ));
	}
	

}
