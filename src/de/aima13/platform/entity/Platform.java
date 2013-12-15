package de.aima13.platform.entity;

import java.util.Random;

import de.aima13.platform.gui.Powerbar;
import de.aima13.platform.util.Face;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import de.aima13.platform.util.Vector;

public class Platform extends Entity {

	private static final Vector DEFAULT_ACCELERATION = new Vector(1.0f, 0f);
	private static final float DECELERATE_FACTOR = 1.5f;
    private final Powerbar powerbar;

    private SpriteSheet engineSpriteSheet;
	private Animation plasmaAnimation;
	private Animation fireAnimation;

	private int width;
	private Vector offsetLeft, offsetRight;
	private int offsetCounter;
	private boolean isActivated;
	private int activationCooldown;
	private int stillActivatedFor;
	private Random randomGenerator;

	public Platform(Powerbar powerbar) {
		this(powerbar, null, null, null, 3);
	}

	public Platform(Powerbar powerbar, int width) {
		this(powerbar, null, null, null, width);
	}

	public Platform(Powerbar powerbar, Vector size) {
		this(powerbar, size, null, null, 3);
	}

	public Platform(Powerbar powerbar, Vector size, Vector position) {
		this(powerbar, size, position, null, 3);
	}

	public Platform(Powerbar powerbar, Vector size, Vector position, Vector acceleration, int width) {
		super();
        this.powerbar = powerbar;
        this.size = size;
		this.position = position;
		this.acceleration = acceleration;

		this.width = width;
		this.offsetLeft = new Vector(0, 0);
		this.offsetRight = new Vector(0, 0);
		this.offsetCounter = 0;
		this.isActivated = false;
		this.randomGenerator = new Random();
		this.activationCooldown = 0;
		this.stillActivatedFor = -1;
	}

    public Powerbar getPowerbar()
    {
        return powerbar;
    }

    @Override
	public void onInit() {
		super.onInit();
		if (size == null) {
			size = new Vector(level.getWidth() / 4, level.getHeight() / 16);
		}
		if (position == null) {
			position = new Vector(level.getWidth() / 2 - size.x / 2,
					level.getHeight() - size.y - 100);
		}
		if (acceleration == null) {
			acceleration = DEFAULT_ACCELERATION;
		}

		// load Sprites and Animations //
		try {
			this.engineSpriteSheet = new SpriteSheet(
					"res/images/platform/Engine.png", 4, 6);
			this.engineSpriteSheet.setFilter(Image.FILTER_NEAREST);
			this.plasmaAnimation = new Animation(new SpriteSheet(
					"res/images/platform/Plasma.png", 3, 3), 100);
			this.plasmaAnimation.start();
			this.fireAnimation = new Animation(new SpriteSheet(
					"res/images/platform/Fire.png", 3, 3), 100);
			this.fireAnimation.start();
		} catch (SlickException e) {
			// do nothing
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if (this.stillActivatedFor > 0) {
			this.stillActivatedFor -= delta;
			if (this.stillActivatedFor < 0) {
				this.stillActivatedFor = 0;
			}
		} else if (this.stillActivatedFor == 0) {
			this.isActivated = false;
			this.activationCooldown = 1000;
			this.stillActivatedFor = -1;
		}
		if (this.activationCooldown > 0) {
			this.activationCooldown -= delta;
		}

		if (level.getInput().isKeyDown(Input.KEY_A)) {
			this.activate();
		}

		// Get movements
		if (level.getInput().isKeyDown(Input.KEY_LEFT) && !isActive()) {
			// Move left
			if (velocity.x > 0) {
				// Reset
				velocity = new Vector(0, acceleration.y);
			}
			velocity = velocity.sub(acceleration);
			if (position.x + velocity.x < 0) {
				position = new Vector(0, position.y);
			} else {
				position = position.add(velocity);
			}
		} else if (level.getInput().isKeyDown(Input.KEY_RIGHT) && !isActive()) {
			// Move right
			if (velocity.x < 0) {
				// Reset
				velocity = new Vector(0, acceleration.y);
			}
			velocity = velocity.add(acceleration);
			if (position.x + velocity.x > level.getWidth() - size.x) {
				position = new Vector(level.getWidth() - size.x, position.y);
				// Reset
				velocity = new Vector(0, acceleration.y);
			} else {
				position = position.add(velocity);
			}
		} else {
			if (velocity.x > 0) {
				if (velocity.x + DECELERATE_FACTOR * acceleration.x < 0) {
					// Reset
					velocity = new Vector(0, acceleration.y);
				} else {
					velocity = velocity.sub(DECELERATE_FACTOR * acceleration.x,
							acceleration.y);
				}
			} else if (velocity.x < 0) {
				if (velocity.x + DECELERATE_FACTOR * acceleration.x > 0) {
					// Reset
					velocity = new Vector(0, acceleration.y);
				} else {
					velocity = velocity.add(DECELERATE_FACTOR * acceleration.x,
							acceleration.y);
				}
			}
			// Reset
			velocity = new Vector(0, 0);

			// update Animations //
			this.plasmaAnimation.update(delta);
			this.fireAnimation.update(delta);

			this.offsetCounter++;
			if (this.offsetCounter >= 10) {
				this.offsetLeft = new Vector(offsetLeft.x + (this.randomGenerator.nextInt(3) - 1), offsetLeft.y + (this.randomGenerator.nextInt(3) - 1));
				this.offsetLeft = this.offsetLeft.mod(3);
				this.offsetRight = new Vector(offsetRight.x + (this.randomGenerator.nextInt(3) - 1), offsetRight.y + (this.randomGenerator.nextInt(3) - 1));
				this.offsetRight = this.offsetRight.mod(3);
			}
			this.offsetCounter %= 10;
		}
	}

	@Override
	public void render(Graphics g) {
		float scale = size.x / ((width * 3) + 2);
		if (this.isActivated) {
			int currentFrame = this.plasmaAnimation.getFrame();
			for (int n = 0; n < width; n++) {
				this.plasmaAnimation.getImage(currentFrame).draw(
						this.position.x + (1 + n * 3) * scale, this.position.y,
						scale);
				currentFrame += this.plasmaAnimation.getFrameCount() / 2;
				currentFrame %= this.plasmaAnimation.getFrameCount();
			}

			this.offsetLeft = new Vector(0, 0);
			this.offsetRight = new Vector(0, 0);
			this.offsetCounter = 9;
		}
		// g.setColor(Color.black);
		// g.drawString("xOffset = " + offsetRight.x + " yOffset = " + offsetLeft.y, 20, 200);
		
		this.engineSpriteSheet.getSubImage(0, 0).draw(this.position.x + this.offsetLeft.x, this.position.y + this.offsetLeft.y, scale);
		this.engineSpriteSheet.getSubImage(1, 0).draw(this.position.x + this.offsetRight.x + 3 * this.width * scale - 2 * scale, this.position.y + this.offsetRight.y, scale);

		this.fireAnimation.getCurrentFrame().draw(this.position.x + this.offsetLeft.x + 1 * scale, this.position.y + this.offsetLeft.y + 6 * scale, scale);
		this.fireAnimation.getCurrentFrame().draw(this.position.x + this.offsetRight.x + 3 * this.width * scale - 2 * scale, this.position.y + this.offsetRight.y + 6 * scale, scale);
	}

	public void activate() {
		if (this.stillActivatedFor < 0 && this.activationCooldown <= 0) {
			this.isActivated = true;
			this.stillActivatedFor = 1000;
			level.getPlasmaSound().play();
		}
	}

	public boolean isActive() {
		return this.isActivated;
	}

    @Override
    public void onCollide(Entity current, Face collidedFace)
    {
        this.powerbar.decreasePower(.1f);
    }
}
