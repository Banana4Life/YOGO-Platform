package de.aima13.platform;

import java.util.LinkedList;

import de.aima13.platform.util.Face;
import de.aima13.platform.util.Rect;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import de.aima13.platform.entity.Entity;

public class GameLevel {
	private final LinkedList<Entity> entities;
	private final GameContainer container;
	private final Input input;

	public GameLevel(GameContainer container, Input input) {
		this.container = container;
		this.input = input;
		entities = new LinkedList<>();
	}

	public void addEntity(Entity entity) {
		this.entities.addLast(entity);
		entity.init(this);
	}

	public final void update(GameContainer container, int delta) {
		for (Entity entity : entities) {
			entity.update(delta);
		}
		this.onUpdate(container, delta);
        this.detectCollisions();
	}

	public void onUpdate(GameContainer container, int delta) {

	}

	public final void render(GameContainer container, Graphics g) {
		for (Entity entity : entities) {
			entity.render(g);
		}
		this.onRender(g);
	}

	private void onRender(Graphics g) {

	}

	public GameContainer getContainer() {
		return container;
	}

	public Input getInput() {
		return input;
	}

	public int getWidth() {
		return getContainer().getWidth();
	}

	public int getHeight() {
		return getContainer().getHeight();
	}

    private void detectCollisions()
    {
        Entity last = this.entities.getFirst();
        Entity current;
        for (int i = 1; i < this.entities.size(); ++i)
        {
            current = this.entities.get(i);

            Face collFace = this.checkCollision(last, current);
            if (collFace != null)
            {
                last.onCollide(current, collFace);
                current.onCollide(last, collFace.opposite());
            }
        }
    }

    private Face checkCollision(Entity entityA, Entity entityB)
    {
        Rect a = new Rect(entityA.getPosition(), entityA.getSize());
        Rect b = new Rect(entityB.getPosition(), entityB.getSize());

        Face collFace = a.intersects(b);
        if (collFace != null)
        {
            System.out.println("Intersection: " + collFace);
            return collFace;
        }

        return null;
    }
}
