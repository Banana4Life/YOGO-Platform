package de.aima13.platform.entity;

import de.aima13.platform.GameLevel;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Entity
{
    private final Vector2f position;
    private final Vector2f velocity;

    protected Entity()
    {
        this.position = new Vector2f(0, 0);
        this.velocity = new Vector2f(0, 0);
    }

    public Vector2f getPosition()
    {
        return position;
    }

    public Vector2f getVelocity()
    {
        return velocity;
    }

    public void init(GameContainer container, GameLevel level)
    {}

    public void update(GameContainer container, int delta)
    {}

    public void render(GameContainer container, Graphics g)
    {}
}
