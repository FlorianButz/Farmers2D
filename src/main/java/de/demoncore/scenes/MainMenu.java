package de.demoncore.scenes;

import java.awt.Color;
import java.awt.Font;

import de.demoncore.game.SceneManager;
import de.demoncore.game.Settings;
import de.demoncore.game.Translation;
import de.demoncore.game.animator.AnimatorOnCompleteEvent;
import de.demoncore.game.animator.AnimatorUpdateEvent;
import de.demoncore.game.animator.Easing.EasingType;
import de.demoncore.game.animator.Vector3Animator;
import de.demoncore.gameObjects.ParticleSystem;
import de.demoncore.gameObjects.SettingsMenu;
import de.demoncore.gui.GUIAlignment;
import de.demoncore.gui.GUIButton;
import de.demoncore.gui.GUIButtonClickEvent;
import de.demoncore.gui.GUIMenu;
import de.demoncore.gui.GUIText;
import de.demoncore.main.Main;
import de.demoncore.utils.Logger;
import de.demoncore.utils.Resources;
import de.demoncore.utils.Vector3;
import de.farmers2d.scenes.FarmersBaseScene;

public class MainMenu extends BaseScene {
	
	private static MainMenu instance;
	public static boolean testSceneEnabled;
	
	public MainMenu() {
		instance = this;
	}

	@Override
	public void initializeScene() {
		super.initializeScene();
		
		Logger.logWarning("MainMenu l.33 Music Manager Disabled", this);
		// MusicManager.ForcePlayMusic(MusicManager.playing1Audio[0], true);
		
		ParticleSystem bgSys = new ParticleSystem(0, 0);	// Neues partikelsystem wird definiert
		bgSys.particleSpawnArea = new Vector3(1000, 1000);
		bgSys.emitLoop = true;
		bgSys.particleColorFirst = new Color(1, 1, 1, 0.085f);
		bgSys.particleColorSecond = new Color(1, 1, 1, 0.04f);
		bgSys.emitPause = 15;
		bgSys.emitChunk = 2;
		bgSys.initialParticleSize = 15;
		bgSys.particleLifetime += 2500;
		bgSys.initialParticleSpeedMax = new Vector3(0.25f, 0.25f);
		bgSys.initialParticleSpeedMin = new Vector3(-0.25f, -0.25f);
		bgSys.particleGravity = 0;
		bgSys.endParticleSize = 0;
		bgSys.Init();
		addObject(bgSys);	// Füge partikelsystem zum level hinzu
		
		GUIText title = new GUIText(0, 175, Translation.literal(Main.gameName), Resources.dialogFont.deriveFont(Font.PLAIN, 125F), Color.WHITE);	// Titel text
		addObject(title);
		
		Vector3Animator anim = new Vector3Animator(Vector3.one().multiply(150), Vector3.one().multiply(160), 5, EasingType.Linear); // Erstelle neue animation für den Titel text
		anim.setOnUpdate(new AnimatorUpdateEvent() {
			@Override
			public void onUpdate(Vector3 value) {
				super.onUpdate(value);
				title.SetFont(title.GetFont().deriveFont(value.x));	// Ändere die größe vom text in der animation
			}
		});
		anim.setOnComplete(new AnimatorOnCompleteEvent() {
			@Override
			public void onComplete() {
				super.onComplete();
				
				Vector3Animator anim2 = new Vector3Animator(Vector3.one().multiply(160), Vector3.one().multiply(150), 5, EasingType.Linear); // Erstelle zweite animation für den Titel text
				anim2.setOnUpdate(new AnimatorUpdateEvent() {
					@Override
					public void onUpdate(Vector3 value) {
						super.onUpdate(value);
						title.SetFont(title.GetFont().deriveFont(value.x));	// Ändere die größe vom text in der animation
					}
				});
				anim2.setOnComplete(new AnimatorOnCompleteEvent() {
					@Override
					public void onComplete() {
						super.onComplete();
						anim.play();	// Spiele animation 1 wenn animation 2 fertig ist
					}
				});
				anim2.play();	// Spiele animation 2 wenn animation 1 fertig ist
			}
		});
		anim.play(); // Spiele animation 1
		
		GUIButton quit = new GUIButton(0, 300, 800, 75, Translation.get("mainmenu.quit"), Resources.uiFont.deriveFont(35F),  new GUIButtonClickEvent() {
			@Override
			public void ButtonClick() {
				super.ButtonClick();
				System.exit(0);
			}
		});
		quit.alignment = GUIAlignment.Center;
		addObject(quit);		
		
		GUIButton play = new GUIButton(0, 0, 800, 75, Translation.get("mainmenu.play"), Resources.uiFont.deriveFont(35F),  new GUIButtonClickEvent() {
			@Override
			public void ButtonClick() {
				super.ButtonClick();
//				SceneManager.loadScene(new DefaultScene());
				SceneManager.loadScene(new FarmersBaseScene());
			}
		});
		play.alignment = GUIAlignment.Center;
		addObject(play);
		
		GUIButton testScene = new GUIButton(0, -100, 800, 75, Translation.get("TestScene"), Resources.uiFont.deriveFont(35F),  new GUIButtonClickEvent() {
			@Override
			public void ButtonClick() {
				super.ButtonClick();
				SceneManager.loadScene(new TestScene());
			}
		});
		testScene.alignment = GUIAlignment.Center;
		if(Settings.getSimpleDebugMode())
			addObject(testScene);
		
		GUIButton settings = new GUIButton(0, 200, 800, 75, Translation.get("mainmenu.settings"), Resources.uiFont.deriveFont(35F), new GUIButtonClickEvent() {
			@Override
			public void ButtonClick() {
				super.ButtonClick();
				GUIMenu s = new SettingsMenu();
				addObject(s);
				s.ShowMenu();
			}
		});
		settings.alignment = GUIAlignment.Center;
		addObject(settings);
	}

	public static MainMenu getInstance() {
		return instance;
	}
}
