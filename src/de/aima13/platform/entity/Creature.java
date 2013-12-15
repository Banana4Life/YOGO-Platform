package de.aima13.platform.entity;

import de.aima13.platform.util.Face;
import de.aima13.platform.util.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Creature extends Entity
{
    @Override
    public void onInit()
    {
        position = new Vector(0, 0);
        velocity = new Vector(5, 5);
        size = new Vector(20, 50);
    }

    @Override
    public void update(int delta)
    {
        float x = position.x;
        float y = position.y;

        float vX = velocity.x;
        float vY = velocity.y;

        if (x + vX < 0)
        {
            x = 0;
            vX *= -1;
        }
        else if (x + vX + size.x > level.getWidth())
        {
            x = level.getWidth() - size.x;
            vX *= -1;
        }
        else
        {
            x += vX;
        }

        if (y + velocity.y < 0)
        {
            y = 0;
            vY *= -1;
        }
        else if (y + velocity.y + size.y > level.getHeight())
        {
            y = level.getHeight() - size.y;
            vY *= -1;
        }
        else
        {
            y += vY;
        }
        position = new Vector(x, y);
        velocity = new Vector(vX, vY);
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);
        Color c = g.getColor();
        g.setColor(Color.green);
        g.drawRect(position.x, position.y, size.x, size.y);
        g.fillRect(position.x, position.y, size.x, size.y);
        g.setColor(c);
    }

    @Override
    public void onCollide(Entity current, Face collidedFace)
    {
        this.position = new Vector(this.position.x, current.position.y - this.size.y - 1);
        this.velocity = new Vector(this.velocity.x, this.velocity.y * -1);
    }
}
