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

public class Loose extends Menu {

	public final static int ID = 5;

	public Loose() {
		super(ID);
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		setHighlightWidth(10);
		setHighlightScale(7);
		addHighlightEntry(new Vector(50, 100 - 2), new OnHighlightSelectListener() {

			@Override
			public void onSelect(StateBasedGame game) {
				game.enterState(Game.ID, new FadeOutTransition(Color.black),
						new FadeInTransition(Color.black));
			}
		});
		addHighlightEntry(new Vector(50, 125 - 2), new OnHighlightSelectListener() {

			@Override
			public void onSelect(StateBasedGame game) throws SlickException {
				((Game) game.getState(Game.ID)).resetState();
				game.enterState(MainMenu.ID,
						new FadeOutTransition(Color.black),
						new FadeInTransition(Color.black));
			}

		});
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		g.setColor(Color.white);

		int textWidth = this.game.fontHeader.getWidth("You lost you ONLY platform!");
		this.game.fontHeader.drawString(game.getContainer().getWidth() / 2
				- textWidth / 2, 10, "You lost you ONLY platform!", Color.white);

		// g.setColor(Color.red);
		this.game.fontDefault.drawString(50, 100, "Start a new game");
		this.game.fontDefault.drawString(50, 125, "Exit to Main Menu");

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

	}

	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		switch (key) {
		case Input.KEY_ESCAPE:
			game.enterState(Game.ID, new FadeOutTransition(Color.black),
					new FadeInTransition(Color.black));
			break;
		case Input.KEY_2:
			// TODO: Implement later
			break;
		case Input.KEY_3:
			System.exit(0);
			break;
		default:
			break;
		}
	}

}
