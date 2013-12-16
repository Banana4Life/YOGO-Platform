package de.aima13.platform;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.util.ResourceLoader;

import de.aima13.platform.states.Credits;
import de.aima13.platform.states.Game;
import de.aima13.platform.states.Loose;
import de.aima13.platform.states.MainMenu;
import de.aima13.platform.states.Pause;
import de.aima13.platform.util.CheatEngine;

public class PlatformGame extends StateBasedGame {
    
    public CheatEngine  cheatEngine;
    
    /** The fonts to draw to the screen */
    public TrueTypeFont fontDefault;
    public TrueTypeFont fontHeader;
    
    private GameLevel   level;
    
    public Image        imageColorA, imageColorB;
    public Animation    shaderColor;
    public boolean      shaderColorActive;
    
    public Color        globalTextColor = Color.white;
    
    public PlatformGame() {
        super("#YOGO Platform");
    }
    
    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        level = new GameLevel(this, container.getInput());
        level.init();
        addState(new MainMenu());
        addState(new Pause());
        addState(new Game());
        addState(new Credits());
        addState(new Loose());
        init();
    }
    
    public void init() throws SlickException {
        fontDefault = loadFont("res/font/minecraft.ttf");
        fontHeader = loadFont("res/font/minecraft.ttf", 54f);
        imageColorA = new Image("res/images/shader/color-a.png");
        imageColorB = new Image("res/images/shader/color-b.png");
        shaderColor = new Animation(new Image[] { imageColorA, imageColorB }, 25);
        cheatEngine = new CheatEngine(this);
        shaderColorActive = false;
    }
    
    public TrueTypeFont loadFont(String res) {
        return loadFont(res, Font.PLAIN);
    }
    
    public TrueTypeFont loadFont(String res, float size) {
        return loadFont(res, size, Font.PLAIN);
    }
    
    public TrueTypeFont loadFont(String res, int style) {
        return loadFont(res, 18f, style);
    }
    
    public TrueTypeFont loadFont(String res, float size, int style) {
        return loadFont(res, size, style, true);
    }
    
    public TrueTypeFont loadFont(String res, float size, int style, boolean antiAlias) {
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream(res);
            
            Font ttf = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            ttf = ttf.deriveFont(style, size);
            return new TrueTypeFont(ttf, antiAlias);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // @Override
    // public void keyPressed(int key, char c) {
    // super.keyPressed(key, c);
    // }
    
    @Override
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        if (getCurrentStateID() == MainMenu.ID) {
            cheatEngine.onKeyPress(key, c);
        }
    }
    
    public GameLevel getLevel() {
        return level;
    }
    
    public void lose() {
        enterState(Loose.ID, new EmptyTransition(), new FadeInTransition(Color.black));
        try {
            ((Game) getState(Game.ID)).resetState();
        } catch (SlickException e) {
            e.printStackTrace(System.err);
        }
    }
    
    public static void main(String[] args) {
        try {
            // System.setProperty("org.lwjgl.opengl.Window.undecorated",
            // "true");
            AppGameContainer appgc;
            appgc = new AppGameContainer(new PlatformGame());
            appgc.setDisplayMode(Math.round(640 * appgc.getAspectRatio()), Math.round(appgc.getScreenHeight() - (150 * appgc.getAspectRatio())), false);
            appgc.setAlwaysRender(true);
            appgc.setTargetFrameRate(60);
            appgc.setShowFPS(false);
            appgc.start();
        } catch (SlickException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
