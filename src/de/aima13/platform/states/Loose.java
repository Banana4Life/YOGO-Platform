package de.aima13.platform.states;

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
import de.aima13.platform.util.Vector;

public class Loose extends Menu {
    
    public final static int ID     = 5;
    
    private int             column = 100;
    
    public Loose() {
        super(ID);
    }
    
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        super.init(container, game);
        setHighlightWidth(10);
        setHighlightScale(7);
        addHighlightEntry(new Vector(column, 250 - 2), new OnHighlightSelectListener() {
            
            @Override
            public void onSelect(StateBasedGame game) {
                game.enterState(Game.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        });
        addHighlightEntry(new Vector(column, 275 - 2), new OnHighlightSelectListener() {
            
            @Override
            public void onSelect(StateBasedGame game) throws SlickException {
                ((Game) game.getState(Game.ID)).resetState();
                game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            
        });
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);
        g.setColor(Color.white);
        
        this.game.fontHeader.drawString(game.getContainer().getWidth() / 2 - this.game.fontHeader.getWidth("You lost your!") / 2, 10, "You lost your", Color.white);
        
        this.game.fontHeader.drawString(game.getContainer().getWidth() / 2 - this.game.fontHeader.getWidth("ONLY") / 2, 70, "ONLY", Color.white);
        
        this.game.fontHeader.drawString(game.getContainer().getWidth() / 2 - this.game.fontHeader.getWidth("platform") / 2, 130, "platform", Color.white);
        
        // g.setColor(Color.red);
        this.game.fontDefault.drawString(column, 250, "Start a new game");
        this.game.fontDefault.drawString(column, 275, "Exit to Main Menu");
        
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        
    }
    
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        switch (key) {
            case Input.KEY_ESCAPE:
                // game.enterState(Game.ID, new FadeOutTransition(Color.black),
                // new FadeInTransition(Color.black));
                break;
            default:
                break;
        }
    }
    
}
