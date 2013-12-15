package de.aima13.platform.entity;

import de.aima13.platform.states.Game;
import de.aima13.platform.states.Loose;
import de.aima13.platform.states.MainMenu;
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
import org.newdawn.slick.state.transition.FadeOutTransition;
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
		position = new Vector(0, 0);
		velocity = new Vector(5, 5);
		size = new Vector(16 * IMAGE_SCALE, 32 * IMAGE_SCALE);

		try {
			this.characterSpriteSheet = new SpriteSheet(
					"res/images/character/CharacterSpriteSheet.png", 16, 32);
			this.characterSpriteSheet.setFilter(Image.FILTER_NEAREST);
			this.beltAnimation = new Animation(new SpriteSheet(
					"res/images/character/Belt.png", 4, 1), 100);
			this.jumpingAnimation = new Animation(this.characterSpriteSheet, 0,
					3, 5, 3, true, 30, false);
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
			velocity = new Vector(velocity.x * -1, velocity.y);
		}

		if (this.inAir) {
			this.prevFallingDown = (this.velocity.y > 0);
		}

		if (this.jumpingAnimation.getFrame() == this.jumpingAnimation
				.getFrameCount() - 1) {
			this.inAir = true;
			this.jumpingAnimation.stop();
			this.jumpingAnimation.setCurrentFrame(0);
			this.currentJumpingYOffset = 0;

			this.velocity = new Vector(0, -5f);
		}

		// Vector newPos = position.add(velocity);
		// if (newPos.y + size.y > platform.position.y) {
		// newPos = new Vector(position.x, newPos.y);
		// velocity = new Vector(0, velocity.y);
		// failed = true;
		// }
		//
		// position = newPos;
		if (position.y + size.y > platform.position.y) {
			velocity = new Vector(0, velocity.y);
			failed = true;
		}
		position = position.add(velocity);
	}

	public boolean isAbovePlatform() {
		if (position.x + size.x >= platform.position.x
				&& position.x < platform.position.x + platform.size.x) {
			System.out.println("Above platform!");
			return true;
		}
		return false;
	}

	public boolean isRising() {
		return velocity.y < 0;
	}

	public boolean mayTurn() {
		if (isAbovePlatform() && isRising()) {
			return Math.abs(this.platform.position.y - this.position.y) > 100;
		}
		return false;
	}

	public void onJump() {
		level.getJumpSound().play();
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		if (this.velocity.y >= 0 && this.inAir) {
			if (!this.prevFallingDown) {
				this.characterSpriteSheet.getSprite(0, 2).draw(this.position.x,
						this.position.y, Creature.IMAGE_SCALE);
			} else {
				this.characterSpriteSheet.getSprite(0, 1).draw(this.position.x,
						this.position.y, Creature.IMAGE_SCALE);
			}
		} else if (this.inAir) {
			this.characterSpriteSheet.getSprite(0, 0).draw(this.position.x,
					this.position.y, Creature.IMAGE_SCALE);
		} else {
			this.jumpingAnimation.getCurrentFrame().draw(
					this.position.x,
					this.position.y
							+ this.jumpingYOffset[this.jumpingAnimation
									.getFrame()] * Creature.IMAGE_SCALE,
					Creature.IMAGE_SCALE);
			this.currentJumpingYOffset = this.jumpingYOffset[this.jumpingAnimation
					.getFrame()];
		}
		this.beltAnimation.getCurrentFrame().draw(
				this.position.x + 6 * Creature.IMAGE_SCALE,
				this.position.y + this.currentJumpingYOffset
						* Creature.IMAGE_SCALE + 15 * Creature.IMAGE_SCALE,
				Creature.IMAGE_SCALE);
	}

	@Override
	public void onCollide(Entity current, Face collidedFace) {
		if (!failed && current instanceof Platform && ((Platform) current).isActive()) {
			this.position = new Vector(this.position.x, current.position.y - this.size.y - 1);
			this.velocity = new Vector(0, 0);

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
			float x = position.x;
			float y = position.y;

			float vX = velocity.x;
			float vY = velocity.y;

			switch (collidedFace) {
			case TOP:
				y = 0;
				vY *= -1;
				break;
			case BOTTOM:
				failed = true;
				Log.error("Phillips Collision hat gefailt!");
				break;
			case LEFT:
				x = 0;
				vX *= -1;
				break;
			case RIGHT:
				x = level.getWidth() - size.x - 1;
				vX *= -1;
				break;
			}

			position = new Vector(x, y);
			velocity = new Vector(vX, vY);
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
