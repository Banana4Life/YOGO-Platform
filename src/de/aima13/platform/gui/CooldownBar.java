package de.aima13.platform.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

public class CooldownBar extends Bar {
    
    public CooldownBar() {
        this(6);
    }
    
    public CooldownBar(int scaling) {
        super(scaling);
        try {
            image = new Image("res/images/otherEntitys/CooldownBar.png");
            image.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            // do nothing
        }
        value = 1f;
        Log.info("padding x:" + PADDING.x + " y:" + PADDING.y);
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        GameContainer c = getLevel().getContainer();
        final float y = c.getHeight() - PADDING.y - 4 * IMAGE_SCALE - HEIGHT;
        final float width = c.getWidth() - 2 * PADDING.x;
        
        image.draw(PADDING.x, y + IMAGE_SCALE, PADDING.x + IMAGE_SCALE, y + 2 * IMAGE_SCALE, 0, 1, 1, 2);
        image.draw(PADDING.x + IMAGE_SCALE, y, (PADDING.x - IMAGE_SCALE) + width, y + 3 * IMAGE_SCALE, 1, 0, 2, 3);
        image.draw((PADDING.x - IMAGE_SCALE) + width, y + IMAGE_SCALE, PADDING.x + width, y + 2 * IMAGE_SCALE, 2, 1, 3, 2);
        image.draw(PADDING.x + 1 * IMAGE_SCALE, y + IMAGE_SCALE, PADDING.x + (width - IMAGE_SCALE) * value, y + 2 * IMAGE_SCALE, 0, 3, 1, 4);
        image.draw(PADDING.x + (width - IMAGE_SCALE) * value, y + IMAGE_SCALE, PADDING.x + (width - IMAGE_SCALE), y + 2 * IMAGE_SCALE, 1, 3, 2, 4);
    }
}
