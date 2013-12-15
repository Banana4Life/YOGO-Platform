package de.aima13.platform;

import java.util.Iterator;
import java.util.LinkedList;

import de.aima13.platform.util.Face;
import de.aima13.platform.util.Rect;
import de.aima13.platform.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;

import de.aima13.platform.entity.Entity;
import de.aima13.platform.entity.TiledBackground;
import de.aima13.platform.util.Face;
import de.aima13.platform.util.Rect;

public class GameLevel {
	private final PlatformGame game;
	private final GameContainer container;
	private final LinkedList<Entity> entities;
	private final Input input;

	Image img1, img2, img3;

	int img1Height, img2Height, img3Height;
	int screenResolution;

	Vector2f position1, position2, position3;

	private TiledBackground background;

	public GameLevel(PlatformGame game, Input input) throws SlickException {
		this.game = game;
		this.container = game.getContainer();

		this.input = input;
		entities = new LinkedList<>();

		Image backImg1 = new Image(
				"res/images/background/BackgroundTileset.png").getSubImage(0,
				0, 32, 32).getScaledCopy(2f);
		backImg1.setFilter(Image.FILTER_NEAREST);
		background = new TiledBackground(new Image[] { backImg1 }, new Vector(
				backImg1.getWidth(), backImg1.getHeight()));
		background.init(this);

		img1 = new Image("res/background/background.png");
		img2 = new Image("res/background/background.png");
		img3 = new Image("res/background/background.png");

		img1Height = img1.getHeight(); // 1280
		img2Height = img2.getHeight(); // 1280
		img3Height = img3.getHeight(); // 1280

		position1 = new Vector2f(0, 0);
		position2 = new Vector2f(0, position1.y + img1.getHeight());
		position3 = new Vector2f(0, position2.y + img2.getHeight());
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
			if (!e.isAlive()) {
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
		this.onRender(g);
		for (Entity entity : entities) {
			entity.render(g);
		}
	}

	private void onRender(Graphics g) {
		// if (position1.y > 0) {
		// // oben platz
		// position3.y = position1.y - img1Height;
		// }
		// if (position2.y > 0) {
		// position1.y = position2.y - img2Height;
		// }
		// if (position3.y > 0) {
		// position2.y = position3.y - img3Height;
		// }
		// g.drawImage(img1, position1.x, position1.y);
		// g.drawImage(img2, position2.x, position2.y);
		// g.drawImage(img3, position3.x, position3.y);
		//
		// position1.y += 5;
		// position2.y += 5;
		// position3.y += 5;
		Log.info("render background");
		background.render(g);
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

		for (Entity entity : this.entities) {
			Vector pos = entity.getPosition();
			Vector size = entity.getSize();
			if (pos.x < 0) {
				entity.onCollideWithBorder(Face.LEFT);
			}
			if (pos.y < 0) {
				entity.onCollideWithBorder(Face.TOP);
			}
			if (pos.x + size.x > getWidth()) {
				entity.onCollideWithBorder(Face.RIGHT);
			}
			if (pos.y + size.y > getHeight()) {
				entity.onCollideWithBorder(Face.BOTTOM);
			}
		}
	}

	private Face checkCollision(Entity entityA, Entity entityB) {
		Rect a = new Rect(entityA.getPosition(), entityA.getSize());
		Rect b = new Rect(entityB.getPosition(), entityB.getSize());

		Face collFace = b.intersects(a);
		if (collFace != null) {
			return collFace;
		}

		return null;
	}
}
