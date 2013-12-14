package de.aima13.platform.util;

public class Rect
{
    private final Vector base;
    private final Vector size;

    public Rect(Vector base, Vector size)
    {
        this.base = base;
        this.size = size;
    }

    public Vector[] getCorners()
    {
        Vector[] corners = new Vector[4];
        corners[0] = this.base;
        corners[1] = this.base.add(this.size.x, 0);
        corners[2] = this.base.add(this.size);
        corners[3] = this.base.add(0, this.size.y);

        return corners;
    }

    public boolean contains(Vector v)
    {
        return v.x >= base.x &&
               v.x < base.x + size.x &&
               v.y >= base.y &&
               v.y < base.y + size.y;
    }

    public Face intersects(Rect r)
    {
        Vector[] corners = this.getCorners();
        for (int i = 0; i < corners.length; ++i)
        {
            if (r.contains(corners[i]))
            {
                return Face.values()[i];
            }
        }
        return null;
    }
}
