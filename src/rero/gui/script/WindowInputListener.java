package rero.gui.script;

import rero.bridges.event.ScriptedEventListener;
import rero.gui.input.InputListener;
import rero.gui.input.UserInputEvent;
import rero.util.ClientUtils;

import java.util.HashMap;

public class WindowInputListener extends ScriptedEventListener implements InputListener {
	public void onInput(UserInputEvent ev) {
		HashMap eventData = ClientUtils.getEventHashMap("-", ev.text);

		if (dispatchEvent(eventData) == rero.ircfw.interfaces.ChatListener.EVENT_HALT) {
			ev.consume();
		}
	}

	public void setupListener() {
		// already setup by default *shrug*
	}
}
