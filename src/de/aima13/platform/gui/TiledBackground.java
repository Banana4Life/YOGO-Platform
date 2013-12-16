package de.aima13.platform.gui;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import de.aima13.platform.entity.Tile;
import de.aima13.platform.util.Vector;

public class TiledBackground extends GuiEntity {
    
    protected Tile[][]    tiles;
    protected Random      generator;
    protected SpriteSheet imageSpriteSheet;
    protected Vector      imageSize;
    
    public TiledBackground(SpriteSheet sheet) {
        super();
        imageSpriteSheet = sheet;
        imageSize = new Vector(sheet.getWidth() / sheet.getVerticalCount(), sheet.getHeight() / sheet.getHorizontalCount());
        generator = new Random();
    }
    
    public void onInit() {
        if (imageSpriteSheet != null) {
            int width = getLevel().getWidth();
            int cols = (int) (width / imageSize.x) + 1;
            int rows = (int) (getLevel().getHeight() / imageSize.y) + 1;
            
            tiles = new Tile[rows + 1][cols];
            // add one row on top
            for (int i = -1; i < rows; i++) {
                for (int h = 0; h < cols; h++) {
                    Tile tile = new Tile(this, null, new Vector(h * imageSize.x, i * imageSize.y), i + 1, h);
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
        
        // Log.info("onLeaveWorld: row:" + tile.row + " col:" + tile.col);
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
