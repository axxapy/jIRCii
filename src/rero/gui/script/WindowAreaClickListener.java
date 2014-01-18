package rero.gui.script;

import rero.bridges.event.ScriptedEventListener;
import rero.ircfw.interfaces.ChatListener;
import rero.gui.text.event.ClickEvent;
import rero.gui.text.event.ClickListener;

import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * Event listener class that handles double clicks on empty channel area
 */
public class WindowAreaClickListener extends ScriptedEventListener implements ClickListener {
	public void wordClicked(ClickEvent ev) {
		MouseEvent event = ev.getEvent();

		HashMap eventData = new HashMap();
		eventData.put("$item", ev.getContext());
		eventData.put("$mouse", event);
		eventData.put("$clicks", new Integer(event.getClickCount()).toString());

		if (dispatchEvent(eventData) == ChatListener.EVENT_HALT) {
			ev.consume();
		}
	}

	public void setupListener() {
		// already setup by default *shrug*
	}
}
