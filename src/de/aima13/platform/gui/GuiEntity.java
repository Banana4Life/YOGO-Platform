package de.aima13.platform.gui;

import de.aima13.platform.entity.Entity;
import de.aima13.platform.util.Box;
import de.aima13.platform.util.Face;

public class GuiEntity extends Entity {
    public GuiEntity() {
        super();
        this.setGravityScale(0);
    }
    
    @Override
    public final void setBB(Box boundingBox) {
    }
    
    @Override
    public final void onCollide(Entity target, Face collidedFace) {
    }
    
    @Override
    public final void onCollideWithBorder(Face collidedFace) {
    }
}
