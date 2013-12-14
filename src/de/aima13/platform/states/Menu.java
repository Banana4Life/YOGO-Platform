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

import de.aima13.platform.entity.Motorcycle;
import de.aima13.platform.util.Vector;

public class Menu extends BasicGameState {

	public final static int ID = 2;
	private StateBasedGame game; // stored for later use

	/*
	 * Easter EGG
	 */

	private Image moto;
	private Image[] framesWheel;
	private Animation wheelAnim;
	private float bikeScale = 0.15f;
	private Vector bikeDimensions;
	private Vector bikePosition;
	private Vector bikeVelocity;
	private boolean bikeDriving = false;

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
		bikeVelocity = new Vector(15f, 0);
		bikeDriving = false;
	}

	/*
	 * 
	 */

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;
		/*
		 * Easter EGG
		 */
		resetBike();

		moto = new Image("res/svg/motorcycle-wireframe.png")
				.getScaledCopy(bikeScale);

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

		wheelAnim = new Animation(framesWheel, 10);
		/*
		 * 
		 */
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.drawString("Higher or Lower", 50, 10);

		g.drawString("1. Play Game", 50, 100);
		g.drawString("2. High Scores", 50, 120);
		g.drawString("3. Quit", 50, 140);
		/*
		 * Easter EGG
		 */
		g.drawAnimation(wheelAnim, bikePosition.x + 100 * bikeScale,
				bikePosition.y + 707f * bikeScale);
		g.drawAnimation(wheelAnim, bikePosition.x + 1850 * bikeScale,
				bikePosition.y + 707f * bikeScale);
		g.drawImage(moto, bikePosition.x, bikePosition.y);
		/*
		 * 
		 */
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		/*
		 * Easter EGG
		 */
		if (bikeDriving) {
			wheelAnim.start();
			bikePosition = bikePosition.add(bikeVelocity);
			if (bikePosition.x > game.getContainer().getWidth()) {
				resetBike();
			}
		} else {
			wheelAnim.stop();
		}
		/*
		 * 
		 */
	}

	@Override
	public int getID() {
		return ID;
	}

	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_1:
			game.enterState(Game.ID, new FadeOutTransition(Color.black),
					new FadeInTransition(Color.black));
			break;
		case Input.KEY_2:
			// TODO: Implement later
			break;
		case Input.KEY_3:
			System.exit(0);
			break;
		/*
		 * Easter EGG
		 */
		case Input.KEY_F12:
			// Easter EGG! :D
			if (bikeDriving) {
				bikeDriving = false;
			} else {
				bikeDriving = true;
			}
			break;
		/*
			 * 
			 */
		default:
			break;
		}
	}

}
