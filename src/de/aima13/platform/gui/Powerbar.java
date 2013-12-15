package de.aima13.platform.gui;

import de.aima13.platform.entity.Entity;
import de.aima13.platform.util.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Powerbar extends GuiEntity
{
    private static final Vector PADDING = new Vector(10, 10);
    private static final float HEIGHT = PADDING.y;

    private float power = 1.0f;

    public float getPower()
    {
        return power;
    }

    public void setPower(float power)
    {
        this.power = Math.max(0f, Math.min(1f, power));
    }

    public void increasePower(float add)
    {
        setPower(getPower() + add);
    }

    public void decreasePower(float sub)
    {
        setPower(getPower() - sub);
    }

    @Override
    public void render(Graphics g)
    {
        final float y = level.getContainer().getHeight() - PADDING.y - HEIGHT;
        final float width = level.getContainer().getWidth() - 2 * PADDING.x;

        g.setColor(Color.red);
        g.fillRect(PADDING.x, y, width, HEIGHT);

        g.setColor(Color.cyan);
        g.fillRect(PADDING.x, y, width * power, HEIGHT);
    }
}
