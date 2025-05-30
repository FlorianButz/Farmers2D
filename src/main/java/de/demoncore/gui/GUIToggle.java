package de.demoncore.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import de.demoncore.audio.AudioSource;
import de.demoncore.game.SceneManager;
import de.demoncore.game.Translation;
import de.demoncore.rendering.Draw;
import de.demoncore.utils.GameMath;
import de.demoncore.utils.Resources;

public class GUIToggle extends GUIObject {

	public Color normalColor = Color.white;
	public Color hoverColor = Color.black;

	public Color currentColor = normalColor;

	public Color normalCheckmarkColor = Color.black;
	public Color hoverCheckmarkColor = Color.white;

	public Color currentCheckmarkColor = normalCheckmarkColor;

	public float colorTransitionSmoothing = 12f;
	public float sizeTransitionSmoothing = 10f;

	public float normalButtonWidth;
	public float hoverButtonWidth = 25;
	public float currentButtonWidth;

	public AudioSource source;

	boolean isOn = false;

	GUIButtonClickEvent event;

	public GUIToggle(int posX, int posY, int width, int height, GUIButtonClickEvent e) {
		super(posX, posY, width, height);

		normalButtonWidth = width;
		currentButtonWidth = normalButtonWidth;

		currentColor = normalColor;
		currentCheckmarkColor = normalCheckmarkColor;

		event = e;
	}

	public void SetIsOn(boolean isOn) {
		this.isOn = isOn;
	}

	public boolean GetIsOn() {
		return isOn;
	}

	@Override
	public void onAddToScene() {
		super.onAddToScene();

		source = new AudioSource(this).setSpacial(false);
		SceneManager.getActiveScene().addObject(source);
	}

	float checkDownscaleFactor = 2.5f;
	float checkmarkPosX = -1;

	int limitDecimalPlaces = 2;
	
	@Override
	public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
		super.draw(g2d, screenWidth, screenHeight);

		g2d.setColor(currentColor);
		g2d.fillRect((int) getUIPosition(screenWidth, screenHeight).x, (int) getUIPosition(screenWidth, screenHeight).y,
				(int) getScale().x, (int) getScale().y);

		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(normalColor);
		g2d.drawRect((int) getUIPosition(screenWidth, screenHeight).x, (int) getUIPosition(screenWidth, screenHeight).y,
				(int) getScale().x, (int) getScale().y);

		int sX = (int) (size.x / checkDownscaleFactor);
		int sY = (int) (size.y / (checkDownscaleFactor / 2));
		int pX = (int) (getUIPosition(screenWidth, screenHeight).x + getScale().x - sX * 1.1f);
		if (!isOn)
			pX = (int) (getUIPosition(screenWidth, screenHeight).x + sX * 0.1f);
		int pY = (int) (getUIPosition(screenWidth, screenHeight).y + (getScale().y / 2) - sY / 2);

		if (checkmarkPosX == -1)
			checkmarkPosX = pX;
		checkmarkPosX = GameMath.limitDecimalPoints(GameMath.Lerp(checkmarkPosX, pX, 25f / Draw.GetFramesPerSecond()), limitDecimalPlaces);

		g2d.setFont(Resources.uiFont.deriveFont(0.75F * size.y));
		Rectangle2D bounds = g2d.getFontMetrics(g2d.getFont()).getStringBounds("String", g2d);
		if (isOn)
			g2d.drawString(Translation.get("settings.on").Get(),
					getUIPosition(screenWidth, screenHeight).x + getScale().x + 35,
					(float) (getUIPosition(screenWidth, screenHeight).y + size.y / 3 + bounds.getHeight() / 2));
		else
			g2d.drawString(Translation.get("settings.off").Get(),
					getUIPosition(screenWidth, screenHeight).x + size.x + 35,
					(float) (getUIPosition(screenWidth, screenHeight).y + getScale().y / 3 + bounds.getHeight() / 2));

		g2d.setColor(currentCheckmarkColor);
		g2d.fillRect((int) checkmarkPosX, pY, sX, sY);

		this.size.x = currentButtonWidth;
		float fps = Draw.GetFramesPerSecond();

		if (this.isHovering) {
			currentColor = GameMath.lerpColor(currentColor, hoverColor, colorTransitionSmoothing / fps);
			currentCheckmarkColor = GameMath.lerpColor(currentCheckmarkColor, hoverCheckmarkColor,
					colorTransitionSmoothing / fps);

			currentButtonWidth = GameMath.limitDecimalPoints(GameMath.Lerp(currentButtonWidth, normalButtonWidth + hoverButtonWidth,
					sizeTransitionSmoothing / fps), limitDecimalPlaces);
		} else {

			currentColor = GameMath.lerpColor(currentColor, normalColor, colorTransitionSmoothing / fps);
			currentCheckmarkColor = GameMath.lerpColor(currentCheckmarkColor, normalCheckmarkColor,
					colorTransitionSmoothing / fps);
			
			currentButtonWidth = GameMath.limitDecimalPoints(GameMath.Lerp(currentButtonWidth, normalButtonWidth,
					sizeTransitionSmoothing / fps), limitDecimalPlaces);
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
		currentCheckmarkColor = normalCheckmarkColor;

		SceneManager.getActiveScene().shakeCamera(60, 2, 45);
		event.ButtonClick();

		event.isMouseDown = true;
		event.ButtonDown();

		SetIsOn(!isOn);

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

		source.Play(Resources.buttonHover);
	}

	@Override
	public void update() {
		super.update();

		event.UpdateEvent();
	}

	public void SetButtonEvent(GUIButtonClickEvent event) {
		this.event = event;
	}

}
