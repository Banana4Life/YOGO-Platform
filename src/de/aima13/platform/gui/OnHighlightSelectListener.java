package de.aima13.platform.gui;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public interface OnHighlightSelectListener {

	void onSelect(StateBasedGame game) throws SlickException;
}
