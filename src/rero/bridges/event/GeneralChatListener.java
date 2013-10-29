package rero.bridges.event;

import java.util.HashMap;

public class GeneralChatListener extends EventChatListener {
	protected String eventId;

	public GeneralChatListener(String id) {
		eventId = id.toUpperCase();
	}

	public boolean isChatEvent(String _eventId, HashMap eventDescription) {
		return eventId.equals(_eventId.toUpperCase());
	}
}
