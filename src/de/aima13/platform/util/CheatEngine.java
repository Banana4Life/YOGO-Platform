package de.aima13.platform.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

import de.aima13.platform.PlatformGame;
import de.aima13.platform.states.MainMenu;

public class CheatEngine {
    
    private final PlatformGame     game;
    private HashMap<String, Cheat> cheatList;
    private ArrayList<Cheat>       activatedCheats;
    
    private String                 currentCheat;
    public Cheat                   lastCheat;
    
    public CheatEngine(PlatformGame game) {
        this.game = game;
        cheatList = new HashMap<>();
        activatedCheats = new ArrayList<>();
        addCheat(new Cheat("a", new CheatAction() {
            
            @Override
            public void onCheat(PlatformGame game) {
                System.exit(0);
            }
        }));
        addCheat(new Cheat("steffen", new CheatAction() {
            
            @Override
            public void onCheat(PlatformGame game) {
                game.shaderColorActive = !game.shaderColorActive;
            }
        }));
        addCheat(new Cheat("malte", new CheatAction() {
            
            @Override
            public void onCheat(PlatformGame game) {
                // Go bikes, go!!!
            }
        }));
        addCheat(new Cheat("jonas", new CheatAction() {
            
            @Override
            public void onCheat(PlatformGame game) {
                game.globalTextColor = (game.globalTextColor == Color.black) ? Color.white : Color.black;
            }
        }));
        addCheat(new Cheat("phillip", new CheatAction() {
            
            @Override
            public void onCheat(PlatformGame game) {
                // kollision deaktivieren
            }
        }));
        currentCheat = "";
    }
    
    private void addCheat(Cheat cheat) {
        cheatList.put(cheat.cheat, cheat);
    }
    
    public void onKeyPress(int key, char c) {
        // Log.info("end pressed: " + ((key == Input.KEY_END) ? "true" :
        // "false"));
        if (Character.isAlphabetic(c)) {
            currentCheat += c;
        }
        if (key == Input.KEY_END) {
            // Log.info("cheat: " + currentCheat);
            Cheat foundCheat = cheatList.get(currentCheat.toLowerCase());
            if (foundCheat != null) {
                boolean isActive = activatedCheats.contains(foundCheat);
                if (isActive) {
                    activatedCheats.remove(foundCheat);
                } else {
                    activatedCheats.add(foundCheat);
                }
                foundCheat.action.onCheat(game);
                ((MainMenu) game.getState(MainMenu.ID)).showCheatActivated(!isActive);
            }
            currentCheat = "";
        }
    }
    
    public interface CheatListener extends KeyListener {
        @Override
        public void keyReleased(KeyEvent e);
    }
}
