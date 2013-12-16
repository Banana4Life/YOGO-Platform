package de.aima13.platform.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PowerBar extends Bar {
	protected static final int IMAGE_SCALE = 4;

	public PowerBar() {
		try {
			image = new Image("res/images/otherEntitys/PowerBar.png");
			image.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			// do nothing
		}
		value = 1f;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
        GameContainer c = getLevel().getContainer();
		final float y = c.getHeight() - PADDING.y - HEIGHT;
		final float width = c.getWidth() - 2 * PADDING.x;

		image.draw(PADDING.x, y, PADDING.x + 2 * IMAGE_SCALE, y + 3 * IMAGE_SCALE, 0, 0, 2, 3);
		image.draw(PADDING.x + 2 * IMAGE_SCALE, y, (PADDING.x - IMAGE_SCALE) + width / 2, y + 3 * IMAGE_SCALE, 2, 0, 3, 3);
		image.draw((PADDING.x - IMAGE_SCALE) + width / 2, y, (PADDING.x - IMAGE_SCALE) + width, y + 3 * IMAGE_SCALE, 3, 0, 4, 3);
		image.draw((PADDING.x - IMAGE_SCALE) + width, y, PADDING.x + width, y + 3 * IMAGE_SCALE, 4, 0, 5, 3);
		image.draw(PADDING.x + 2 * IMAGE_SCALE, y + IMAGE_SCALE, PADDING.x + (width - IMAGE_SCALE) * value, y + 2 * IMAGE_SCALE, 0, 3, 1, 4);
		image.draw(PADDING.x + (width - IMAGE_SCALE) * value, y + IMAGE_SCALE, PADDING.x + (width - IMAGE_SCALE), y + 2 * IMAGE_SCALE, 1, 3, 2, 4);
	}
}
