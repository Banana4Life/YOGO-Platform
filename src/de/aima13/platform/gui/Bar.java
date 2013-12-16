package de.aima13.platform.gui;

import de.aima13.platform.util.Vector;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Bar extends GuiEntity {
    protected static int    IMAGE_SCALE;
    protected static Vector PADDING;
    protected static float  HEIGHT;
    
    protected Image         image;
    
    protected float         value = 1.0f;
    
    public Bar() {
        this(8);
    }
    
    public Bar(int scaling) {
        IMAGE_SCALE = scaling;
        PADDING = new Vector(2 * IMAGE_SCALE, 2 * IMAGE_SCALE);
        HEIGHT = PADDING.y;
    }
    
    public float getValue() {
        return value;
    }
    
    public void setValue(float power) {
        this.value = Math.max(0f, Math.min(1f, power));
    }
    
    public void increaseValue(float add) {
        setValue(getValue() + add);
    }
    
    public void decreaseValue(float sub) {
        setValue(getValue() - sub);
    }
    
    @Override
    public void render(Graphics g) {
        // final float y = level.getContainer().getHeight() - PADDING.y -
        // HEIGHT;
        // final float width = level.getContainer().getWidth() - 2 * PADDING.x;
        //
        // g.setColor(Color.red);
        // g.fillRect(PADDING.x, y, width, HEIGHT);
        //
        // g.setColor(Color.cyan);
        // g.fillRect(PADDING.x, y, width * value, HEIGHT);
    }
}
