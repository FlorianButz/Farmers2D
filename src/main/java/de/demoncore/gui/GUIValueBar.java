package de.demoncore.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import de.demoncore.game.GameLogic;
import de.demoncore.game.SceneManager;
import de.demoncore.rendering.Draw;
import de.demoncore.utils.GameMath;

public class GUIValueBar extends GUIObject {

	protected float value = 0;
	public float minValue = 0;
	public float maxValue = 0;

	float displayValue;
	
	public Color fillColor;
	public Color borderColor;
	
	public float borderSize = 1;
	
	public float updateSmoothing = 3.5f;
	
	public GUIValueBar(int posX, int posY, int width, int height, float minValue, float maxValue) {
		super(posX, posY, width, height);
		value = maxValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		
		color = Color.red;
		borderColor = Color.darkGray;
		fillColor = Color.green;
		
		displayValue = value;
	}
	
	@Override
	public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
		
		displayValue = GameMath.Lerp(displayValue, value, updateSmoothing / Draw.GetFramesPerSecond());
		
		g2d.setColor(color);
		g2d.fillRect((int)getUIPosition(screenWidth, screenHeight).x, (int)getUIPosition(screenWidth, screenHeight).y, (int)getScale().x, (int)getScale().y);
		
		g2d.setColor(fillColor);
		g2d.fillRect((int)getUIPosition(screenWidth, screenHeight).x, (int)getUIPosition(screenWidth, screenHeight).y, (int)(getScale().x * GameMath.RemapValue(displayValue, minValue, maxValue, 0f, 1f)), (int)getScale().y);

		g2d.setStroke(new BasicStroke(borderSize));
		g2d.setColor(borderColor);
		g2d.drawRect((int)getUIPosition(screenWidth, screenHeight).x, (int)getUIPosition(screenWidth, screenHeight).y, (int)getScale().x, (int)getScale().y);
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(float value) {
		if(value != this.value) {
			this.value = value;
			
			SceneManager.getActiveScene().shakeCamera(20, 10, 40);
		}
	}

	public void silentSetValue(float value) {
		if(value != this.value) {
			this.value = value;
		}
	}

}
