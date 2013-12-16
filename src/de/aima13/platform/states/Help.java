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
import de.aima13.platform.gui.OnHighlightSelectListener;
import de.aima13.platform.highscore.Score;
import de.aima13.platform.util.Vector;

public class Help extends BasicGameState {
    
    public final static int ID = 8;
    
    protected PlatformGame  game;
    
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
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        
        g.setColor(Color.white);
        
        this.game.fontHeader.drawString(game.getContainer().getWidth() / 2 - this.game.fontHeader.getWidth("Help") / 2, 10, "Help", Color.white);
        
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(0), "In #YOGO Platform you have to keep");
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(1), "the player jumping. For him to jump on");
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(2), "your platform you have to activate it!");
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(3), "Activating your platform costs you energy.");
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(4), "To get more energy, collect yellow batteries.");
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(5), "Attention: Grey batteries a broken. Don't collect!");
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(6), "If the player hits a battery he destroys it!");
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(8), "For every jump and battery you'll get points!");
        
        int keyboardRow = 10;
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(keyboardRow), "Arrow keys:");
        this.game.fontDefault.drawString(getColPixel() + 50, getRowPixel(keyboardRow + 1), "Move you platform left and right");
        
        
        this.game.fontDefault.drawString(getColPixel(), getRowPixel(keyboardRow +3), "\"SPACE\":");
        this.game.fontDefault.drawString(getColPixel() + 50, getRowPixel(keyboardRow + 4), "Activate your platform. But remember:");
        this.game.fontDefault.drawString(getColPixel() + 50, getRowPixel(keyboardRow + 5), "it needs energy and has a cooldown time!");
        
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
