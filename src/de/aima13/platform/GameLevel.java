package de.aima13.platform;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import de.aima13.platform.entity.Entity;

public class GameLevel {
	private final LinkedList<Entity> entities;
	private final GameContainer container;
	private final Input input;

	public GameLevel(GameContainer container, Input input) {
		this.container = container;
		this.input = input;
		entities = new LinkedList<>();
	}

	public void addEntity(Entity entity) {
		this.entities.addLast(entity);
		entity.init(this);
	}

	public final void update(GameContainer container, int delta) {
		for (Entity entity : entities) {
			entity.update(delta);
		}
		this.onUpdate(container, delta);
	}

	public void onUpdate(GameContainer container, int delta) {

	}

	public final void render(GameContainer container, Graphics g) {
		for (Entity entity : entities) {
			entity.render(g);
		}
		this.onRender(g);
	}

	private void onRender(Graphics g) {

	}

	public GameContainer getContainer() {
		return container;
	}

	public Input getInput() {
		return input;
	}

	public int getWidth() {
		return getContainer().getWidth();
	}

	public int getHeight() {
		return getContainer().getHeight();
	}
}
