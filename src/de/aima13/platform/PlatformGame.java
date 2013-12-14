package de.aima13.platform;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.aima13.platform.states.Game;
import de.aima13.platform.states.Menu;

public class PlatformGame extends StateBasedGame {

	private AppGameContainer app;

	public PlatformGame() {
		super("One Platform");
	}

	

	public void initStatesList(GameContainer container) throws SlickException {
		addState(new Menu());
		addState(new Game());
	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			//System.exit(0);
		}
		if (key == Input.KEY_F1) {
			if (app != null) {
				try {
					int fullhd = 1;
					app.setDisplayMode(fullhd * 1920, fullhd * 1080, false);
					app.reinit();
				} catch (Exception e) {
					Log.error(e);
				}
			}
		}
	}
	
	public AppGameContainer getApp() {
		return app;
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new PlatformGame());
			appgc.setDisplayMode(640, 480, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(PlatformGame.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}
