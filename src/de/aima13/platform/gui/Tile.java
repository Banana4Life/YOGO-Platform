package de.aima13.platform.gui;

import org.newdawn.slick.Image;

import de.aima13.platform.entity.TiledBackground;
import de.aima13.platform.util.Vector;

public class Tile {

	protected TiledBackground background;
	protected Image tileImage;
	protected Vector position;
	public final int row, col;

	public Tile(TiledBackground background, Image image, Vector position,
			int row, int col) {
		this.background = background;
		this.tileImage = image;
		this.position = position;
		this.row = row;
		this.col = col;
	}

	public Vector getPosition() {
		return position;
	}

	public Image getImage() {
		return tileImage;
	}

	public void move(Vector v) {
		move(v.x, v.y);
	}

	public void move(float x, float y) {
		position = position.add(x, y);
		if (((position.x + tileImage.getWidth()) < 0)
				|| (position.x > background.getLevel().getWidth())
				|| (position.y + tileImage.getHeight() < 0)
				|| (position.y > background.getLevel().getHeight())) {
			background.onLeaveWorld(this);
		}
	}

	public void moveTo(Vector v) {
		position = v;
	}

	public void moveTo(float x, float y) {
		moveTo(new Vector(x, y));
	}
}
