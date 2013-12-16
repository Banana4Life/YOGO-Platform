package de.aima13.platform.entity;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;

import de.aima13.platform.gui.GuiEntity;
import de.aima13.platform.gui.Tile;
import de.aima13.platform.util.Vector;

public class TiledBackground extends GuiEntity {

	protected Tile[][] tiles;
	protected Random generator;
	protected SpriteSheet imageSpriteSheet;
	protected Image[] imageSet;
	protected Vector imageSize;

	public TiledBackground(SpriteSheet sheet) {
		super();
		imageSpriteSheet = sheet;
		imageSize = new Vector(sheet.getWidth() / sheet.getVerticalCount(), sheet.getHeight() / sheet.getHorizontalCount());
		generator = new Random();
	}

	public TiledBackground(Image[] set, Vector size) {
		super();
		imageSet = set;
		imageSize = size;
		generator = new Random();
	}

	public void onInit() {
		if (imageSet != null || imageSpriteSheet != null) {
			int width = getLevel().getWidth();
			int notpr = (int) (width / imageSize.x) + 1; // number
															// of
															// tiles
															// per
															// row
			Log.info("" + imageSize.x + " " + imageSize.y);
			int nor = (int) (getLevel().getHeight() / imageSize.y) + 1; // number
																		// of
																		// rows
			tiles = new Tile[nor + 1][notpr];
			// add one row in top
			for (int i = -1; i < nor; i++) {
				for (int h = 0; h < notpr; h++) {
					Tile tile = new Tile(this, null,
							new Vector(h * imageSize.x, i * imageSize.y),
							i + 1, h);
					tiles[i + 1][h] = tile;
					if (i == -1) {
						tile.setImage(true);
					} else {
						tile.setImage(false);
					}
				}
			}
		}
	}

	public void update(int delta) {

	}

	public void render(Graphics g) {
		for (int i = 0; i < tiles.length; i++) {
			for (int h = 0; h < tiles[i].length; h++) {
				tiles[i][h].render(g);
			}
		}
	}

	public void onLeaveWorld(Tile tile) {
//		 Log.info("onLeaveWorld: row:" + tile.row + " col:" + tile.col);
		tile.moveTo(new Vector(tile.getPosition().x, -imageSize.y));
		tile.setImage(false);
	}
	
	public Tile getTile(int x, int y) {
		return tiles[y][x];
	}
	public Image getSprite(int x, int y) {
		return imageSpriteSheet.getSprite(x, y);
	}
}
