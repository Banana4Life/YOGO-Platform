package de.aima13.platform.gui;

import de.aima13.platform.entity.Entity;
import de.aima13.platform.util.Face;

/**
 * Created by Phillip on 15.12.13.
 */
public class GuiEntity extends Entity
{
    public GuiEntity()
    {
        super();
        this.setCollidable(false);
    }

    @Override
    public final void onCollide(Entity current, Face collidedFace)
    {}

    @Override
    public final void onCollideWithBorder(Face collidedFace)
    {}
}
