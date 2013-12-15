package de.aima13.platform.entity;

import de.aima13.platform.states.Game;
import de.aima13.platform.states.Loose;
import de.aima13.platform.util.Box;
import de.aima13.platform.util.Face;
import de.aima13.platform.util.Vector;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.util.Log;

public class Creature extends Entity {
	private static final float IMAGE_SCALE = 4;
	private final Platform platform;
	private boolean failed = false;

	protected SpriteSheet characterSpriteSheet;
	protected Animation jumpingAnimation;
	protected int currentJumpingYOffset = 0;
	protected int[] jumpingYOffset = { 3, 5, 6, 5, 3 };
	protected Animation beltAnimation;

	protected boolean inAir;
	protected boolean prevFallingDown;

	public Creature(Platform platform) {
		this.platform = platform;
	}

	@Override
	public void onInit() {
        move(Vector.ZERO);
		//setVelocity(new Vector(5, 5));
        this.setBoundingBox(new Box(Vector.ZERO, new Vector(16 * IMAGE_SCALE, 32 * IMAGE_SCALE)));

		try {
			this.characterSpriteSheet = new SpriteSheet("res/images/character/CharacterSpriteSheet.png", 16, 32);
			this.characterSpriteSheet.setFilter(Image.FILTER_NEAREST);
			this.beltAnimation = new Animation(new SpriteSheet("res/images/character/Belt.png", 4, 1), 100);
			this.jumpingAnimation = new Animation(this.characterSpriteSheet, 0, 3, 5, 3, true, 30, false);
			jumpingAnimation.stop();
		} catch (SlickException e) {
			// do nothing
		}

		this.inAir = this.prevFallingDown = true;
	}

	@Override
	public void update(int delta) {
		this.jumpingAnimation.update(delta);
		this.beltAnimation.update(delta);

		if (mayTurn()) {
            setVelocity(getVelocity().scale(-1, 1));
		}

		if (this.inAir) {
			this.prevFallingDown = (getVelocity().y > 0);
		}

		if (this.jumpingAnimation.getFrame() == this.jumpingAnimation.getFrameCount() - 1) {
			this.inAir = true;
			this.jumpingAnimation.stop();
			this.jumpingAnimation.setCurrentFrame(0);
			this.currentJumpingYOffset = 0;

            setVelocity(new Vector(0, -5f));
		}

		if (getPosition().y + getBB().getHeight() > platform.getPosition().y) {
			setVelocity(getVelocity().scale(0, 1));
			failed = true;
		}
	}

	public boolean isAbovePlatform() {
        Box bb = getAbsBB();
        Box platformBb = platform.getAbsBB();
		if (bb.getBase().x + bb.getWidth() >= platformBb.getBase().x && getPosition().x < platform.getPosition().x + platformBb.getWidth()) {
			System.out.println("Above platform!");
			return true;
		}
		return false;
	}

	public boolean isRising() {
		return getVelocity().y < 0;
	}

	public boolean mayTurn() {
		if (isAbovePlatform() && isRising()) {
			return Math.abs(this.platform.getPosition().y - this.getPosition().y) > 100;
		}
		return false;
	}

	public void onJump() {
		level.getJumpSound().play();
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

        Vector pos = getPosition();
        Vector v = getVelocity();

		if (v.y >= 0 && this.inAir) {
			if (!this.prevFallingDown) {
				this.characterSpriteSheet.getSprite(0, 2).draw(pos.x,
						pos.y, Creature.IMAGE_SCALE);
			} else {
				this.characterSpriteSheet.getSprite(0, 1).draw(pos.x,
						pos.y, Creature.IMAGE_SCALE);
			}
		} else if (this.inAir) {
			this.characterSpriteSheet.getSprite(0, 0).draw(pos.x,
					pos.y, Creature.IMAGE_SCALE);
		} else {
			this.jumpingAnimation.getCurrentFrame().draw(
					pos.x,
					pos.y
							+ this.jumpingYOffset[this.jumpingAnimation
									.getFrame()] * Creature.IMAGE_SCALE,
					Creature.IMAGE_SCALE);
			this.currentJumpingYOffset = this.jumpingYOffset[this.jumpingAnimation
					.getFrame()];
		}
		this.beltAnimation.getCurrentFrame().draw(
				pos.x + 6 * Creature.IMAGE_SCALE,
				pos.y + this.currentJumpingYOffset
						* Creature.IMAGE_SCALE + 15 * Creature.IMAGE_SCALE,
				Creature.IMAGE_SCALE);
	}

	@Override
	public void onCollide(Entity current, Face collidedFace) {
		if (!failed && current instanceof Platform && ((Platform) current).isActive()) {
			move(getPosition().add(0, current.getPosition().y - this.getBB().getHeight() - 1));
			setVelocity(Vector.ZERO);

			this.inAir = false;
			this.jumpingAnimation.start();
			this.onJump();
		}
	}

	@Override
	public void onCollideWithBorder(Face collidedFace) {
		if (failed) {
			die();
		} else {
			float x = getPosition().x;
			float y = getPosition().y;

			float vX = getVelocity().x;
			float vY = getVelocity().y;

			switch (collidedFace) {
			case TOP:
				y = 0;
				vY *= -1;
				break;
			case BOTTOM:
                failed = true;
				Log.error("Collided with the world border!");
				break;
			case LEFT:
				x = 0;
				vX *= -1;
				break;
			case RIGHT:
				x = level.getWidth() - getBB().getWidth() - 1;
				vX *= -1;
				break;
			}

            move(x, y);
			setVelocity(new Vector(vX, vY));
		}
	}

	@Override
	public void onDeath() {
		level.getGame().enterState(Loose.ID, new EmptyTransition(),
				new FadeInTransition(Color.black));
		try {
			((Game) level.getGame().getState(Game.ID)).resetState();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
