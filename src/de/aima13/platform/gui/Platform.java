package de.aima13.platform.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import de.aima13.platform.entity.Entity;
import de.aima13.platform.util.Vector;

public class Platform extends Entity {

	private static final Vector DEFAULT_VELOCITY = new Vector(5f, 0f);

	public Platform() {
		this(null, null, null);
	}

	public Platform(Vector size) {
		this(size, null, null);
	}

	public Platform(Vector size, Vector position) {
		this(size, position, null);
	}

	public Platform(Vector size, Vector position, Vector velocity) {
		this.size = size;
		this.position = position;
		this.velocity = velocity;
	}

	@Override
	public void onInit() {
		super.onInit();
		if (size == null) {
			size = new Vector(level.getWidth() / 4, level.getHeight() / 16);
		}
		if (position == null) {
			position = new Vector(level.getWidth() / 2 - size.x / 2,
					level.getHeight() - size.y);
		}
		if (velocity == null) {
			velocity = DEFAULT_VELOCITY;
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		// Get movements
		if (level.getInput().isKeyDown(Input.KEY_LEFT)) {
			// Move left
			if (position.x - velocity.x < 0) {
				position = new Vector(0, position.y);
			} else {
				position = position.sub(velocity);
			}
		} else if (level.getInput().isKeyDown(Input.KEY_RIGHT)) {
			// Move right
			if (position.x + velocity.x > level.getWidth() - size.x) {
				position = new Vector(level.getWidth() - size.x, position.y);
			} else {
				position = position.add(velocity);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		// Draw the platform
		// float x = position.x
		g.drawRect(position.x, position.y, level.getWidth() / 4,
				level.getHeight() / 16);
	}
}
