package de.aima13.platform.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import de.aima13.platform.GameLevel;
import de.aima13.platform.util.Box;
import de.aima13.platform.util.Face;
import de.aima13.platform.util.Vector;

public class KillingFire extends Entity {
    
    private Animation fireAnimation;
    
    private int       height;
    private int       scale;
    private Vector    fireDimensions;
    private Vector    fireOffset;
    
    public KillingFire() {
        this(1);
    }
    
    public KillingFire(int startHeight) {
        this(startHeight, 4);
    }
    
    public KillingFire(int startHeight, int scale) {
        super();
        this.height = startHeight;
        this.scale = scale;
        
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    @Override
    public void onInit() throws SlickException {
        this.setGravityScale(0);
        GameLevel lvl = getLevel();
        this.setBB(new Box(lvl.getWidth() / 4, lvl.getHeight() / 16));
        move(lvl.getWidth() / 2 - getBB().getWidth() / 2, lvl.getHeight() - getBB().getHeight() - 100);
        
        // load Sprites and Animations //
        this.fireAnimation = new Animation(new SpriteSheet("res/images/platform/Fire.png", 3, 3), 100);
        this.fireAnimation.start();
        int fireWidth = this.fireAnimation.getImage(0).getWidth();
        int fireHeight = this.fireAnimation.getImage(0).getHeight();
        fireDimensions = new Vector(fireWidth, fireHeight);
        fireOffset = new Vector(0, 0);
    }
    
    @Override
    public void update(int delta) {
        super.update(delta);
        this.fireAnimation.update(delta);
    }
    
    @Override
    public void render(Graphics g) {
        int columns = (int) ((this.getLevel().getWidth() / fireDimensions.x) + 1);
        for (int col = 0; col < columns; col++) {
            this.fireAnimation.getCurrentFrame().getFlippedCopy(true, true).draw(0 + col * fireDimensions.x * scale + col * fireOffset.x * scale, this.getLevel().getHeight() - fireDimensions.y * scale, scale);
        }
    }
    
    @Override
    public void onCollide(Entity target, Face collidedFace) {
    }
    
    @Override
    public void onCollideWithBorder(Face collidedFace) {
        switch (collidedFace) {
            case LEFT:
                break;
            case RIGHT:
                break;
            default:
                System.err.println("Should not happen!");
                new IllegalStateException().printStackTrace(System.err);
        }
    }
}
