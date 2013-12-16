package de.aima13.platform.util;

public class Box {
    public static final Box ZERO = new Box(Vector.ZERO, Vector.ZERO);
    
    private final Vector    base;
    private final Vector    size;
    
    public Box(Vector base, Vector size) {
        this.base = base;
        this.size = size;
    }
    
    public Box(Vector base, float width, float height) {
        this(base, new Vector(width, height));
    }
    
    public Box(float width, float height) {
        this(Vector.ZERO, width, height);
    }
    
    public Vector[] getCorners() {
        Vector[] corners = new Vector[4];
        corners[0] = this.base;
        corners[1] = this.base.add(this.size.x, 0);
        corners[2] = this.base.add(this.size);
        corners[3] = this.base.add(0, this.size.y);
        
        return corners;
    }
    
    public Vector getBase() {
        return this.base;
    }
    
    public float getWidth() {
        return this.size.x;
    }
    
    public float getHeight() {
        return this.size.y;
    }
    
    public Box absolute(Vector pos) {
        return new Box(getBase().add(pos), this.size);
    }
    
    public boolean contains(Vector v) {
        return v.x >= base.x && v.x < base.x + size.x && v.y >= base.y && v.y < base.y + size.y;
    }
    
    public Face intersects(Box r) {
        Vector[] corners = this.getCorners();
        for (int i = 0; i < corners.length; ++i) {
            if (r.contains(corners[i])) {
                return Face.values()[i];
            }
        }
        return null;
    }
}
