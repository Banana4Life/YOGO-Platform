package de.aima13.platform.entity;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import de.aima13.platform.gui.Tile;

public class TiledBackground extends Entity {

	protected ArrayList<Tile> tiles;
	protected Random generator;
	protected Image[] imageSet;

	protected TiledBackground(Image[] images) {
		super();
		tiles = new ArrayList<Tile>();
		imageSet = images;
	}

	public void onInit() {
//for (int i = 0; )
	}

	public void update(int delta) {

	}

	public void render(Graphics g) {

	}
	
	public void onLeaveWorld(Tile tile) {
		tiles.remove(tile);
	}
}
