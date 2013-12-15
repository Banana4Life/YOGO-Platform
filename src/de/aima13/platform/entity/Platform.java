package de.aima13.platform.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import de.aima13.platform.util.Vector;

public class Platform extends Entity {

	private static final Vector DEFAULT_ACCELERATION = new Vector(1.0f, 0f);
	private static final float DECELERATE_FACTOR = 1.5f;

	public Platform() {
		this(null, null, null);
	}

	public Platform(Vector size) {
		this(size, null, null);
	}

	public Platform(Vector size, Vector position) {
		this(size, position, null);
	}

	public Platform(Vector size, Vector position, Vector acceleration) {
		super();
		this.size = size;
		this.position = position;
		this.acceleration = acceleration;
	}

	@Override
	public void onInit() {
		super.onInit();
		if (size == null) {
			size = new Vector(level.getWidth() / 4, level.getHeight() / 16);
		}
		if (position == null) {
			position = new Vector(level.getWidth() / 2 - size.x / 2,
					level.getHeight() - size.y - 100);
		}
		if (acceleration == null) {
			acceleration = DEFAULT_ACCELERATION;
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		// Get movements
		if (level.getInput().isKeyDown(Input.KEY_LEFT)) {
			// Move left
			if (velocity.x > 0) {
				// Reset
				velocity = new Vector(0, acceleration.y);
			}
			velocity = velocity.sub(acceleration);
			if (position.x + velocity.x < 0) {
				position = new Vector(0, position.y);
			} else {
				position = position.add(velocity);
			}
		} else if (level.getInput().isKeyDown(Input.KEY_RIGHT)) {
			// Move right
			if (velocity.x < 0) {
				// Reset
				velocity = new Vector(0, acceleration.y);
			}
			velocity = velocity.add(acceleration);
			if (position.x + velocity.x > level.getWidth() - size.x) {
				position = new Vector(level.getWidth() - size.x, position.y);
				// Reset
				velocity = new Vector(0, acceleration.y);
			} else {
				position = position.add(velocity);
			}
		} else {
			if (velocity.x > 0) {
				if (velocity.x + DECELERATE_FACTOR * acceleration.x < 0) {
					// Reset
					velocity = new Vector(0, acceleration.y);
				} else {
					velocity = velocity.sub(DECELERATE_FACTOR * acceleration.x,
							acceleration.y);
				}
			} else if (velocity.x < 0) {
				if (velocity.x + DECELERATE_FACTOR * acceleration.x > 0) {
					// Reset
					velocity = new Vector(0, acceleration.y);
				} else {
					velocity = velocity.add(DECELERATE_FACTOR * acceleration.x,
							acceleration.y);
				}
			}
			// Reset
			velocity = new Vector(0, 0);
		}
	}

	@Override
	public void render(Graphics g) {
		// Draw the platform
		g.setColor(Color.cyan);
		g.fillRect(position.x, position.y, level.getWidth() / 4,
				level.getHeight() / 24);
	}
}
