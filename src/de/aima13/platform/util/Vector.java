package de.aima13.platform.util;

public class Vector {

    public static final Vector ZERO = new Vector(0, 0);

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

	public Vector mod(float n) {
		return new Vector(this.x % n, this.y % n);
	}

	public double len(Vector v) {
		return Math.sqrt(x * x + y * y);
	}

    public Vector scale(float scaleX, float scaleY)
    {
        return new Vector(x * scaleX, y * scaleY);
    }

    public Vector scale(float n)
    {
        return this.scale(n, n);
    }
}
