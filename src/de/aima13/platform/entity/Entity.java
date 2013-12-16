package de.aima13.platform.entity;

import de.aima13.platform.util.Box;
import de.aima13.platform.util.Face;
import de.aima13.platform.GameLevel;
import de.aima13.platform.PlatformGame;
import de.aima13.platform.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class Entity {
    private GameLevel level;

	private Vector position;
	private Vector velocity;
	private Vector acceleration;
    private Box boundingBox;

	private boolean alive;
    private float gravityScale = 1f;

	protected Entity() {
		this.position = Vector.ZERO;
        this.velocity = Vector.ZERO;
		this.acceleration = Vector.ZERO;
        this.boundingBox = null;
		this.alive = true;
	}

    public Vector getPosition() {
		return position;
	}

	public Box getBB() {
		return this.boundingBox;
	}

    public Box getAbsBB()
    {
        if (this.boundingBox == null)
        {
            return null;
        }
        return this.boundingBox.absolute(this.position);
    }

	public boolean isAlive() {
		return this.alive;
	}

	public void die() {
		this.alive = false;
	}

	public Vector getVelocity() {
		return velocity;
	}

    public Vector getAcceleration()
    {
        return acceleration;
    }

    public void setAcceleration(Vector acceleration)
    {
        this.acceleration = acceleration;
    }

	public GameLevel getLevel() {
		return level;
	}

	public final void init(PlatformGame game) throws SlickException
    {
		this.level = game.getLevel();
		onInit();
	}

	public void onInit() throws SlickException
    {
	}

	public void update(int delta) {
	}

	public void render(Graphics g) {
	}

	public void onCollide(Entity target, Face collidedFace) {
	}

	public void onCollideWithBorder(Face collidedFace) {
	}

	public void onDeath() {
	}

    public void relativeMove(float x, float y)
    {
        this.relativeMove(new Vector(x, y));
    }

    public void relativeMove(Vector vector)
    {
        this.move(position.add(vector));
    }

    public void move(float x, float y)
    {
        move(new Vector(x, y));
    }

    public void move(Vector vector)
    {
        this.position = vector;
    }

    public void setVelocity(Vector velocity)
    {
        this.velocity = velocity;
    }

    public float getGravityScale()
    {
        return gravityScale;
    }

    public void setGravityScale(float gravityScale)
    {
        this.gravityScale = gravityScale;
    }

    public void setBB(Box boundingBox)
    {
        this.boundingBox = boundingBox;
    }

    protected void drawBB(Graphics g, Color c)
    {
        g.setColor(c);

        Box b = getAbsBB();
        g.drawRect(b.getBase().x, b.getBase().y, b.getWidth(), b.getHeight());
        g.fillRect(b.getBase().x, b.getBase().y, b.getWidth(), b.getHeight());
    }

    public void preUpdate(int delta)
    {}
}
