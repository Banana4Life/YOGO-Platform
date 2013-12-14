package de.aima13.platform.util;

public enum Face
{
    TOP,
    RIGHT,
    BOTTOM,
    LEFT;

    public Face opposite()
    {
        return Face.values()[(this.ordinal() + 2) % 4];
    }
}
