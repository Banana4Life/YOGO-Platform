package de.aima13.platform;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new PlatformGame());
            app.setDisplayMode(Math.round(640 * app.getAspectRatio()), Math.round(app.getScreenHeight() - (150 * app.getAspectRatio())), false);
            app.setAlwaysRender(true);
            app.setTargetFrameRate(60);
            app.setShowFPS(false);
            app.setSoundVolume(.5f);
            app.setMusicVolume(.5f);
            app.start();
        } catch (SlickException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
