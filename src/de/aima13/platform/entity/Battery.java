package de.aima13.platform.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.aima13.platform.util.Box;
import de.aima13.platform.util.Face;
import de.aima13.platform.util.Vector;

public class Battery extends Entity {
    private static final int IMAGE_SCALE = 3;
    
    private float            power;
    private Image            batteryImage;
    private Image            batteryNegativeImage;
    
    public Battery(float power) {
        this.power = power;
        
        try {
            batteryImage = new Image("res/images/otherEntitys/Battery.png");
            batteryImage.setFilter(Image.FILTER_NEAREST);
            batteryNegativeImage = new Image("res/images/otherEntitys/BatteryNegative.png");
            batteryNegativeImage.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            // do nothing
        }
    }
    
    @Override
    public void onInit() {
        setBB(new Box(batteryImage.getWidth() * IMAGE_SCALE, batteryImage.getHeight() * IMAGE_SCALE));
        setVelocity(new Vector(0, 2));
        setAcceleration(Vector.ZERO);
    }
    
    @Override
    public void render(Graphics g) {
        if (power >= 0) {
            batteryImage.draw(getPosition().x, getPosition().y, IMAGE_SCALE);
        } else {
            batteryNegativeImage.draw(getPosition().x, getPosition().y, IMAGE_SCALE);
        }
    }
    
    @Override
    public void onCollide(Entity target, Face collidedFace) {
        if (target instanceof Platform && ((Platform) target).isActive()) {
            ((Platform) target).getPowerbar().increaseValue(this.getPower());
            die();
        } else if (target instanceof Creature) {
            die();
        }
        super.onCollide(target, collidedFace);
    }
    
    public float getPower() {
        return power;
    }
}
