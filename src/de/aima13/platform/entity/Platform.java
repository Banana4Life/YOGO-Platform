package de.aima13.platform.entity;

import java.util.Random;

import de.aima13.platform.GameLevel;
import de.aima13.platform.gui.PowerBar;
import de.aima13.platform.util.Box;
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
    private final PowerBar powerBar;

    private SpriteSheet engineSpriteSheet;
	private Animation plasmaAnimation;
	private Animation fireAnimation;

	private int width;
	private Vector offsetLeft, offsetRight;
	private int offsetCounter;
	private boolean active;
	private int activationCooldown;
	private int stillActivatedFor;
	private Random randomGenerator;

	public Platform(PowerBar powerBar, int width) {
		super();
        this.powerBar = powerBar;

		this.width = width;
		this.offsetLeft = new Vector(0, 0);
		this.offsetRight = new Vector(0, 0);
		this.offsetCounter = 0;
		this.active = false;
		this.randomGenerator = new Random();
		this.activationCooldown = 0;
		this.stillActivatedFor = -1;
	}

    public PowerBar getPowerbar()
    {
        return powerBar;
    }

    @Override
    public void onInit() throws SlickException {
        this.setGravityScale(0);
        GameLevel lvl = getLevel();
        this.setBB(new Box(lvl.getWidth() / 4, lvl.getHeight() / 16));
        move(lvl.getWidth() / 2 - getBB().getWidth() / 2, lvl.getHeight() - getBB().getHeight() - 100);

        // load Sprites and Animations //
        this.engineSpriteSheet = new SpriteSheet("res/images/platform/Engine.png", 4, 6);
        this.engineSpriteSheet.setFilter(Image.FILTER_NEAREST);
        this.plasmaAnimation = new Animation(new SpriteSheet("res/images/platform/Plasma.png", 3, 3), 100);
        this.plasmaAnimation.start();
        this.fireAnimation = new Animation(new SpriteSheet("res/images/platform/Fire.png", 3, 3), 100);
        this.fireAnimation.start();
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
			this.active = false;
			this.activationCooldown = 1000;
			this.stillActivatedFor = -1;
		}
		if (this.activationCooldown > 0) {
			this.activationCooldown -= delta;
		}

        GameLevel lvl = getLevel();
		if (lvl.getInput().isKeyDown(Input.KEY_A)) {
			this.activate();
            setVelocity(Vector.ZERO);
            setAcceleration(Vector.ZERO);
		}

		// Get movements
		if (lvl.getInput().isKeyDown(Input.KEY_LEFT) && !isActive()) {
			// Move left
            setAcceleration(DEFAULT_ACCELERATION.scale(-1));

		} else if (lvl.getInput().isKeyDown(Input.KEY_RIGHT) && !isActive()) {
			// Move right
            setAcceleration(DEFAULT_ACCELERATION);

		} else {
            setAcceleration(Vector.ZERO);
            setVelocity(Vector.ZERO);

		}

        if (isActive())
        {
            // update Animations //
            this.plasmaAnimation.update(delta);
        } else {
        	this.offsetCounter++;
            if (this.offsetCounter >= 10)
            {
                this.offsetLeft = new Vector(offsetLeft.x + (this.randomGenerator.nextInt(3) - 1), offsetLeft.y + (this.randomGenerator.nextInt(3) - 1));
                this.offsetLeft = this.offsetLeft.mod(3);
                this.offsetRight = new Vector(offsetRight.x + (this.randomGenerator.nextInt(3) - 1), offsetRight.y + (this.randomGenerator.nextInt(3) - 1));
                this.offsetRight = this.offsetRight.mod(3);
            }
            this.offsetCounter %= 10;
        }
        this.fireAnimation.update(delta);
	}

	@Override
	public void render(Graphics g) {
		float scale = getBB().getWidth() / ((width * 3) + 2);
		if (this.active) {
			int currentFrame = this.plasmaAnimation.getFrame();
			for (int n = 0; n < width; n++) {
				this.plasmaAnimation.getImage(currentFrame).draw(
						getPosition().x + (1 + n * 3) * scale, getPosition().y,
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

        Vector p = getPosition();

		this.engineSpriteSheet.getSubImage(0, 0).draw(p.x + this.offsetLeft.x, p.y + this.offsetLeft.y, scale);
		this.engineSpriteSheet.getSubImage(1, 0).draw(p.x + this.offsetRight.x + 3 * this.width * scale - 2 * scale, p.y + this.offsetRight.y, scale);

		this.fireAnimation.getCurrentFrame().draw(p.x + this.offsetLeft.x + 1 * scale, p.y + this.offsetLeft.y + 6 * scale, scale);
		this.fireAnimation.getCurrentFrame().draw(p.x + this.offsetRight.x + 3 * this.width * scale - 2 * scale, p.y + this.offsetRight.y + 6 * scale, scale);
	}

	public void activate() {
		if (this.stillActivatedFor < 0 && this.activationCooldown <= 0) {
			this.active = true;
			this.stillActivatedFor = 1000;
			getLevel().getPlasmaSound().play();
		}
	}

	public boolean isActive() {
		return this.active;
	}

    @Override
    public void onCollide(Entity current, Face collidedFace)
    {
        if (isActive())
        {
            this.powerBar.decreaseValue(.1f);
        }
    }

    @Override
    public void onCollideWithBorder(Face collidedFace)
    {
        switch (collidedFace)
        {
            case LEFT:
                move(getPosition().scale(0, 1));
                setVelocity(Vector.ZERO);
                setAcceleration(Vector.ZERO);
                break;
            case RIGHT:
                move(new Vector(getLevel().getWidth() - getBB().getWidth() - 1, getPosition().y));
                setVelocity(Vector.ZERO);
                setAcceleration(Vector.ZERO);
                break;
            default:
                System.err.println("Should not happen!");
                new IllegalStateException().printStackTrace(System.err);
        }
    }
}
