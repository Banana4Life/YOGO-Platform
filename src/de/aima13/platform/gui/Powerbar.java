package de.aima13.platform.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Powerbar extends Bar {


	public Powerbar() {
		value = 1.0f;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		final float y = level.getContainer().getHeight() - PADDING.y - HEIGHT;
		final float width = level.getContainer().getWidth() - 2 * PADDING.x;

		g.setColor(Color.red);
		g.fillRect(PADDING.x, y, width, HEIGHT);

		g.setColor(Color.cyan);
		g.fillRect(PADDING.x, y, width * value, HEIGHT);
	}
}
