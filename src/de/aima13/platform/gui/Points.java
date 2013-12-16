package de.aima13.platform.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Points extends GuiEntity {
    private int points;
    
    @Override
    public void onInit() throws SlickException {
        this.points = 0;
        move(5, 5);
    }
    
    @Override
    public void render(Graphics g) {
        getLevel().getGame().fontDefault.drawString(this.getPosition().x, this.getPosition().y, "Points: " + this.points);
    }
    
    public void addPoints(int points) {
        this.points += points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public int getPoints() {
        return this.points;
    }
}
