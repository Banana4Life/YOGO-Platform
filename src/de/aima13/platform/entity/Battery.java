package de.aima13.platform.entity;

import de.aima13.platform.util.Face;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Battery extends Entity
{
    private float power;

    public Battery(float power) {
        this.power = power;
    }

    @Override
    public void update(int delta)
    {
        relativeMove(0, 1);
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.blue);
        g.fillRect(position.x, position.y, 30, 30);
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
