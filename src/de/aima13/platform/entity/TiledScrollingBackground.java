package de.aima13.platform.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import de.aima13.platform.util.Vector;

public class TiledScrollingBackground extends TiledBackground {

	protected Vector velocity;
	
	protected TiledScrollingBackground(SpriteSheet sheet, Vector size, Vector velocity) {
		super(sheet, size);
		this.velocity = velocity;
	}

	public void onInit() {

	}

	public void update(int delta) {
		
	}

	public void render(Graphics g) {
	}
}
