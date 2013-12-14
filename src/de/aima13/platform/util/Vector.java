package de.aima13.platform.util;

public class Vector {

	public final float x, y;

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y);
	}

	public Vector sub(Vector v) {
		return new Vector(x - v.x, y - v.y);
	}

	public double len(Vector v) {
		return Math.sqrt(x * x + y * y);
	}
	
}
