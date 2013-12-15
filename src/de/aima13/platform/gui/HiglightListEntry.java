package de.aima13.platform.gui;

import de.aima13.platform.util.Vector;

public class HiglightListEntry {

	public final Vector position;
	public final OnHighlightSelectListener listener;

	public HiglightListEntry(Vector position, OnHighlightSelectListener listener) {
		this.position = position.sub(20, 0);
		this.listener = listener;
	}
}
