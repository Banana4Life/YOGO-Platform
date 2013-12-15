package de.aima13.platform.states;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.aima13.platform.PlatformGame;
import de.aima13.platform.gui.HighlightList;
import de.aima13.platform.gui.HiglightListEntry;
import de.aima13.platform.gui.OnHighlightSelectListener;
import de.aima13.platform.gui.TiledScrollingBackground;
import de.aima13.platform.util.Vector;

public class Menu extends BasicGameState {

	protected PlatformGame game; // stored for later use

	protected Sound moveSound, selectSound;

	protected SpriteSheet engine;
	protected Animation plasmaPlatform;
	protected int scale;
	protected int width;
	protected boolean highlightActive;
	protected Vector higlightPosition;
	protected Vector offsetLeft, offsetRight;
	protected int offsetCounter;
	protected Random generator;
	protected int selectedEntry;
	protected boolean waitForExec;
	protected int waitedFramesCount;
	protected static final int waitFrames = 30;
	private TiledScrollingBackground background;

	protected HighlightList highlightEntries;

	public final int ID;

	public Menu(int id) {
		ID = id;
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		if (game instanceof PlatformGame) {
			this.game = (PlatformGame) game;
		} else {
			throw new SlickException("StateBaseGame isn't a PlatformGame!");
		}
		engine = new SpriteSheet("res/images/platform/brackets.png", 4, 3);
		engine.setFilter(Image.FILTER_NEAREST);
		plasmaPlatform = new Animation(new SpriteSheet(
				"res/images/platform/Plasma.png", 3, 3), 100);
		plasmaPlatform.start();

		scale = 6;
		higlightPosition = new Vector(0, 0);
		highlightActive = false;
		width = 10;
		offsetLeft = new Vector(0, 0);
		offsetRight = new Vector(0, 0);
		offsetCounter = 0;
		generator = new Random();
		highlightEntries = new HighlightList(10);
		selectedEntry = -1;

		moveSound = new Sound("res/sound/move.wav");
		selectSound = new Sound("res/sound/select.wav");

		SpriteSheet sheet = new SpriteSheet("res/images/background/BackgroundTileset.png", 32, 32);
		background = new TiledScrollingBackground(sheet, new Vector(0, 1));
		background.init(this.game);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		highlightActive = false;
		waitForExec = false;
		waitedFramesCount = 0;
	}

	public void setHighlightWidth(int width) {
		highlightEntries.setWidth(width);
	}

	public void setHighlightScale(int scale) {
		this.scale = scale;
	}

	public void addHighlightEntry(Vector position) {
		addHighlightEntry(position, new OnHighlightSelectListener() {
			@Override
			public void onSelect(StateBasedGame game) {
				// Do nothing
			}
		});
	}

	public void addHighlightEntry(Vector position,
			OnHighlightSelectListener listener) {
		highlightEntries.add(new HiglightListEntry(position, listener));
		if (highlightEntries.size() == 1) {
			selectedEntry = 0;
		}
	}

	public void addHighlightEntry(Vector position, int width) {
		addHighlightEntry(position);
		setHighlightWidth(width);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.render(g);
		if (selectedEntry >= 0) {
			width = highlightEntries.getWidth();
			if (highlightActive) {
				int currentFrame = plasmaPlatform.getFrame();
				for (int n = 0; n < width; n++) {
					plasmaPlatform.getImage(currentFrame).draw(
							highlightEntries.get(selectedEntry).position.x
									+ (1 + n * 3) * scale,
							highlightEntries.get(selectedEntry).position.y,
							scale);
					currentFrame += plasmaPlatform.getFrameCount() / 2;
					currentFrame %= plasmaPlatform.getFrameCount();
				}

				offsetLeft = new Vector(0, 0);
				offsetRight = new Vector(0, 0);
				offsetCounter = 9;
			}
			engine.getSubImage(0, 0).draw(
					highlightEntries.get(selectedEntry).position.x
							+ offsetLeft.x,
					highlightEntries.get(selectedEntry).position.y
							+ offsetLeft.y, scale);
			engine.getSubImage(1, 0).draw(
					highlightEntries.get(selectedEntry).position.x
							+ offsetRight.x + 3 * width * scale - 2 * scale,
					highlightEntries.get(selectedEntry).position.y
							+ offsetRight.y, scale);

			// fire.getCurrentFrame().draw(
			// highlightEntries.get(selectedEntry).position.x
			// + offsetLeft.x + 1 * scale,
			// highlightEntries.get(selectedEntry).position.y
			// + offsetLeft.y + 6 * scale, scale);
			// fire.getCurrentFrame().draw(
			// highlightEntries.get(selectedEntry).position.x
			// + offsetRight.x + 3 * width * scale - 2 * scale,
			// highlightEntries.get(selectedEntry).position.y
			// + offsetRight.y + 6 * scale, scale);
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if (waitForExec)
			return;
		if (key == Input.KEY_ENTER) {
			highlightActive = true;
			waitForExec = true;
			waitedFramesCount = 0;
			selectSound.play();
		} else {
			highlightActive = false;
			if (key == Input.KEY_UP) {
				if (selectedEntry > 0) {
					selectedEntry--;
				} else {
					selectedEntry = highlightEntries.size() - 1;
				}
				moveSound.play();
			} else if (key == Input.KEY_DOWN) {
				if (selectedEntry < highlightEntries.size() - 1) {
					selectedEntry++;
				} else {
					selectedEntry = 0;
				}
				moveSound.play();
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		background.update(delta);
		plasmaPlatform.update(delta);
		// fire.update(delta);

		offsetCounter++;
		if (offsetCounter >= 10) {
			offsetLeft = new Vector(generator.nextInt(4) - 2,
					generator.nextInt(4) - 2);
			offsetRight = new Vector(generator.nextInt(4) - 2,
					generator.nextInt(4) - 2);
		}
		offsetCounter %= 10;
		if (waitForExec) {
			waitedFramesCount++;
			if (waitForExec && waitedFramesCount > waitFrames) {
				waitForExec = false;
				waitedFramesCount = 0;
				highlightEntries.get(selectedEntry).listener.onSelect(game);
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
