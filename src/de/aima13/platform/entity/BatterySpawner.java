package de.aima13.platform.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Random;

public class BatterySpawner extends Entity
{
    private static final long DELAY = 1000 * 5;

    private long lastSpawned = 0;
    private Random random = new Random();

    @Override
    public void onInit()
    {
        this.setGravityScale(0);
    }

    @Override
    public void update(int delta)
    {
        long current = System.currentTimeMillis();
        if (current - lastSpawned > DELAY)
        {
            lastSpawned = current;
            level.spawn(new Battery(random.nextFloat() * .3f - .1f)).move(getPosition());
        }
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.yellow);
        g.fillRect(getPosition().x - 10, getPosition().y - 10, 20, 20);
    }
}
