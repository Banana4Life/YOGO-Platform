package de.aima13.platform.entity;

import org.newdawn.slick.*;
import org.newdawn.slick.util.Log;

import de.aima13.platform.gui.Points;
import de.aima13.platform.util.Box;
import de.aima13.platform.util.Face;
import de.aima13.platform.util.Vector;

import java.util.Random;

public class Creature extends Entity {
    private static final float   IMAGE_SCALE           = 4;
    private static final boolean SMOOTH_CAMERA         = true;
    
    private final Platform       platform;
    private final Points         points;
    
    protected SpriteSheet        charSprite;
    protected Animation          jumpingAnimation;
    protected int                currentJumpingYOffset = 0;
    protected int[]              jumpingYOffset        = { 3, 5, 6, 5, 3 };
    protected Animation          beltAnimation;
    
    protected boolean            inAir;
    protected boolean            prevFallingDown;
    private boolean              lost;
    private boolean              turned;
    private Random               random                = new Random();
    
    public Creature(Platform platform, Points points) {
        this.platform = platform;
        this.points = points;
        this.lost = false;
        this.turned = false;
    }
    
    @Override
    public void onInit() throws SlickException {
        setGravityScale(1.2f);
        
        move(100, 20);
        accelerate(2.5f, 0);
        
        float width = 16 * IMAGE_SCALE / 2;
        float height = 32 * IMAGE_SCALE;
        
        this.setBB(new Box(new Vector(width / 2, 0), new Vector(width, height)));
        
        this.charSprite = new SpriteSheet("res/images/character/CharacterSpriteSheet.png", 16, 32);
        this.charSprite.setFilter(Image.FILTER_NEAREST);
        this.beltAnimation = new Animation(new SpriteSheet("res/images/character/Belt.png", 4, 1), 100);
        this.jumpingAnimation = new Animation(this.charSprite, 0, 3, 5, 3, true, 30, false);
        jumpingAnimation.stop();
        
        this.inAir = this.prevFallingDown = true;
    }

    @Override
    public void preUpdate(int delta) {
        if (getPosition().y + getBB().getHeight() > platform.getPosition().y) {
            setVelocity(getVelocity().scale(0, 1));
            this.lost = true;
        }
    }
    
    @Override
    public void update(int delta) {
        this.jumpingAnimation.update(delta);
        this.beltAnimation.update(delta);
        
        if (isRising()) {
            if (!turned && Math.abs(getPosition().y - platform.getPosition().y) > getLevel().getContainer().getScreenHeight() / 2f) {
                turned = true;
                accelerate((2 + random.nextInt(3)) * (random.nextInt(2) == 1 ? 1 : -1), 0);
            }
        } else {
            this.turned = false;
        }
        
//        if (mayTurn()) {
//            setVelocity(getVelocity().scale(-1, 1));
//        }
        
        if (this.inAir) {
            this.prevFallingDown = (getVelocity().y > 0);
            this.platform.getLevel().setBackgroundVelocity(new Vector(0, getVelocity().y));
        }
        
        if (!this.SMOOTH_CAMERA && this.jumpingAnimation.getFrame() == 0) {
            this.platform.getLevel().setBackgroundVelocity(new Vector(0, getVelocity().y));
        }
        
        if (this.jumpingAnimation.getFrame() == this.jumpingAnimation.getFrameCount() - 1) {
            this.inAir = true;
            this.jumpingAnimation.stop();
            this.jumpingAnimation.setCurrentFrame(0);
            this.currentJumpingYOffset = 0;
            accelerate(0, -9.5f);
        }
    }
    
    public boolean isAbovePlatform() {
        Box bb = getAbsBB();
        Box platformBb = platform.getAbsBB();
        if (bb.getBase().x + bb.getWidth() >= platformBb.getBase().x && getPosition().x < platform.getPosition().x + platformBb.getWidth()) {
            return true;
        }
        return false;
    }
    
    public boolean isRising() {
        return getVelocity().y < 0;
    }
    
    public boolean mayTurn() {
        if (isAbovePlatform() && isRising()) {
            return Math.abs(this.platform.getPosition().y - this.getPosition().y) > 100;
        }
        return false;
    }
    
    public void onJump() {
        getLevel().getJumpSound().play();
        this.points.addPoints(500);
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        
        drawBB(g, Color.red);
        
        Vector pos = getPosition();
        Vector v = getVelocity();
        
        if (v.y >= 0 && isInAir()) {
            if (!prevFallingDown) {
                charSprite.getSprite(0, 2).draw(pos.x, pos.y, IMAGE_SCALE);
            } else {
                charSprite.getSprite(0, 1).draw(pos.x, pos.y, IMAGE_SCALE);
            }
        } else if (this.inAir) {
            charSprite.getSprite(0, 0).draw(pos.x, pos.y, IMAGE_SCALE);
        } else {
            jumpingAnimation.getCurrentFrame().draw(pos.x, pos.y + jumpingYOffset[jumpingAnimation.getFrame()] * IMAGE_SCALE, IMAGE_SCALE);
            currentJumpingYOffset = jumpingYOffset[jumpingAnimation.getFrame()];
        }
        this.beltAnimation.getCurrentFrame().draw(pos.x + 6 * IMAGE_SCALE, pos.y + this.currentJumpingYOffset * IMAGE_SCALE + 15 * IMAGE_SCALE, IMAGE_SCALE);
    }
    
    @Override
    public void onCollide(Entity target, Face collidedFace) {
        if (target instanceof Platform && ((Platform) target).isActive() && !isLost()) {
            move(getPosition().x, target.getPosition().y - this.getBB().getHeight() - 1);
            setVelocity(Vector.ZERO);
            
            this.inAir = false;
            this.jumpingAnimation.start();
            this.onJump();
        }
        // speed up / slow down on battery collision
        if (target instanceof Battery) {
            if (collidedFace == Face.TOP)
            {
                accelerate(0, 1);
            }
            else if (collidedFace == Face.BOTTOM)
            {
                accelerate(0, -1);
            }
        }
    }
    
    @Override
    public void onCollideWithBorder(Face collidedFace) {
        switch (collidedFace) {
            case BOTTOM:
                Log.error("Collided with the world border!");
                die();
                break;
            case LEFT:
            case RIGHT:
                setVelocity(getVelocity().scale(-1, 1));
                break;
        }
        
    }
    
    @Override
    public void onDeath() {
        this.getLevel().getGame().lose(this.points);
    }
    
    public boolean isInAir() {
        return inAir;
    }
    
    public boolean isLost() {
        return lost;
    }
}
