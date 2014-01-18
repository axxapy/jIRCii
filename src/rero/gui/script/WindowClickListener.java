package rero.gui.script;

import rero.bridges.event.ScriptedEventListener;
import rero.gui.text.event.ClickEvent;
import rero.gui.text.event.ClickListener;

import java.util.HashMap;

public class WindowClickListener extends ScriptedEventListener implements ClickListener {
	public void wordClicked(ClickEvent ev) {
		HashMap eventData = new HashMap();

		eventData.put("$item", ev.getClickedText());
		eventData.put("$parms", ev.getContext());
		eventData.put("$data", ev.getClickedText() + " " + ev.getContext());
		eventData.put("$mouse", ev.getEvent());
		eventData.put("$clicks", new Integer(ev.getEvent().getClickCount()).toString());

		if (dispatchEvent(eventData) == rero.ircfw.interfaces.ChatListener.EVENT_HALT) {
			ev.consume();
		}
	}

	public void setupListener() {
		// already setup by default *shrug*
	}
}
