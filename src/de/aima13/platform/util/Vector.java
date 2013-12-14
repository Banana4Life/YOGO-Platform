package de.aima13.platform.util;

public class Vector {

	public final float a, b;

	public Vector(float a, float b) {
		this.a = a;
		this.b = b;
	}

	public Vector add(Vector v) {
		return new Vector(a + v.a, b + v.b);
	}

	public Vector sub(Vector v) {
		return new Vector(a - v.a, b - v.b);
	}

	public double len(Vector v) {
		return Math.sqrt(a * a + b * b);
	}

}
