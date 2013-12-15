package de.aima13.platform.entity;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import de.aima13.platform.gui.GuiEntity;
import de.aima13.platform.gui.Tile;
import de.aima13.platform.util.Vector;

public class TiledBackground extends GuiEntity {

	protected Tile[][] tiles;
	protected Random generator;
	protected SpriteSheet imageSheet;
	protected Image[] imageSet;
	protected Vector imageSize;

	public TiledBackground(SpriteSheet sheet, Vector size) {
		super();
		imageSheet = sheet;
		imageSize = size;
		generator = new Random();
	}

	public TiledBackground(Image[] set, Vector size) {
		super();
		imageSet = set;
		imageSize = size;
		generator = new Random();
	}

	public void onInit() {
		if (imageSet.length > 0) {
			int width = getLevel().getWidth();
			int notpr = (int) (width / imageSize.x) + 1; // number
															// of
															// tiles
															// per
															// row
			int nor = (int) (getLevel().getHeight() / imageSize.y) + 1; // number
																		// of
																		// rows
			tiles = new Tile[nor + 1][notpr];
			// add one row in top
			for (int i = -1; i < nor; i++) {
				for (int h = 0; h < notpr; h++) {
					// get random image
					int randomImage = generator.nextInt(imageSet.length);
					Tile tile = new Tile(this, imageSet[randomImage],
							new Vector(h * imageSize.x, i * imageSize.y),
							i + 1, h);
					tiles[i + 1][h] = tile;
				}
			}
		}
	}

	public void update(int delta) {

	}

	public void render(Graphics g) {
		for (int i = 0; i < tiles.length; i++) {
			for (int h = 0; h < tiles[i].length; h++) {
				Tile t = tiles[i][h];
				g.drawImage(t.getImage(), t.getPosition().x, t.getPosition().y);
			}
		}
	}

	public void onLeaveWorld(Tile tile) {
		// Log.info("onLeaveWorld: row:" + tile.row + " col:" + tile.col);
		tile.moveTo(new Vector(tile.getPosition().x, -imageSize.y));
	}
}
