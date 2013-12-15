package de.aima13.platform;

import java.awt.Font;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.aima13.platform.util.Rect;
import de.aima13.platform.util.Vector;
import org.lwjgl.Sys;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import de.aima13.platform.states.Game;
import de.aima13.platform.states.MainMenu;
import de.aima13.platform.states.Pause;

public class PlatformGame extends StateBasedGame {

	private AppGameContainer app;
	/** The fonts to draw to the screen */
	public TrueTypeFont fontHeader;

	/** Boolean flag on whether AntiAliasing is enabled or not */
	private boolean antiAlias = true;

	public PlatformGame() {
		super("One Platform");
	}

	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainMenu());
		addState(new Pause());
		addState(new Game());
		init();
	}

	public void init() {
		try {
			InputStream inputStream = ResourceLoader
					.getResourceAsStream("res/font/Ubuntu-R.ttf");

			Font ttfUbuntu = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			ttfUbuntu = ttfUbuntu.deriveFont(54f); // set font size
			ttfUbuntu = ttfUbuntu.deriveFont(Font.BOLD); // set font size
			fontHeader = new TrueTypeFont(ttfUbuntu, antiAlias);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			// System.exit(0);
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
			appgc.setShowFPS(false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(PlatformGame.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}
