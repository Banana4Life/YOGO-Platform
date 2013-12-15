package de.aima13.platform.entity;

import de.aima13.platform.util.Face;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Battery extends Entity
{
	private static final int IMAGE_SCALE = 4;
	
    private float power;
    private Image batteryImage;
    private Image batteryNegativeImage;

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
    public void update(int delta)
    {
        relativeMove(0, 1);
    }

    @Override
    public void render(Graphics g)
    {
    	if (power >= 0) {
    		batteryImage.draw(this.position.x, this.position.y, IMAGE_SCALE);
    	} else {
    		batteryNegativeImage.draw(this.position.x, this.position.y, IMAGE_SCALE);
    	}
    }

    @Override
    public void onCollide(Entity current, Face collidedFace)
    {
        if (current instanceof Platform)
        {
            ((Platform)current).getPowerbar().increaseValue(this.getPower());
            die();
        }
        else if (current instanceof Creature)
        {
            die();
        }
        super.onCollide(current, collidedFace);
    }

    public float getPower()
    {
        return power;
    }

    @Override
    public void onDeath()
    {
        System.out.println("Sad battery :(");
    }
}
