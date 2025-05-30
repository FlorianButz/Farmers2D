package de.demoncore.gameObjects;

import de.demoncore.game.GameLogic;
import de.demoncore.game.GameObject;

public class BeweglichesRechteck extends GameObject {
	
	public byte richtung;
	public int schritteInGleicherRichtung;

	public BeweglichesRechteck(int posX, int posY, int breite, int hoehe) {
		super(posX, posY, breite, hoehe);
		schritteInGleicherRichtung = 0;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	
		automatischeKreisbewegung();
	}
	
	public void automatischeKreisbewegung() {

		if(GameLogic.IsGamePaused()) return;
		
		if(richtung == 0) {
			position.x += 1;
		} else if (richtung == 1) {
			position.y += 1;
		} else if (richtung == 2) {
			position.x -= 1;
		} else if (richtung == 3) {
			position.y -= 1;
		}
		if (schritteInGleicherRichtung > 75) {
			richtung += 1;
			if (richtung > 3) {
				richtung = 0;
			}
			schritteInGleicherRichtung = 0;
		} else {
			schritteInGleicherRichtung += 1;
		}
	}
}
