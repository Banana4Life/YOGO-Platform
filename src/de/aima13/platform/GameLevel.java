package de.aima13.platform;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import de.aima13.platform.entity.Entity;

public class GameLevel {
	private final LinkedList<Entity> entities;
	private final GameContainer container;

	public GameLevel(GameContainer container) {
		this.container = container;
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

	public int getWidth() {
		return getContainer().getWidth();
	}

	public int getHeight() {
		return getContainer().getHeight();
	}
}
