package de.aima13.platform.gui;

import de.aima13.platform.entity.Entity;
import de.aima13.platform.util.Face;

public class GuiEntity extends Entity
{
    public GuiEntity()
    {
        super();
        this.setGravityScale(0);
    }

    @Override
    public final void onCollide(Entity current, Face collidedFace)
    {}

    @Override
    public final void onCollideWithBorder(Face collidedFace)
    {}
}
