package de.aima13.platform.gui;

import de.aima13.platform.util.Vector;
import org.newdawn.slick.Graphics;

public class Bar extends GuiEntity {
	protected static final Vector PADDING = new Vector(10, 10);
	protected static final float HEIGHT = PADDING.y;

	protected float value = 1.0f;

	public float getValue() {
		return value;
	}

	public void setValue(float power) {
		this.value = Math.max(0f, Math.min(1f, power));
	}

	public void increaseValue(float add) {
		setValue(getValue() + add);
	}

	public void decreaseValue(float sub) {
		setValue(getValue() - sub);
	}

	@Override
	public void render(Graphics g) {
		// final float y = level.getContainer().getHeight() - PADDING.y -
		// HEIGHT;
		// final float width = level.getContainer().getWidth() - 2 * PADDING.x;
		//
		// g.setColor(Color.red);
		// g.fillRect(PADDING.x, y, width, HEIGHT);
		//
		// g.setColor(Color.cyan);
		// g.fillRect(PADDING.x, y, width * value, HEIGHT);
	}
}
