package de.aima13.platform.entity;

import de.aima13.platform.GameLevel;
import de.aima13.platform.util.Vector;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Entity {
	protected Vector position;
	protected Vector velocity;

	protected Entity() {
		this.position = new Vector(0, 0);
		this.velocity = new Vector(0, 0);
	}

	public Vector getPosition() {
		return position;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void init(GameContainer container, GameLevel level) {
	}

	public void update(GameContainer container, int delta) {
	}

	public void render(GameContainer container, Graphics g) {
	}
}
