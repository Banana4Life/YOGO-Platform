package de.aima13.platform.states;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.aima13.platform.GameLevel;
import de.aima13.platform.PlatformGame;
import de.aima13.platform.gui.Platform;

public class Game extends BasicGameState {

	public final static int ID = 1;
	private PlatformGame game; // stored for later use

	private GameLevel level;
	private Platform platform;

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		if (game instanceof PlatformGame) {
			this.game = (PlatformGame) game;
		} else {
			throw new SlickException("StateBaseGame isn't a PlatformGame!");
		}
		this.level = new GameLevel(container, container.getInput());
		platform = new Platform();
		this.level.addEntity(platform);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		this.level.render(container, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		this.level.update(container, delta);
	}

	@Override
	public int getID() {
		return ID;
	}

	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_ESCAPE:
			game.enterState(Menu.ID, new FadeOutTransition(Color.black),
					new FadeInTransition(Color.black));
			break;
		default:
			break;
		}
	}

}
