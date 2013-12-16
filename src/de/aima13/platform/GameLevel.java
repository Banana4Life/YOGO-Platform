package de.aima13.platform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import de.aima13.platform.entity.Entity;
import de.aima13.platform.gui.TiledBackground;
import de.aima13.platform.gui.TiledScrollingBackground;
import de.aima13.platform.util.Box;
import de.aima13.platform.util.Face;
import de.aima13.platform.util.Vector;

public class GameLevel {
    private static final Vector      G = new Vector(0, 9.81f).scale(.01f);
    
    private final PlatformGame       game;
    private final GameContainer      container;
    private final LinkedList<Entity> entities;
    private final Input              input;
    
    private Vector                   gravity;
    private boolean                  collisionsEnabled;
    
    private TiledScrollingBackground background;
    private Sound                    plasmaSound, jumpSound, deathSound;
    
    public GameLevel(PlatformGame game, Input input) throws SlickException {
        this.game = game;
        this.container = game.getContainer();
        this.collisionsEnabled = true;
        
        this.input = input;
        entities = new LinkedList<Entity>();
    }
    
    public void toggleCollisionsEnabled() {
        this.collisionsEnabled = !this.collisionsEnabled;
    }
    
    public void setCollisionsEnabled(boolean state) {
        this.collisionsEnabled = state;
    }
    
    public boolean isCollisionsEnabled() {
        return collisionsEnabled;
    }
    
    public void init() throws SlickException {
        SpriteSheet sheet = new SpriteSheet("res/images/background/BackgroundTileset.png", 32, 32);
        
        background = new TiledScrollingBackground(sheet, new Vector(0, 2));
        background.init(game);
        
        plasmaSound = new Sound("res/sound/plasma.wav");
        jumpSound = new Sound("res/sound/jump.wav");
        deathSound = new Sound("res/sound/death.wav");
        
        this.gravity = G;
    }
    
    public void setBackgroundVelocity(Vector v) {
        this.background.setVelocity(v);
    }
    
    public <T extends Entity> T spawn(T e) {
        this.addEntity(e);
        return e;
    }
    
    public Vector getGravity() {
        return gravity;
    }
    
    public void setGravity(Vector gravity) {
        this.gravity = gravity;
    }
    
    protected void addEntity(Entity entity) {
        try {
            entity.init(game);
            this.entities.addLast(entity);
        } catch (SlickException e) {
            e.printStackTrace(System.err);
        }
    }
    
    public void reset() {
        entities.clear();
    }
    
    public final void update(int delta) {
        this.onUpdate(delta);
        List<Entity> remove = new ArrayList<Entity>();
        for (Entity e : new ArrayList<Entity>(this.entities)) {
            if (!e.isAlive() || outOfRange(e)) {
                remove.add(e);
                e.onDeath();
                continue;
            }
            
            e.preUpdate(delta);
            e.relativeMove(e.getVelocity());
            e.accelerate(e.getAcceleration().add(this.gravity.scale(e.getGravityScale())));
            
            e.update(delta);
        }
        
        this.entities.removeAll(remove);
        
        this.detectCollisions();
    }
    
    private boolean outOfRange(Entity e) {
        return (e.getPosition().y > getHeight() * 2 || e.getPosition().x > getWidth() * 2);
    }
    
    public void onUpdate(int delta) {
        background.update(delta);
    }
    
    public final void render(Graphics g) {
        this.onRender(g);
        for (Entity entity : entities) {
            entity.render(g);
        }
    }
    
    private void onRender(Graphics g) {
        background.render(g);
    }
    
    public PlatformGame getGame() {
        return this.game;
    }
    
    public GameContainer getContainer() {
        return container;
    }
    
    public Input getInput() {
        return input;
    }
    
    public int getWidth() {
        return getContainer().getWidth();
    }
    
    public int getHeight() {
        return getContainer().getHeight();
    }
    
    public Sound getPlasmaSound() {
        return plasmaSound;
    }
    
    public Sound getJumpSound() {
        return jumpSound;
    }
    
    public Sound getDeathSound() {
        return deathSound;
    }
    
    private void detectCollisions() {
        if (!isCollisionsEnabled()) {
            return;
        }
        Set<Long> checked = new HashSet<Long>();
        for (Entity a : this.entities) {
            for (Entity b : this.entities) {
                if (a == b) {
                    continue;
                }
                long combinedHash = (long) a.hashCode() + (long) b.hashCode();
                if (checked.contains(combinedHash)) {
                    continue;
                }
                checked.add(combinedHash);
                
                Face collFace = this.checkCollision(a, b);
                if (collFace != null) {
                    a.onCollide(b, collFace);
                    b.onCollide(a, collFace.opposite());
                }
            }
        }
        
        for (Entity entity : this.entities) {
            Box bb = entity.getAbsBB();
            if (bb == null) {
                continue;
            }
            Vector b = bb.getBase();
            if (b.x < 0) {
                entity.onCollideWithBorder(Face.LEFT);
            }
            if (b.y < 0) {
                entity.onCollideWithBorder(Face.TOP);
            }
            if (b.x + bb.getWidth() > getWidth()) {
                entity.onCollideWithBorder(Face.RIGHT);
            }
            if (b.y + bb.getHeight() > getHeight()) {
                entity.onCollideWithBorder(Face.BOTTOM);
            }
        }
    }
    
    private Face checkCollision(Entity entityA, Entity entityB) {
        Box a = entityA.getAbsBB();
        Box b = entityB.getAbsBB();
        
        if (a == null || b == null) {
            return null;
        }
        
        Face collFace = b.intersects(a);
        if (collFace != null) {
            return collFace;
        }
        
        return null;
    }
}
