package de.aima13.platform.entity;

import de.aima13.platform.util.Box;
import de.aima13.platform.util.Face;
import de.aima13.platform.util.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Random;

public class BatterySpawner extends Entity
{
    private static final long DELAY = 1000 * 1;

    private long lastSpawned = 0;
    private Random random = new Random();

    @Override
    public void onInit()
    {
        move(1, -10);
        this.setGravityScale(0);
        this.setBB(new Box(40, 1));
        this.setVelocity(new Vector(3, 0));
    }

    @Override
    public void update(int delta)
    {
        long current = System.currentTimeMillis();
        if (current - lastSpawned > DELAY)
        {
            lastSpawned = current;
            getLevel().spawn(new Battery(random.nextFloat() * .3f - .1f)).move(getPosition().add(getBB().getWidth() / 2, 0));
        }
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.yellow);
        g.fillRect(getPosition().x - 10, getPosition().y - 10, 20, 20);
    }

    @Override
    public void onCollideWithBorder(Face collidedFace)
    {
        switch (collidedFace)
        {
            case LEFT:
                move(getPosition().scale(0, 1));
                setVelocity(getVelocity().scale(-1));
                break;
            case RIGHT:
                move(getLevel().getWidth() - getBB().getWidth() - 1, getPosition().y);
                setVelocity(getVelocity().scale(-1));
                break;
        }
    }
}
