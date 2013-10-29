package rero.gui.script;

import rero.bridges.event.ScriptedEventListener;
import rero.gui.IRCSession;
import rero.gui.windows.ClientWindowEvent;

import java.util.HashMap;

public class ScriptedWindowStateListener extends ScriptedEventListener {
	protected IRCSession gui;

	public ScriptedWindowStateListener(IRCSession _gui) {
		gui = _gui;
	}

	public void onWindowEvent(ClientWindowEvent ev) {
		HashMap event = new HashMap();

		event.put("$window", gui.resolveClientWindow(ev.getSource()).getName());
		dispatchEvent(event);
	}

	public void setupListener() {
		// do nothing, this will be installed by default (unfortunately)
	}
}
