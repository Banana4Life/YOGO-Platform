package de.aima13.platform;

import java.util.Iterator;
import java.util.LinkedList;

import de.aima13.platform.util.Face;
import de.aima13.platform.util.Rect;
import de.aima13.platform.util.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import de.aima13.platform.entity.Entity;

public class GameLevel {
    private final PlatformGame game;
    private final GameContainer container;
	private final LinkedList<Entity> entities;
	private final Input input;

	public GameLevel(PlatformGame game, Input input) {
        this.game = game;
		this.container = game.getContainer();
		this.input = input;
		entities = new LinkedList<>();
	}

	public void addEntity(Entity entity) {
		this.entities.addLast(entity);
		entity.init(this);
	}

	public final void update(int delta) {
        this.onUpdate(delta);
        Iterator<Entity> it = this.entities.iterator();
        Entity e;
		while (it.hasNext()) {
            e = it.next();
            if (!e.isAlive())
            {
                it.remove();
                e.onDeath();
                continue;
            }
			e.update(delta);
		}
		this.detectCollisions();
	}

	public void onUpdate(int delta) {

	}

	public final void render(Graphics g) {
		for (Entity entity : entities) {
			entity.render(g);
		}
		this.onRender(g);
	}

	private void onRender(Graphics g) {

	}

    public PlatformGame getGame() {
        return this.game;
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

	private void detectCollisions() {
		Entity last = this.entities.getFirst();
		Entity current;
		for (int i = 1; i < this.entities.size(); ++i) {
			current = this.entities.get(i);

			Face collFace = this.checkCollision(last, current);
			if (collFace != null) {
				last.onCollide(current, collFace);
				current.onCollide(last, collFace.opposite());
			}
		}

        for (Entity entity : this.entities)
        {
            Vector pos = entity.getPosition();
            Vector size = entity.getSize();
            if (pos.x < 0)
            {
                entity.onCollideWithBorder(Face.LEFT);
            }
            if (pos.y < 0)
            {
                entity.onCollideWithBorder(Face.TOP);
            }
            if (pos.x + size.x > getWidth())
            {
                entity.onCollideWithBorder(Face.RIGHT);
            }
            if (pos.y + size.y > getHeight())
            {
                entity.onCollideWithBorder(Face.BOTTOM);
            }
        }
	}

	private Face checkCollision(Entity entityA, Entity entityB) {
		Rect a = new Rect(entityA.getPosition(), entityA.getSize());
		Rect b = new Rect(entityB.getPosition(), entityB.getSize());

		Face collFace = b.intersects(a);
		if (collFace != null) {
			System.out.println("Intersection: " + collFace);
			return collFace;
		}

		return null;
	}
}
