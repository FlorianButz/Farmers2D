package de.demoncore.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.demoncore.game.GameObject;
import de.demoncore.game.SceneManager;
import de.demoncore.game.Translation;
import de.demoncore.gui.GUIAlignment;
import de.demoncore.gui.GUIText;
import de.demoncore.gui.GUIValueBar;
import de.demoncore.gui.Gui;
import de.demoncore.gui.ValueBarRenderable;
import de.demoncore.utils.Resources;
import de.demoncore.utils.Vector3;

public class SplashScreen extends BaseScene {
	
	String progressAction = "resources";
	public GUIValueBar progressBar;
	
	public static SplashScreen instance;
	
	@Override
	public void initializeScene() {
		super.initializeScene();
		instance = this;
		progressBar = new GUIValueBar(0, 0, 0, 0, 0, 0);
		addObject(progressBar);
	}
	
	public void createProgressBar(float max) {
		destroyObject(progressBar);
		
		progressBar = new GUIValueBar(0, 0, 1750, 50, 0, max){
			@Override
			public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
				super.draw(g2d, screenWidth, screenHeight);
				String text = "Loading " + progressAction + "...";
				Graphics2D g = (Graphics2D) g2d.create();
				
				if(Resources.uiFont != null)
					g.setFont(Resources.uiFont.deriveFont(75f));
				else
					g.setFont(g.getFont().deriveFont(75f));
				Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);

				g.drawString(text,
						getUIPosition(screenWidth, screenHeight).x + (int) (size.x / 2) - (int) (bounds.getMaxX() / 2),
						(float) (getUIPosition(screenWidth, screenHeight).y - bounds.getMaxY() / 2 - 125));
				
				g.setFont(g.getFont().deriveFont(45F));
				
				String demText = "Initializing Demoncore Ver 1.0.1";
				Rectangle2D bounds2 = g.getFontMetrics().getStringBounds(demText, g);
				g.drawString(demText,
						 screenWidth / 2 - (int) (bounds2.getMaxX() / 2),
						 screenHeight + 75);
			}
		};
		progressBar.alignment = GUIAlignment.Center;

		progressBar.updateSmoothing = 10f;
		
		progressBar.color = Color.black;
		progressBar.fillColor = Color.white;
		progressBar.borderColor = Color.gray;
		progressBar.borderSize = 2.5f;
		
		addObject(progressBar);
	}
	
	
	public void setProg(float progress, String action) {
		progressBar.silentSetValue(progress);
		this.progressAction = action;

		
		if(progress >= progressBar.maxValue) SceneManager.loadScene(new MainMenu());
	}
}
