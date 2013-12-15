package de.aima13.platform.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.aima13.platform.PlatformGame;
import de.aima13.platform.util.Vector;

public class Credits extends BasicGameState {

	public final static int ID = 4;
	protected PlatformGame game; // stored for later use

	/*
	 * Easter EGG
	 */

	private Image moto;
	private Image[] framesWheel;
	private Animation wheelAnim;
	private float bikeScale = 0.1f;
	private Vector bikeDimensions;
	private Vector bikePosition;
	private Vector bikeVelocity;
	private boolean bikePaused = false;
	private String hint;
	private int waitedFramesDrive;
	private boolean waitToDrive, bikeWaited;
	private final static int waitFramesToDrive = 60;

	/*
	 * 
	 */

	/*
	 * Easter EGG
	 */
	private void resetBike() {
		bikeDimensions = new Vector(2600f * bikeScale, 1470f * bikeScale + 0f); // add
																				// 10
																				// pixels
																				// spacing
																				// for
																				// the
																				// street
																				// ;)
		bikePosition = new Vector(0 - bikeDimensions.x, game.getContainer()
				.getHeight() - bikeDimensions.y);
		bikeVelocity = new Vector(10f, 0);
		bikePaused = false;
		waitedFramesDrive = 0;
		bikeWaited = false;
		waitToDrive = false;
	}

	/*
	 * 
	 */

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		if (game instanceof PlatformGame) {
			this.game = (PlatformGame) game;
		} else {
			throw new SlickException("StateBaseGame isn't a PlatformGame!");
		}
		/*
		 * Easter EGG
		 */
		resetBike();

		moto = new Image("res/svg/motorcycle-wireframe.png")
				.getScaledCopy(bikeScale);
		moto.setRotation(-30);

		framesWheel = new Image[10];
		framesWheel[0] = new Image("res/svg/frames-wheel/frame01.png")
				.getScaledCopy(bikeScale);
		framesWheel[1] = new Image("res/svg/frames-wheel/frame02.png")
				.getScaledCopy(bikeScale);
		framesWheel[2] = new Image("res/svg/frames-wheel/frame03.png")
				.getScaledCopy(bikeScale);
		framesWheel[3] = new Image("res/svg/frames-wheel/frame04.png")
				.getScaledCopy(bikeScale);
		framesWheel[4] = new Image("res/svg/frames-wheel/frame05.png")
				.getScaledCopy(bikeScale);
		framesWheel[5] = new Image("res/svg/frames-wheel/frame06.png")
				.getScaledCopy(bikeScale);
		framesWheel[6] = new Image("res/svg/frames-wheel/frame07.png")
				.getScaledCopy(bikeScale);
		framesWheel[7] = new Image("res/svg/frames-wheel/frame08.png")
				.getScaledCopy(bikeScale);
		framesWheel[8] = new Image("res/svg/frames-wheel/frame09.png")
				.getScaledCopy(bikeScale);
		framesWheel[9] = new Image("res/svg/frames-wheel/frame10.png")
				.getScaledCopy(bikeScale);

		wheelAnim = new Animation(framesWheel, 15);

		bikePaused = false;
		hint = "Press \"ESC\" to return to main menu";
		/*
		 * 
		 */
	}

	public int getRowPixel(int row) {
		return 100 + row * 25;
	}

	public int getColPixel() {
		return 50;
	}

	public int getHintX(String hint) {
		return (int) this.game.fontDefault.getWidth(hint);
	}

	public int getHintY(String hint) {
		return (int) ((bikeDimensions.y) / 2)
				- (this.game.fontDefault.getHeight(hint) / 2);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		bikePaused = false;
		wheelAnim.start();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.white);

		int textWidth = this.game.fontHeader.getWidth("Credits");
		this.game.fontHeader.drawString(game.getContainer().getWidth() / 2
				- textWidth / 2, 10, "Credits", Color.white);

		// g.setColor(Color.red);
		this.game.fontDefault.drawString(getColPixel(), getRowPixel(0),
				"Â© Copyright 2013:");
		this.game.fontDefault.drawString(getColPixel() + 20, getRowPixel(1),
				"Jonas Dann");
		this.game.fontDefault.drawString(getColPixel() + 20, getRowPixel(2),
				"Malte Heinzelmann");
		this.game.fontDefault.drawString(getColPixel() + 20, getRowPixel(3),
				"Phillip Schichtel");
		this.game.fontDefault.drawString(getColPixel(), getRowPixel(4),
				"A project developed for LudumDare 2013");
		this.game.fontDefault.drawString(getColPixel(), getRowPixel(5),
				"(see Http://ludumdare.com/ for more information)");
		/*
		 * Easter EGG
		 */
		g.drawAnimation(wheelAnim, bikePosition.x + 100 * bikeScale,
				bikePosition.y + 707f * bikeScale);
		// g.drawAnimation(wheelAnim, bikePosition.x + 1850 * bikeScale,
		// bikePosition.y + 707f * bikeScale);
		g.drawImage(moto, bikePosition.x - 300 * bikeScale, bikePosition.y
				- 360 * bikeScale);

		/*
		 * 
		 */
		this.game.fontDefault.drawString(bikePosition.x - getHintX(hint) - 300
				* bikeScale, bikePosition.y + getHintY(hint), hint);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		/*
		 * Easter EGG
		 */
		if (waitToDrive) {
			waitedFramesDrive++;
			if (waitedFramesDrive > waitFramesToDrive) {
				waitToDrive = false;
				bikePaused = false;
				waitedFramesDrive = 0;
			}
		}
		if (!bikePaused) {
			wheelAnim.start();
			bikePosition = bikePosition.add(bikeVelocity);
			if (bikePosition.x - getHintX(hint) - 10 > game.getContainer()
					.getWidth()) {
				resetBike();
			} else if (bikePosition.x + bikeDimensions.x - 45 >= game
					.getContainer().getWidth() && !bikeWaited) {
				bikePaused = true;
				waitedFramesDrive = 0;
				waitToDrive = true;
				bikeWaited = true;
			}
		} else {
			wheelAnim.stop();
		}
		/*
		 * 
		 */
	}

	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		switch (key) {
		case Input.KEY_ESCAPE:
			resetBike();
			game.enterState(MainMenu.ID, new FadeOutTransition(Color.black),
					new FadeInTransition(Color.black));
			break;
		/*
		 * Easter EGG
		 */
		case Input.KEY_F12:
			// Easter EGG! :D
			bikePaused = !bikePaused;
			break;
		/*
				 * 
				 */
		default:
			break;
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}