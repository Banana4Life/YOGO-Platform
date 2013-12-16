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

import de.aima13.platform.GameLevel;
import de.aima13.platform.PlatformGame;
import de.aima13.platform.entity.BatterySpawner;
import de.aima13.platform.entity.Creature;
import de.aima13.platform.entity.KillingFire;
import de.aima13.platform.entity.Platform;
import de.aima13.platform.gui.CooldownBar;
import de.aima13.platform.gui.Points;
import de.aima13.platform.gui.PowerBar;

public class Game extends BasicGameState {
    
    public final static int ID = 1;
    private PlatformGame    game;       // stored for later use
    private GameLevel       level;
    
    private Platform        platform;
    private KillingFire     killingFire;
    private Creature        creature;
    BatterySpawner          spawner;
    
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        if (game instanceof PlatformGame) {
            this.game = (PlatformGame) game;
        } else {
            throw new SlickException("StateBaseGame isn't a PlatformGame!");
        }
        resetState();
    }
    
    public void resetState() throws SlickException {
        this.level = game.getLevel();
        level.reset();
        
        PowerBar powerBar = level.spawn(new PowerBar());
        CooldownBar cooldownBar = level.spawn(new CooldownBar());
        Points points = level.spawn(new Points());
        platform = level.spawn(new Platform(powerBar, cooldownBar, 10));
         killingFire = level.spawn(new KillingFire());
        
        creature = level.spawn(new Creature(platform, points));
        
        spawner = level.spawn(new BatterySpawner());
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        this.level.render(g);
        if (this.game.shaderColorActive) {
            this.game.shaderColor.draw(0, 0, this.game.getContainer().getWidth(), this.game.getContainer().getHeight());
        }
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (!this.game.getContainer().hasFocus()) {
            this.game.enterState(Pause.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        this.level.update(delta);
    }
    
    @Override
    public int getID() {
        return ID;
    }
    
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_ESCAPE:
                game.enterState(Pause.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                break;
            default:
                break;
        }
    }
    
}
