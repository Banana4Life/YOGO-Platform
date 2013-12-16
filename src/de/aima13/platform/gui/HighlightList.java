package de.aima13.platform.gui;

import java.util.ArrayList;

public class HighlightList extends ArrayList<HiglightListEntry> {
    
    /**
     * 
     */
    private static final long serialVersionUID = -5571628692866529849L;
    
    private int width;
    
    public HighlightList() {
        
    }
    
    public HighlightList(int width) {
        this.width = width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getWidth() {
        return width;
    }
}
