package de.aima13.platform;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PlatformGame extends BasicGame {

	private AppGameContainer app;
	private GameLevel level;

	public PlatformGame() {
		super("One Platform");
	}

	public void init(GameContainer container) throws SlickException {
		if (container instanceof AppGameContainer) {
			app = (AppGameContainer) container;
		}
		this.level = new GameLevel(container);
	}

	public void update(GameContainer container, int delta) {
		this.level.update(container, delta);
	}

	public void render(GameContainer container, Graphics g) {
		this.level.render(container, g);
	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			System.exit(0);
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
