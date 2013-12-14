package de.aima13.platform.entity;

import de.aima13.platform.GameLevel;
import de.aima13.platform.util.Vector;
import org.newdawn.slick.Graphics;

public abstract class Entity {
	protected Vector position;
	protected Vector size;
	protected Vector velocity;
	protected Vector acceleration;
	protected GameLevel level;

	protected Entity() {
		this.position = new Vector(0, 0);
		this.size = new Vector(0, 0);
		this.velocity = new Vector(0, 0);
		this.acceleration = new Vector(0, 0);
	}

	public Vector getPosition() {
		return position;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public Vector getAcceleration() {
		return acceleration;
	}

	public final void init(GameLevel level) {
		this.level = level;
		onInit();
	}

	public void onInit() {
	}

	public void update(int delta) {
	}

	public void render(Graphics g) {
	}
}
