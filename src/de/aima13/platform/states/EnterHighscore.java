package de.aima13.platform.states;

import java.util.ArrayList;

import de.aima13.platform.gui.TiledScrollingBackground;
import de.aima13.platform.util.Vector;
import org.newdawn.slick.*;
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

    private static final int CURSOR_BLINK = 15;
    
    protected PlatformGame   game;
    private ArrayList<Score> scores;
    private String           enteredName;
    private boolean          underscoreVisible;
    private int              framesDelay;
    private TiledScrollingBackground background;

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        if (game instanceof PlatformGame) {
            this.game = (PlatformGame) game;
        } else {
            throw new SlickException("StateBaseGame isn't a PlatformGame!");
        }

        SpriteSheet sheet = new SpriteSheet("res/images/background/BackgroundTileset.png", 32, 32);
        background = new TiledScrollingBackground(sheet, new Vector(0, 1));

        background.init(this.game);
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
        framesDelay = CURSOR_BLINK;
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        resetState();
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        background.render(g);
        
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
        background.update(delta);
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
