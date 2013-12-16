package de.aima13.platform.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.aima13.platform.gui.OnHighlightSelectListener;
import de.aima13.platform.util.Vector;

public class MainMenu extends Menu {
    
    public final static int ID          = 2;
    public Image            title;
    int                     column      = 100;
    protected Sound         cheatSound;
    
    /*
     * Easter EGG
     */
    
    private Image           moto;
    private Image[]         framesWheel;
    private Animation       wheelAnim;
    private float           bikeScale   = 0.05f;
    private Vector          bikeDimensions;
    private Vector          bikePosition;
    private Vector          bikeVelocity;
    private boolean         bikeDriving = false;
    private boolean         bikePaused  = false;
    private float           spacing;
    private int             rows;
    private float           spl;
    private float           rand_factor = 30;
    
    /*
	 * 
	 */
    
    private boolean         cheatSwitch;
    private int             showCheatFrames;
    
    public MainMenu() {
        super(ID);
    }
    
    /*
     * Easter EGG
     */
    private void resetBike() {
        bikeDimensions = new Vector(2600f * bikeScale, 1470f * bikeScale + 0f); // add
                                                                                // 10
                                                                                // pixels
                                                                                // spacing
                                                                                // for
                                                                                // the
                                                                                // street
                                                                                // ;)
        bikePosition = new Vector(0 - bikeDimensions.x, game.getContainer().getHeight() - bikeDimensions.y);
        bikeVelocity = new Vector(15f, 0);
        bikePaused = false;
        bikeDriving = false;
    }
    
    /*
	 * 
	 */
    
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        super.init(container, game);
        
        title = new Image("res/images/background/Title.png");
        title.setFilter(Image.FILTER_NEAREST);
        
        cheatSound = new Sound("res/sound/cheat.wav");
        
        /*
         * Easter EGG
         */
        resetBike();
        
        moto = new Image("res/svg/motorcycle-wireframe.png").getScaledCopy(bikeScale);
        
        framesWheel = new Image[10];
        framesWheel[0] = new Image("res/svg/frames-wheel/frame01.png").getScaledCopy(bikeScale);
        framesWheel[1] = new Image("res/svg/frames-wheel/frame02.png").getScaledCopy(bikeScale);
        framesWheel[2] = new Image("res/svg/frames-wheel/frame03.png").getScaledCopy(bikeScale);
        framesWheel[3] = new Image("res/svg/frames-wheel/frame04.png").getScaledCopy(bikeScale);
        framesWheel[4] = new Image("res/svg/frames-wheel/frame05.png").getScaledCopy(bikeScale);
        framesWheel[5] = new Image("res/svg/frames-wheel/frame06.png").getScaledCopy(bikeScale);
        framesWheel[6] = new Image("res/svg/frames-wheel/frame07.png").getScaledCopy(bikeScale);
        framesWheel[7] = new Image("res/svg/frames-wheel/frame08.png").getScaledCopy(bikeScale);
        framesWheel[8] = new Image("res/svg/frames-wheel/frame09.png").getScaledCopy(bikeScale);
        framesWheel[9] = new Image("res/svg/frames-wheel/frame10.png").getScaledCopy(bikeScale);
        
        wheelAnim = new Animation(framesWheel, 10);
        
        spacing = game.getContainer().getHeight() % bikeDimensions.y;
        rows = (int) ((game.getContainer().getHeight() - spacing) / bikeDimensions.y);
        spl = spacing / (rows - 1); // space per line
        
        /*
		 * 
		 */
        
        setHighlightWidth(8);
        setHighlightScale(7);
        addHighlightEntry(new Vector(column, 200 - 2), new OnHighlightSelectListener() {
            
            @Override
            public void onSelect(StateBasedGame game) {
                game.enterState(Game.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        });
        addHighlightEntry(new Vector(column, 225 - 2), new OnHighlightSelectListener() {
            
            @Override
            public void onSelect(StateBasedGame game) {
                game.enterState(Highscores.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        });
        ;
        addHighlightEntry(new Vector(column, 250 - 2), new OnHighlightSelectListener() {
            
            @Override
            public void onSelect(StateBasedGame game) {
                game.enterState(Credits.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        });
        addHighlightEntry(new Vector(column, 275 - 2), new OnHighlightSelectListener() {
            
            @Override
            public void onSelect(StateBasedGame game) {
                game.getContainer().exit();
            }
            
        });
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);
        g.setColor(Color.white);
        
        // int textWidth = this.game.fontHeader.getWidth("Main Menu");
        // this.game.fontHeader.drawString(game.getContainer().getWidth() / 2
        // - textWidth / 2, 10, "Main Menu", Color.white);
        
        title.draw(0, 0, 2);
        
        this.game.fontDefault.drawString(column, 200, "Play Game", this.game.globalTextColor);
        this.game.fontDefault.drawString(column, 225, "High Scores", this.game.globalTextColor);
        this.game.fontDefault.drawString(column, 250, "Credits", this.game.globalTextColor);
        this.game.fontDefault.drawString(column, 275, "Quit", this.game.globalTextColor);
        /*
         * Easter EGG
         */
        for (int i = 0; i < rows; i++) {
            float x_offset = (bikeDriving) ? (i % 2 == 0) ? rand_factor * i : -rand_factor * i : 0;
            g.drawAnimation(wheelAnim, bikePosition.x + 100 * bikeScale + x_offset, 707f * bikeScale + i * bikeDimensions.y + i * spl);
            if (i % 2 == 0) {
                g.drawAnimation(wheelAnim, bikePosition.x + 1850 * bikeScale + x_offset, 707f * bikeScale + i * bikeDimensions.y + i * spl);
            }
            g.drawImage(moto, bikePosition.x + x_offset, i * bikeDimensions.y + i * spl);
        }
        
        /*
		 * 
		 */
        if (showCheatFrames > 0) {
            String cheat = "Cheat " + (cheatSwitch ? "" : "de") + "activated!";
            this.game.fontDefault.drawString(5, this.game.getContainer().getHeight() - this.game.fontDefault.getHeight(cheat) - 5, cheat, this.game.globalTextColor);
            showCheatFrames--;
        }
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        /*
         * Easter EGG
         */
        if (bikeDriving && !bikePaused) {
            wheelAnim.start();
            bikePosition = bikePosition.add(bikeVelocity);
            if (bikePosition.x - rand_factor * (rows - 1) > game.getContainer().getWidth()) {
                resetBike();
            }
        } else {
            wheelAnim.stop();
        }
        /*
		 * 
		 */
    }
    
    @Override
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        switch (key) {
        /*
         * Easter EGG
         */
            case Input.KEY_F12:
                // Easter EGG! :D
                if (bikeDriving) {
                    bikePaused = !bikePaused;
                } else {
                    bikeDriving = true;
                    bikePaused = false;
                }
                break;
            /*
			 * 
			 */
            default:
                break;
        }
    }
    
    public void showCheatActivated(boolean switched) {
        cheatSwitch = switched;
        showCheatFrames = 120;
        cheatSound.play();
    }
    
}
