package de.aima13.platform.util;

public class Vector {

	public final float x, y;

	public Vector(float a, float b) {
		this.x = a;
		this.y = b;
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
