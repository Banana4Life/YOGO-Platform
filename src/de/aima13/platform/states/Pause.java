package de.aima13.platform.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.aima13.platform.gui.OnHighlightSelectListener;
import de.aima13.platform.util.Vector;

public class Pause extends Menu {
    
    public final static int ID = 3;
    
    public Pause() {
        super(ID);
    }
    
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        super.init(container, game);
        setHighlightWidth(10);
        setHighlightScale(7);
        addHighlightEntry(new Vector(50, 100 - 2), new OnHighlightSelectListener() {
            public void onSelect(StateBasedGame game) {
                game.enterState(Game.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        });
        addHighlightEntry(new Vector(50, 125 - 2), new OnHighlightSelectListener() {
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
        
        int textWidth = this.game.fontHeader.getWidth("Pause Menu");
        this.game.fontHeader.drawString(game.getContainer().getWidth() / 2 - textWidth / 2, 10, "Pause Menu", this.game.globalTextColor);
        
        // g.setColor(Color.red);
        this.game.fontDefault.drawString(50, 100, "Resume Game", this.game.globalTextColor);
        this.game.fontDefault.drawString(50, 125, "Exit to Main Menu", this.game.globalTextColor);
        
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        
    }
    
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        switch (key) {
            case Input.KEY_ESCAPE:
                game.enterState(Game.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                break;
            default:
                break;
        }
    }
    
}
