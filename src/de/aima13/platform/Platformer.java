package de.aima13.platform;

public class Platformer {

	public class SimpleSlickGame extends BasicGame {
		/** The message to be displayed */
		private String message = "Press any key, mouse button, or drag the mouse";
		/** True if the mouse button is down */
		private boolean buttonDown;
		/** The x position of our controlled stuff */
		private float x;
		/** The y position of our controlled stuff */
		private float y;
		/** The current color index */
		private int index;
		/** The input syste being polled */
		private Input input;
		/** The scroll box */
		private int ypos;
		/** The container holding this test */
		private AppGameContainer app;

		/** True if space is down */
		private boolean space;
		/** True if left shift is down */
		private boolean lshift;
		/** True if right shift is down */
		private boolean rshift;

		Image img, wheel;
		Motorcycle moto;

		Image[] framesWheel;
		Animation wheelanim;

		private float fastMove = 2.5f;

		private float imgx, imgy;

		protected Vec2 gravity = new Vec2(0.0f, 10.0f);
		protected float timeStep = 1.0f / 50.f;
		protected int velocityIterations = 1;
		protected int positionIterations = 1;
		protected World world = new World(gravity);
		protected Body wheelBody, bikeBody;
		protected boolean turnWheelRight = false;

		private float wheelScale = 0.1f;

		/**
		 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
		 */
		public void init(GameContainer container) throws SlickException {
			if (container instanceof AppGameContainer) {
				app = (AppGameContainer) container;
			}

			img = new Image("res/images/html.jpg");
			moto = new Motorcycle("res/svg/motorcycle-wireframe.png");
			wheel = new Image("res/svg/wheel.png");
			wheel = wheel.getScaledCopy(wheelScale);

			framesWheel = new Image[10];
			framesWheel[0] = new Image("res/svg/frames-wheel/frame01.png")
					.getScaledCopy(wheelScale);
			framesWheel[1] = new Image("res/svg/frames-wheel/frame02.png")
					.getScaledCopy(wheelScale);
			framesWheel[2] = new Image("res/svg/frames-wheel/frame03.png")
					.getScaledCopy(wheelScale);
			framesWheel[3] = new Image("res/svg/frames-wheel/frame04.png")
					.getScaledCopy(wheelScale);
			framesWheel[4] = new Image("res/svg/frames-wheel/frame05.png")
					.getScaledCopy(wheelScale);
			framesWheel[5] = new Image("res/svg/frames-wheel/frame06.png")
					.getScaledCopy(wheelScale);
			framesWheel[6] = new Image("res/svg/frames-wheel/frame07.png")
					.getScaledCopy(wheelScale);
			framesWheel[7] = new Image("res/svg/frames-wheel/frame08.png")
					.getScaledCopy(wheelScale);
			framesWheel[8] = new Image("res/svg/frames-wheel/frame09.png")
					.getScaledCopy(wheelScale);
			framesWheel[9] = new Image("res/svg/frames-wheel/frame10.png")
					.getScaledCopy(wheelScale);

			wheelanim = new Animation(framesWheel, 20);

			input = container.getInput();

			x = 300;
			y = 300;

			// body definition
			BodyDef bd = new BodyDef();
			bd.type = BodyType.DYNAMIC;
			bd.position.set(0, 0);

			// define shape of the wheel.
			CircleShape cs = new CircleShape();
			cs.m_radius = 37.5f;

			PolygonShape ps = new PolygonShape();

			
			ps.setAsBox(250.1f, 127.7f);
			

			// define fixture of the body.
			FixtureDef fd = new FixtureDef();
			fd.shape = ps;
			fd.density = 0.5f;
			fd.friction = 0f;
			fd.restitution = 0.5f;

			bikeBody = world.createBody(bd);
			bikeBody.createFixture(fd);

			fd.friction = 2.9f;
			bd.position.set(10f, 70.7f);

			fd.shape = cs;
			fd.density = 0.5f;
			fd.friction = 3f;
			fd.restitution = 0.5f;
			// create the body and add fixture to it
			wheelBody = world.createBody(bd);
			wheelBody.createFixture(fd);

			WheelJointDef def = new WheelJointDef();
			def.initialize(bikeBody, wheelBody, wheelBody.getWorldCenter(),
					new Vec2());

			PolygonShape floorShape = new PolygonShape();
			floorShape.setAsBox(600, 10);

			fd = new FixtureDef();
			fd.shape = floorShape;

			bd = new BodyDef();
			bd.position = new Vec2(0.0f, 480f);

			world.createBody(bd).createFixture(fd);

			floorShape = new PolygonShape();
			floorShape.setAsBox(10, 600);

			fd = new FixtureDef();
			fd.shape = ps;

			bd = new BodyDef();
			bd.position = new Vec2(300f, 0f);

			world.createBody(bd).createFixture(fd);
		}

		/**
		 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer,
		 *      org.newdawn.slick.Graphics)
		 */
		public void render(GameContainer container, Graphics g) {
			g.drawString("left shift down: " + lshift, 100, 240);
			g.drawString("right shift down: " + rshift, 100, 260);
			g.drawString("space down: " + space, 100, 280);

			g.setColor(Color.white);
			g.drawString(message, 10, 50);
			g.drawString("" + container.getInput().getMouseY(), 10, 400);
			g.drawString(
					"Use the primary gamepad to control the blob, and hit a gamepad button to change the color",
					10, 90);

			for (int i = 0; i < lines.size(); i++) {
				Line line = (Line) lines.get(i);
				line.draw(g);
			}

			g.setColor(cols[index]);
			g.fillOval((int) x, (int) y, 50, 50);
			g.setColor(Color.yellow);
			g.fillRect(50, 200 + ypos, 40, 40);

			g.setColor(Color.cyan);
			g.fillOval(wheelBody.getPosition().x, wheelBody.getPosition().y, 20, 20);
			g.drawString("Velocity: " + wheelBody.getLinearVelocity(), 200, 400);
			g.setColor(Color.red);
			g.fillOval(bikeBody.getPosition().x,
					bikeBody.getPosition().y, bikeBody.get, 20);
			g.drawImage(moto.getTile(), bikeBody.getPosition().x,
					bikeBody.getPosition().y);
			g.setColor(Color.black);
//			g.fillOval(
//					wheelBody.getPosition().x + 9f + 5f
//							* (float) Math.sin(wheelBody.getAngle()),
//					wheelBody.getPosition().y + 9f - 5f
//							* (float) Math.cos(wheelBody.getAngle()), 2f, 2f);
			// g.fillRect(0, 0, 400, 400);

			g.drawAnimation(wheelanim, wheelBody.getPosition().x,
					wheelBody.getPosition().y);
			// g.drawAnimation(wheelanim, imgx + 10, imgy + 70.7f);
			// g.drawAnimation(wheelanim, imgx + 185, imgy + 70.7f);
		}

		private boolean anyControllerPressed(boolean[] controller) {
			for (int i = 0; i < controller.length; i++) {
				if (controller[i])
					return true;
			}
			return false;
		}

		/**
		 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
		 *      int)
		 */
		public void update(GameContainer container, int delta) {
			lshift = container.getInput().isKeyDown(Input.KEY_LSHIFT);
			rshift = container.getInput().isKeyDown(Input.KEY_RSHIFT);
			space = container.getInput().isKeyDown(Input.KEY_SPACE);

			if (anyControllerPressed(controllerLeft)) {
				x -= delta
						* 0.1f
						* ((controllerButton[5] != null && controllerButton[5][1]) ? fastMove
								: 1);
			}
			if (anyControllerPressed(controllerRight)) {
				x += delta
						* 0.1f
						* ((controllerButton[5] != null && controllerButton[5][1]) ? fastMove
								: 1);
			}
			if (anyControllerPressed(controllerUp)) {
				y -= delta
						* 0.1f
						* ((controllerButton[5] != null && controllerButton[5][1]) ? fastMove
								: 1);
			}
			if (anyControllerPressed(controllerDown)) {
				y += delta
						* 0.1f
						* ((controllerButton[5] != null && controllerButton[5][1]) ? fastMove
								: 1);
			}

			if (turnWheelRight) {
				// wheelBody.applyLinearImpulse(new Vec2(100, 0), new Vec2(100,
				// 100));
				wheelBody.applyAngularImpulse(100);
			}

			world.step(timeStep, velocityIterations, positionIterations);
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
			if (key == Input.KEY_D) {
				turnWheelRight = true;
			}
		}

		/**
		 * @see org.newdawn.slick.BasicGame#keyReleased(int, char)
		 */
		public void keyReleased(int key, char c) {
			message = "You pressed key code " + key + " (character = " + c + ")";
			if (key == Input.KEY_D) {
				turnWheelRight = false;
			}
		}

		/**
		 * @see org.newdawn.slick.BasicGame#mousePressed(int, int, int)
		 */
		public void mousePressed(int button, int x, int y) {
			if (button == 0) {
				buttonDown = true;
			}

			message = "Mouse pressed " + button + " " + x + "," + y;
		}

		/**
		 * @see org.newdawn.slick.BasicGame#mouseReleased(int, int, int)
		 */
		public void mouseReleased(int button, int x, int y) {
			if (button == 0) {
				buttonDown = false;
			}

			message = "Mouse released " + button + " " + x + "," + y;
		}

		/**
		 * @see org.newdawn.slick.BasicGame#mouseClicked(int, int, int, int)
		 */
		public void mouseClicked(int button, int x, int y, int clickCount) {
			System.out.println("CLICKED:" + x + "," + y + " " + clickCount);
		}

		/**
		 * @see org.newdawn.slick.BasicGame#mouseWheelMoved(int)
		 */
		public void mouseWheelMoved(int change) {
			message = "Mouse wheel moved: " + change;

			if (change < 0) {
				ypos -= 10;
			}
			if (change > 0) {
				ypos += 10;
			}
		}

		/**
		 * @see org.newdawn.slick.BasicGame#mouseMoved(int, int, int, int)
		 */
		public void mouseMoinputved(int oldx, int oldy, int newx, int newy) {
			if (buttonDown) {
				lines.add(new Line(oldx, oldy, newx, newy));
			}
		}

		/**
		 * A line that has been drawn by the user
		 * 
		 * @author kevin
		 */
		private class Line {
			/** The start x position */
			private int oldx;
			/** The start y position */
			private int oldy;
			/** The end x position */
			private int newx;
			/** The end y position */
			private int newy;

			/**
			 * Create a new line
			 * 
			 * @param oldx
			 *            The start x position
			 * @param oldy
			 *            The start y position
			 * @param newx
			 *            The end x position
			 * @param newy
			 *            The end y position
			 */
			public Line(int oldx, int oldy, int newx, int newy) {
				this.oldx = oldx;
				this.oldy = oldy;
				this.newx = newx;
				this.newy = newy;
			}

			/**
			 * Draw the line to the provided graphics context
			 * 
			 * @param g
			 *            The graphics context on which to draw the line
			 */
			public void draw(Graphics g) {
				g.drawLine(oldx, oldy, newx, newy);
			}
		}

		/**
		 * @see org.newdawn.slick.BasicGame#controllerButtonPressed(int, int)
		 */
		public void controllerButtonPressed(int controller, int button) {
			super.controllerButtonPressed(controller, button);
			index++;
			index %= cols.length;
		}

		public SimpleSlickGame(String gamename) {
			super(gamename);
		}

		public static void main(String[] args) {
			try {
				AppGameContainer appgc;
				appgc = new AppGameContainer(new SimpleSlickGame(
						"Simple Slick Game"));
				appgc.setDisplayMode(640, 480, false);
				appgc.start();
			} catch (SlickException ex) {
				Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	
}
