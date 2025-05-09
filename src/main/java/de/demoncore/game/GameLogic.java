package de.demoncore.game;

import java.util.Timer;
import java.util.TimerTask;

import de.demoncore.audio.AudioMaster;
import de.demoncore.gameObjects.PauseMenu;
import de.demoncore.gui.dialog.Dialog;

public class GameLogic {
	
	private static GameLogic _instance;
	
	private static boolean isGamePaused = false;
	
	public static GameLogic getInstance() {
		return _instance;
	}
	
	private Timer gameTimer;
	public float gameTime = 0;

	public double countedTps;
	public double tps;
	public double lastTime;
	public double accurateTps;
	public double accurateLastTime;
	
	public boolean doPauseGameOnDialog = true;
	
	public GameLogic() {
		_instance = this;
	}
	
	public void Start() {

	    Thread gameThread = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            Thread.currentThread().setName("gamelogic");
	            
	            // Set up timing variables
	            long lastFrameTime = System.nanoTime();
	            double nsPerTick = 1000000000.0 / 60; // Target 60 updates per second
	            
	            // FPS/TPS counter variables
	            long lastCounterTime = System.currentTimeMillis();
	            int frames = 0;
	            
	            while (!Thread.interrupted()) {
	                long currentTime = System.nanoTime();
	                double deltaTime = (currentTime - lastFrameTime) / 1000000000.0; // Convert to seconds
	                lastFrameTime = currentTime;
	                
	                frames++;
	                if (System.currentTimeMillis() - lastCounterTime > 1000) {
	                    tps = frames;
	                    frames = 0;
	                    lastCounterTime = System.currentTimeMillis();
	                }
	                
	                gameTime += deltaTime;
	                
	                boolean shouldPause = (doPauseGameOnDialog ? Dialog.isActiveDialog : false) 
	                                     || PauseMenu.isPauseMenuActive;
	                GameLogic.SetGamePaused(shouldPause);
	                
	                if (SceneManager.getActiveScene() != null && AudioMaster.isInitialized()) {
	                    AudioMaster.SetListener(SceneManager.getActiveScene().cameraPosition);
	                }
	                
	                try {
	                    SceneManager.UpdateScenes();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                
	                // Sleep to maintain frame rate and reduce CPU usage
	                try {
	                    long sleepTime = (long)((lastFrameTime + nsPerTick - System.nanoTime()) / 1000000);
	                    if (sleepTime > 0) {
	                        Thread.sleep(sleepTime);
	                    }
	                } catch (InterruptedException e) {
	                    Thread.currentThread().interrupt();
	                    break;
	                }
	            }
	        }
	    });
	    
	    gameThread.setDaemon(true); // Make thread exit when application closes
	    gameThread.start();
	}
	
	public static void SetGamePaused(boolean isPaused) {
		isGamePaused = isPaused;
		//AudioMaster.SetAllPaused(isPaused);
	}
	
	public static boolean IsGamePaused() {
		return isGamePaused;
	}
	
	public float getGameTime() {
		return gameTime;
	}
	
	public double GetTps() {
		return accurateTps;
	}
	
	public double GetAverageTps() {
		return tps;
	}
}
