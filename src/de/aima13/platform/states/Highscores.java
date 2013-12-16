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

public class Highscores extends BasicGameState {
    
    public final static int  ID = 6;
    protected PlatformGame   game;  // stored for later use
    private ArrayList<Score> scores;
    
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        if (game instanceof PlatformGame) {
            this.game = (PlatformGame) game;
        } else {
            throw new SlickException("StateBaseGame isn't a PlatformGame!");
        }
        
    }
    
    public int getRowPixel(int row) {
        return 100 + row * 25;
    }
    
    public int getColPixel() {
        return 50;
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        HighscoreManager hm = this.game.getHighscoreManager();
        System.out.println(hm.getHighscoreString());
        scores = hm.getScores();
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        
        int textWidth = this.game.fontHeader.getWidth("Highscores");
        this.game.fontHeader.drawString(game.getContainer().getWidth() / 2 - textWidth / 2, 10, "Highscores", Color.white);
        
        for (int row = 0; row < 10; row++) {
            if (row < scores.size()) {
                Score s = scores.get(row);
                this.game.fontDefault.drawString(getColPixel(), getRowPixel(row), (row + 1) + ".");
                this.game.fontDefault.drawString(getColPixel() + 50, getRowPixel(row), s.getNaam());
                this.game.fontDefault.drawString(getColPixel() + 50 + this.game.fontDefault.getWidth("A") * 22, getRowPixel(row), "" + s.getScore());
            } else {
                this.game.fontDefault.drawString(getColPixel(), getRowPixel(row), (row + 1) + ".");
                this.game.fontDefault.drawString(getColPixel() + 50, getRowPixel(row), "-");
            }
        }
        
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        
    }
    
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        switch (key) {
            case Input.KEY_ESCAPE:
                game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
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