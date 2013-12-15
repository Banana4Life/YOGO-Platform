package de.aima13.platform.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.aima13.platform.PlatformGame;
import de.aima13.platform.gui.OnHighlightSelectListener;
import de.aima13.platform.util.Vector;

public class Credits extends BasicGameState {

	public final static int ID = 4;
	protected PlatformGame game; // stored for later use

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		if (game instanceof PlatformGame) {
			this.game = (PlatformGame) game;
		} else {
			throw new SlickException("StateBaseGame isn't a PlatformGame!");
		}

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.white);

		int textWidth = this.game.fontHeader.getWidth("Credits");
		this.game.fontHeader.drawString(game.getContainer().getWidth() / 2
				- textWidth / 2, 10, "Credits", Color.white);

		// g.setColor(Color.red);
		this.game.fontDefault.drawString(50, 100, "Resume Game");
		this.game.fontDefault.drawString(50, 125, "Exit to Main Menu");
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

	}

	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		switch (key) {
		case Input.KEY_ESCAPE:
			game.enterState(MainMenu.ID, new FadeOutTransition(Color.black),
					new FadeInTransition(Color.black));
			break;
		default:
			break;
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
