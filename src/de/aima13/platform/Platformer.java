package de.aima13.platform;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import de.aima13.platform.util.Vector;

public class Platformer extends BasicGame {

	/** The input syste being polled */
	private Input input;
	/** The container holding this test */
	private AppGameContainer app;

	protected Vector gravity = new Vector(0.0f, 10.0f);
	protected float timeStep = 1.0f / 50.f;

	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		if (container instanceof AppGameContainer) {
			app = (AppGameContainer) container;
		}

		input = container.getInput();

	}

	/**
	 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		g.drawString("left shift down: ", 100, 240);
		g.drawString("right shift down: ", 100, 260);

		g.setColor(Color.white);
		g.drawString("HI", 10, 50);
		g.drawString("" + container.getInput().getMouseY(), 10, 400);
		g.drawString(
				"Use the primary gamepad to control the blob, and hit a gamepad button to change the color",
				10, 90);

		//
		// g.setColor(cols.cyan);
		// g.fillOval((int) x, (int) y, 50, 50);
		// g.setColor(Color.yellow);
		// g.fillRect(50, 200 + ypos, 40, 40);

		// g.drawAnimation(wheelanim, imgx + 10, imgy + 70.7f);
		// g.drawAnimation(wheelanim, imgx + 185, imgy + 70.7f);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
	 *      int)
	 */
	public void update(GameContainer container, int delta) {
		// lshift = container.getInput().isKeyDown(Input.KEY_LSHIFT);
		// rshift = container.getInput().isKeyDown(Input.KEY_RSHIFT);
		// space = container.getInput().isKeyDown(Input.KEY_SPACE);
		//
		// if (anyControllerPressed(controllerLeft)) {
		// x -= delta
		// * 0.1f
		// * ((controllerButton[5] != null && controllerButton[5][1]) ? fastMove
		// : 1);
		// }
		// if (anyControllerPressed(controllerRight)) {
		// x += delta
		// * 0.1f
		// * ((controllerButton[5] != null && controllerButton[5][1]) ? fastMove
		// : 1);
		// }
		// if (anyControllerPressed(controllerUp)) {
		// y -= delta
		// * 0.1f
		// * ((controllerButton[5] != null && controllerButton[5][1]) ? fastMove
		// : 1);
		// }
		// if (anyControllerPressed(controllerDown)) {
		// y += delta
		// * 0.1f
		// * ((controllerButton[5] != null && controllerButton[5][1]) ? fastMove
		// : 1);
		// }

	}

	/**
	 * @see org.newdawn.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			System.exit(0);
		}
		if (key == Input.KEY_F1) {
			if (app != null) {
				try {
					app.setDisplayMode(600, 600, false);
					app.reinit();
				} catch (Exception e) {
					Log.error(e);
				}
			}
		}
	}

	/**
	 * @see org.newdawn.slick.BasicGame#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_D) {
		}
	}

	public Platformer(String gamename) {
		super(gamename);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Platformer("Simple Slick Game"));
			appgc.setDisplayMode(640, 480, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Platformer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

}
