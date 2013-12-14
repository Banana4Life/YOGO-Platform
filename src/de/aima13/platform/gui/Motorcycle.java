package de.aima13.platform.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.aima13.platform.entity.Entity;

public class Motorcycle extends Entity {
	private Image mainTile;

	public Motorcycle(String ressource) throws SlickException {
		this(ressource, Color.cyan);
	}

	public Motorcycle(String ressource, Color color) throws SlickException {
		this.mainTile = new Image(ressource).getScaledCopy(0.1f);
	}

	public void update(int delta) {
	}

	public void render(Graphics g) {
	}

	public Image getTile() {
		return mainTile;
	}
}
