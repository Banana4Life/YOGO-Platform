package de.aima13.platform;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PlatformGame extends BasicGame
{

	private AppGameContainer app;
    private GameLevel level;

    public PlatformGame()
    {
        super("One Wheel");
    }

	public void init(GameContainer container) throws SlickException
    {
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
