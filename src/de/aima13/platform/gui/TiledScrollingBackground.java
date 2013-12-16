package de.aima13.platform.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;

import de.aima13.platform.util.Vector;

public class TiledScrollingBackground extends TiledBackground {
    
    protected Vector velocity;
    
    public TiledScrollingBackground(SpriteSheet sheet, Vector velocity) {
        super(sheet);
        this.velocity = velocity;
    }
    
    public void onInit() {
        super.onInit();
    }
    
    public void update(int delta) {
        super.update(delta);
        for (int i = 0; i < tiles.length; i++) {
            for (int h = 0; h < tiles[i].length; h++) {
                tiles[i][h].move(velocity);
            }
        }
    }
    
    public void render(Graphics g) {
        super.render(g);
    }
}
