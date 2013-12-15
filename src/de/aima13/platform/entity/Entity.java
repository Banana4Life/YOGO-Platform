package de.aima13.platform.entity;

import de.aima13.platform.util.Face;
import de.aima13.platform.GameLevel;
import de.aima13.platform.PlatformGame;
import de.aima13.platform.util.Vector;

import org.newdawn.slick.Graphics;

public abstract class Entity {
	protected Vector position;

	protected Vector size;
	protected Vector velocity;
	protected Vector acceleration;
	protected GameLevel level;

	private boolean alive;
    private boolean collidable;

	protected Entity() {
		this.position = new Vector(0, 0);
		this.size = new Vector(0, 0);
		this.velocity = new Vector(0, 0);
		this.acceleration = new Vector(0, 0);
		this.alive = true;
        this.collidable = true;
	}

    public boolean isCollidable()
    {
        return collidable;
    }

    public void setCollidable(boolean collidable)
    {
        this.collidable = collidable;
    }

    public Vector getPosition() {
		return position;
	}

	public Vector getSize() {
		return size;
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

	public Vector getAcceleration() {
		return acceleration;
	}

	public GameLevel getLevel() {
		return level;
	}

	public final void init(PlatformGame game) {
		this.level = game.getLevel();
		onInit();
	}

	public void onInit() {
	}

	public void update(int delta) {
	}

	public void render(Graphics g) {
	}

	public void onCollide(Entity current, Face collidedFace) {
	}

	public void onCollideWithBorder(Face collidedFace) {
	}

	public void onDeath() {
	}

    public void relativeMove(float x, float y)
    {
        this.relativeMove(new Vector(x, y));
    }

    private void relativeMove(Vector vector)
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
}
