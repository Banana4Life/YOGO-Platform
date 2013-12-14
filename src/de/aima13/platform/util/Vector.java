package de.aima13.platform.util;

public class Vector {

	public final float x, y;

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector add(Vector v) {
		return add(v.x, v.y);
	}

	public Vector add(float x, float y) {
		return new Vector(this.x + x, this.y + y);
	}

	public Vector sub(Vector v) {
		return sub(v.x, v.y);
	}

	public Vector sub(float x, float y) {
		return new Vector(this.x - x, this.y - y);
	}

	public double len(Vector v) {
		return Math.sqrt(x * x + y * y);
	}
}
