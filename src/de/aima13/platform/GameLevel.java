package de.aima13.platform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.aima13.platform.util.Box;
import de.aima13.platform.util.Face;
import de.aima13.platform.util.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import de.aima13.platform.entity.Entity;
import de.aima13.platform.entity.TiledBackground;

public class GameLevel {
    private static final Vector G = new Vector(0, 9.81f).scale(.01f);

	private final PlatformGame game;
	private final GameContainer container;
	private final LinkedList<Entity> entities;
	private final Input input;
    private Vector gravity;

	private TiledBackground background;
	private Sound plasmaSound, jumpSound;

	public GameLevel(PlatformGame game, Input input) throws SlickException {
		this.game = game;
		this.container = game.getContainer();

		this.input = input;
		entities = new LinkedList<>();

		plasmaSound = new Sound("res/sound/plasma.wav");
		jumpSound = new Sound("res/sound/plasma.wav");
	}

	public void init() throws SlickException {
		SpriteSheet sheet = new SpriteSheet(new Image(
				"res/images/background/BackgroundTileset.png"), 32, 32);
		Image img = new Image("res/images/background/BackgroundTileset.png")
				.getSubImage(0, 0, 32, 32).getScaledCopy(2f);
		img.setFilter(Image.FILTER_NEAREST);
		Image[] set = new Image[] { img };
		// backImg1.setFilter(Image.FILTER_NEAREST);
		background = new TiledBackground(set, new Vector(set[0].getWidth(),
				set[0].getHeight()));
		background.init(game);

		plasmaSound = new Sound("res/sound/plasma.wav");
		jumpSound = new Sound("res/sound/plasma.wav");
        this.gravity = G;
	}

	public <T extends Entity> T spawn(T e) {
		this.addEntity(e);
		return e;
	}

    public Vector getGravity()
    {
        return gravity;
    }

    public void setGravity(Vector gravity)
    {
        this.gravity = gravity;
    }

    protected void addEntity(Entity entity) {
        try
        {
            entity.init(game);
            this.entities.addLast(entity);
        }
        catch (SlickException e)
        {
            e.printStackTrace(System.err);
        }
	}

	public void reset() {
		entities.clear();
	}

	public final void update(int delta) {
		this.onUpdate(delta);
		List<Entity> remove = new ArrayList<>();
		for (Entity e : new ArrayList<>(this.entities)) {
			if (!e.isAlive()) {
				remove.add(e);
				e.onDeath();
				continue;
			}
            e.relativeMove(e.getVelocity());
            e.setVelocity(e.getVelocity().add(e.getAcceleration().add(this.gravity.scale(e.getGravityScale()))));
			e.update(delta);
		}

		this.entities.removeAll(remove);

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

	public Sound getPlasmaSound() {
		return plasmaSound;
	}

	public Sound getJumpSound() {
		return jumpSound;
	}

	private void detectCollisions() {
		Set<Long> checked = new HashSet<>();
		for (Entity a : this.entities) {
			for (Entity b : this.entities) {
				if (a == b) {
					continue;
				}
				long combinedHash = (long) a.hashCode() + (long) b.hashCode();
				if (checked.contains(combinedHash)) {
					continue;
				}
				checked.add(combinedHash);

				Face collFace = this.checkCollision(a, b);
				if (collFace != null) {
					a.onCollide(b, collFace);
					b.onCollide(a, collFace.opposite());
				}
			}
		}

		for (Entity entity : this.entities) {
			Box bb = entity.getAbsBB();
            if (bb == null)
            {
                continue;
            }
            Vector b = bb.getBase();
			if (b.x < 0) {
				entity.onCollideWithBorder(Face.LEFT);
			}
			if (b.y < 0) {
				entity.onCollideWithBorder(Face.TOP);
			}
			if (b.x + bb.getWidth() > getWidth()) {
				entity.onCollideWithBorder(Face.RIGHT);
			}
			if (b.y + bb.getHeight() > getHeight()) {
				entity.onCollideWithBorder(Face.BOTTOM);
			}
		}
	}

	private Face checkCollision(Entity entityA, Entity entityB) {
		Box a = entityA.getAbsBB();
		Box b = entityB.getAbsBB();

        if (a == null || b == null)
        {
            return null;
        }

		Face collFace = b.intersects(a);
		if (collFace != null) {
			return collFace;
		}

		return null;
	}
}
