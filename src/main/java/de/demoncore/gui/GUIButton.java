package de.demoncore.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import de.demoncore.audio.AudioSource;
import de.demoncore.game.SceneManager;
import de.demoncore.game.Settings;
import de.demoncore.game.TranslationComponent;
import de.demoncore.rendering.Draw;
import de.demoncore.utils.GameMath;
import de.demoncore.utils.Resources;

public class GUIButton extends GUIObject {

	public Color normalColor = Color.white;
	public Color hoverColor = Color.black;
	
	public Color currentColor = normalColor;
	
	public Color normalTextColor = Color.black;
	public Color hoverTextColor = Color.white;
	
	public Color currentTextColor = normalTextColor;

	public float colorTransitionSmoothing = 12f;
	public float sizeTransitionSmoothing = 10f;

	public float normalButtonWidth;
	public float hoverButtonWidth = 25;
	public float currentButtonWidth;
	
	public float textNormalSize;
	public float textHoverSize = 5F;
	public float textCurrentSize;
	
	public AudioSource source;
	
	TranslationComponent text;
	Font font;
	
	GUIButtonClickEvent event;
	
	public GUIButton(int posX, int posY, int width, int height, TranslationComponent text, Font font, GUIButtonClickEvent e) {
		super(posX, posY, width, height);
		
		this.text = text;
		this.font = font;
		
		textNormalSize = font.getSize();
		textCurrentSize = textNormalSize;
		
		normalButtonWidth = width;
		currentButtonWidth = normalButtonWidth;
		
		currentColor = normalColor;
		currentTextColor = normalTextColor;
		
		event = e;
	}
	
	@Override
	public void onAddToScene() {
		super.onAddToScene();

		source = new AudioSource(this).setSpacial(false);
		SceneManager.getActiveScene().addObject(source);
	}
	
	public void SetText(TranslationComponent text) {
		this.text = text;
	}
	
	int limitDecimalPlace = 2;
	
	@Override
	public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
		super.draw(g2d, screenWidth, screenHeight);

		g2d.setColor(currentColor);
		g2d.fillRect((int)getUIPosition(screenWidth, screenHeight).x, (int)getUIPosition(screenWidth, screenHeight).y, (int)size.x, (int)size.y);
		
		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(normalColor);
		g2d.drawRect((int)getUIPosition(screenWidth, screenHeight).x, (int)getUIPosition(screenWidth, screenHeight).y, (int)size.x, (int)size.y);
		
		g2d.setFont(font.deriveFont(textCurrentSize));
		g2d.setColor(currentTextColor);
		
		this.size.x = currentButtonWidth;
		
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(text.Get(), g2d);
		g2d.drawString(text.Get(), (int)(getUIPosition(screenWidth, screenHeight).x + this.getScale().x / 2 - bounds.getWidth() / 2),
				(int)(getUIPosition(screenWidth, screenHeight).y + this.getScale().y / 2 +  textCurrentSize / 3)
				);
		
		float fps = Draw.GetFramesPerSecond();
		
		if(this.isHovering) {
			currentColor = GameMath.lerpColor(currentColor, hoverColor, colorTransitionSmoothing / fps);
			currentTextColor = GameMath.lerpColor(currentTextColor, hoverTextColor, colorTransitionSmoothing / fps);
		
			textCurrentSize = GameMath.limitDecimalPoints(GameMath.Lerp(textCurrentSize, textNormalSize + textHoverSize, sizeTransitionSmoothing / fps), limitDecimalPlace);

			currentButtonWidth = GameMath.limitDecimalPoints(GameMath.Lerp(currentButtonWidth, normalButtonWidth + hoverButtonWidth, sizeTransitionSmoothing / fps), limitDecimalPlace);
		}
		else {
			
			currentColor = GameMath.lerpColor(currentColor, normalColor, colorTransitionSmoothing / fps);
			currentTextColor = GameMath.lerpColor(currentTextColor, normalTextColor, colorTransitionSmoothing / fps);
	
			textCurrentSize = GameMath.limitDecimalPoints(GameMath.Lerp(textCurrentSize, textNormalSize, sizeTransitionSmoothing / fps), limitDecimalPlace);

			currentButtonWidth = GameMath.limitDecimalPoints(GameMath.Lerp(currentButtonWidth, normalButtonWidth, sizeTransitionSmoothing / fps), limitDecimalPlace);
		}
	}
	
	@Override
	public void OnMouseUpUIObject(MouseEvent e) {
		super.OnMouseDownUIObject(e);

		event.isMouseDown = false;
		event.ButtonUp();
	}

	@Override
	public void OnMouseDownUIObject(MouseEvent e) {
		super.OnMouseDownUIObject(e);

		currentColor = normalColor;
		currentTextColor = normalTextColor;
		
		SceneManager.getActiveScene().shakeCamera(60, 2, 45);
		event.ButtonClick();
		
		event.isMouseDown = true;
		event.ButtonDown();
		
		source.Play(Resources.buttonClick);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		SceneManager.getActiveScene().destroyObject(source);
	}
	
	@Override
	public void onMouseHoverOverUIObject() {
		super.onMouseHoverOverUIObject();

		if(source != null)
			source.Play(Resources.buttonHover);
	}
	
	@Override
	public void update() {
		super.update();
		
		event.UpdateEvent();	
	}

	public void setButtonEvent(GUIButtonClickEvent event) {
		this.event = event;
	}
}
