package de.aima13.platform.states;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.aima13.platform.PlatformGame;
import de.aima13.platform.highscore.HighscoreManager;
import de.aima13.platform.highscore.Score;

public class EnterHighscore extends BasicGameState {
    
    public final static int  ID         = 7;
    
    private static final int MAX_LENGTH = 20;
    
    protected PlatformGame   game;
    private ArrayList<Score> scores;
    private String           enteredName;
    private boolean          underscoreVisible;
    private int              framesDelay;
    
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        if (game instanceof PlatformGame) {
            this.game = (PlatformGame) game;
        } else {
            throw new SlickException("StateBaseGame isn't a PlatformGame!");
        }
        
    }
    
    public int getRowPixel(int row) {
        return 200 + row * 25;
    }
    
    public int getColPixel() {
        return 50;
    }
    
    public void resetState() {
        enteredName = "";
        underscoreVisible = false;
        resetDelay();
    }
    
    public void resetDelay() {
        framesDelay = 60;
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        resetState();
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        
        this.game.fontHeader.drawString(game.getContainer().getWidth() / 2 - this.game.fontHeader.getWidth("You lost your!") / 2, 10, "You lost your", Color.white);
        this.game.fontHeader.drawString(game.getContainer().getWidth() / 2 - this.game.fontHeader.getWidth("ONLY") / 2, 70, "ONLY", Color.white);
        this.game.fontHeader.drawString(game.getContainer().getWidth() / 2 - this.game.fontHeader.getWidth("platform") / 2, 130, "platform", Color.white);
        
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(0), "Score:");
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(1), "" + this.game.currentPoints.getPoints());
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(3), "Name:");
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(4), enteredName + (underscoreVisible ? "_" : ""));
        
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (framesDelay > 0) {
            framesDelay--;
        } else if (framesDelay == 0) {
            underscoreVisible = !underscoreVisible;
            resetDelay();
        }
    }
    
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        if (Character.isAlphabetic(c) && enteredName.length() <= MAX_LENGTH) {
            enteredName += c;
        }
        switch (key) {
            case Input.KEY_BACK:
                if (enteredName.length() > 0) {
                    enteredName = enteredName.substring(0, enteredName.length() - 1);
                }
                break;
            case Input.KEY_ENTER:
                if (enteredName.length() > 0) {
                    this.game.getHighscoreManager().addScore(enteredName, this.game.currentPoints.getPoints());
                    game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                }
                break;
            default:
                break;
        }
    }
    
    @Override
    public int getID() {
        return ID;
    }
    
}