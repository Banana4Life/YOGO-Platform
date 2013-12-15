package de.aima13.platform.gui;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import de.aima13.platform.entity.TiledBackground;
import de.aima13.platform.util.Vector;
import org.newdawn.slick.Image;

public class Tile {

	protected TiledBackground background;
	protected Image tileImage;
	protected Vector position;
	protected Random randomGenerator;
	public final int row, col;

	public Tile(TiledBackground background, Image image, Vector position,
			int row, int col) {
		this.background = background;
		this.tileImage = image;
		this.position = position;
		this.row = row;
		this.col = col;
		this.randomGenerator = new Random();
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
	
	public void setImage(boolean firstLine) {
		int texRow = randomGenerator.nextInt(100);
		int texCol;
		if (texRow <= 98) {
			texRow = 0;
			texCol = randomGenerator.nextInt(3);
		} else {
			texRow = 1;
			texCol = 0;
		}
		tileImage = background.getSprite(texCol, texRow);
	}
	
	public void render(Graphics g) {
		tileImage.draw(this.position.x, this.position.y);
	}
}
