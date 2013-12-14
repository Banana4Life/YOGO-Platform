package de.aima13.platform.gui;

import org.newdawn.slick.Graphics;

import de.aima13.platform.GameLevel;
import de.aima13.platform.entity.Entity;
import de.aima13.platform.util.Vector;

public class Platform extends Entity {

	private static final float DEFAULT_SPEED = 5f;

	protected GameLevel level;
	protected float speed;

	public Platform(GameLevel level) {
		this(level, new Vector(level.getWidth() / 4, level.getHeight() / 16));
	}

	public Platform(GameLevel level, Vector size) {
		this(level, size, new Vector(level.getWidth() / 2 - size.x / 2, level.getHeight() - size.y));
	}

	public Platform(GameLevel level, Vector size, Vector position) {
		this(level, position, DEFAULT_SPEED);
	}

	public Platform(GameLevel level, Vector position, float speed) {
		this.level = level;
		this.position = position;
		this.speed = speed;
	}

	@Override
	public void render(Graphics g) {
		// Draw the platform
		g.drawRect(position.x, position.y, level.getWidth() / 4, level.getHeight() / 16);
	}
}
