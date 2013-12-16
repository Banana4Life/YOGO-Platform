package de.aima13.platform.states;

import de.aima13.platform.entity.BatterySpawner;
import de.aima13.platform.gui.CooldownBar;
import de.aima13.platform.gui.PowerBar;
import de.aima13.platform.gui.TiledBackground;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.aima13.platform.GameLevel;
import de.aima13.platform.PlatformGame;
import de.aima13.platform.entity.Creature;
import de.aima13.platform.entity.Platform;
import de.aima13.platform.util.Vector;

public class Game extends BasicGameState {

	public final static int ID = 1;
	private PlatformGame game; // stored for later use
	private GameContainer container;
	private GameLevel level;

	private Platform platform;

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		if (game instanceof PlatformGame) {
			this.game = (PlatformGame) game;
		} else {
			throw new SlickException("StateBaseGame isn't a PlatformGame!");
		}
		this.container = container;
		resetState();
	}

	public void resetState() throws SlickException {
		this.level = game.getLevel();
		level.reset();


		PowerBar powerBar = level.spawn(new PowerBar());
		CooldownBar cooldownBar = level.spawn(new CooldownBar());
		platform = level.spawn(new Platform(powerBar, 10));

		Creature creature = level.spawn(new Creature(platform));

		BatterySpawner spawner = level.spawn(new BatterySpawner());
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		this.level.render(g);
		if (this.game.shaderColorActive) {
			this.game.shaderColor.draw(0, 0, this.game.getContainer()
					.getWidth(), this.game.getContainer().getHeight());
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		this.level.update(delta);
	}

	@Override
	public int getID() {
		return ID;
	}

	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_ESCAPE:
			game.enterState(Pause.ID, new FadeOutTransition(Color.black),
					new FadeInTransition(Color.black));
			break;
		default:
			break;
		}
	}

}
