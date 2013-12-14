package de.aima13.platform;

import de.aima13.slick2dtest.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.LinkedList;

public class GameLevel
{
    private final LinkedList<Entity> entities;
    private final GameContainer container;

    public GameLevel(GameContainer container)
    {
        this.container = container;
        entities = new LinkedList<>();
    }

    public void addEntity(Entity entity)
    {
        this.entities.addLast(entity);
        entity.init(container, this);
    }

    public final void update(GameContainer container, int delta)
    {
        for (Entity entity : entities)
        {
            entity.update(container, delta);
        }
        this.onUpdate(container, delta);
    }

    public void onUpdate(GameContainer container, int delta)
    {

    }

    public final void render(GameContainer container, Graphics g)
    {
        for (Entity entity : entities)
        {
            entity.render(container, g);
        }
        this.onRender(container, g);
    }

    private void onRender(GameContainer container, Graphics g)
    {

    }
}
