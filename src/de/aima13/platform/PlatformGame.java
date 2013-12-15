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

import de.aima13.platform.states.Credits;
import de.aima13.platform.states.Game;
import de.aima13.platform.states.Loose;
import de.aima13.platform.states.MainMenu;
import de.aima13.platform.states.Pause;

public class PlatformGame extends StateBasedGame {

	private AppGameContainer app;
	/** The fonts to draw to the screen */
	public TrueTypeFont fontDefault;
	public TrueTypeFont fontHeader;

	private GameContainer container;
	private GameLevel level;

	public PlatformGame() {
		super("#YOGO Platform");
	}

	public void initStatesList(GameContainer container) throws SlickException {
		this.container = container;
		level = new GameLevel(this, container.getInput());
		level.init();
		addState(new MainMenu());
		addState(new Pause());
		addState(new Game());
		addState(new Credits());
		addState(new Loose());
		init();
	}

	public TrueTypeFont loadFont(String res) {
		return loadFont(res, Font.PLAIN);
	}

	public TrueTypeFont loadFont(String res, float size) {
		return loadFont(res, size, Font.PLAIN);
	}

	public TrueTypeFont loadFont(String res, int style) {
		return loadFont(res, 18f, style);
	}

	public TrueTypeFont loadFont(String res, float size, int style) {
		return loadFont(res, size, style, true);
	}

	public TrueTypeFont loadFont(String res, float size, int style,
			boolean antiAlias) {
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream(res);

			Font ttf = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			ttf = ttf.deriveFont(size); // set font size
			ttf = ttf.deriveFont(style); // set font style
			return new TrueTypeFont(ttf, antiAlias);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void init() throws SlickException {
		fontDefault = loadFont("res/font/minecraft.ttf");
		fontHeader = loadFont("res/font/minecraft.ttf", 54f);
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

	public GameLevel getLevel() {
		return level;
	}
}
