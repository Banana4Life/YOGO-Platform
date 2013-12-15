package de.aima13.platform.gui;

import org.newdawn.slick.Image;

import de.aima13.platform.util.Vector;

public class Tile {

	protected Image tileImage;
	protected Vector position;

	public Tile(Image image, Vector position) {
		this.tileImage = image;
		this.position = position;
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector v) {
		setPosition(v.x, v.y);
	}

	public void setPosition(float x, float y) {
		position = position.add(x, y);
	}
}
