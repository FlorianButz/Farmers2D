package de.demoncore.gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

import de.demoncore.actions.GameActionListener;
import de.demoncore.actions.KeyHandler;
import de.demoncore.game.GameObject;
import de.demoncore.game.Translation;
import de.demoncore.utils.Logger;
import de.demoncore.utils.Resources;

public class InteractableObject extends GameObject{
	
	private GameActionListener listener;
	public InteractableEvent event;
	public Color textColor;
	
	
	public InteractableObject(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		
		collisionEnabled = false;
		
		listener = new GameActionListener() {
			@Override
			public void onInteractionKeyPressed() {
				super.onInteractionKeyPressed();
				
				if(Player.getInstance().getBoundingBox().intersects(getBoundingBox())) {
					onInteraction();
				}
				
			}
		};
		
		KeyHandler.listeners.add(listener);
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		KeyHandler.listeners.remove(listener);
		
	}

	public void onInteraction() {
		if(event == null) {
			Logger.logWarning("No Interaction was set"); 
			return;
		}
		
		event.onInteract();
	}
	
	@Override
	public void draw(Graphics2D g, int screenWidth, int screenHeight) {
		super.draw(g, screenWidth, screenHeight);
		
		if(Player.getInstance().getBoundingBox().intersects(getBoundingBox())) {
			
			g.setFont(Resources.uiFont.deriveFont(25f));
			g.setColor(textColor);
			
			String text = Translation.get("interactable.interact").Get();
			int stringWidth = g.getFontMetrics().stringWidth(text);
			int stringHeight = g.getFontMetrics().getHeight();
			int posX = position.getX() - stringWidth / 2;
			int posY = position.getY() - size.getY() / 2 - stringHeight - stringHeight / 5;
			
			g.drawString(text, posX, posY);
			
			
		}
		
		
	}

}
