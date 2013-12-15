package de.aima13.platform.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Random;

/**
 * Created by Phillip on 15.12.13.
 */
public class BatterySpawner extends Entity
{
    private static final long DELAY = 1000 * 5;

    private long lastSpawned = 0;
    private Random random = new Random();

    @Override
    public void onInit()
    {
        this.setCollidable(false);
    }

    @Override
    public void update(int delta)
    {
        long current = System.currentTimeMillis();
        if (current - lastSpawned > DELAY)
        {
            lastSpawned = current;
            level.spawn(new Battery(random.nextFloat() * .2f)).move(this.position);
        }
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.yellow);
        g.fillRect(position.x, position.y, 20, 20);
    }
}
