package rero.gui.components.toolbar;

import rero.client.Capabilities;
import rero.gui.SessionManager;

import java.awt.event.MouseEvent;

public class ActionList implements Action {
	public void actionPerformed(MouseEvent ev) {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		SessionManager.getGlobalCapabilities().getActiveSession().executeCommand("/list -gui");
	}

	public String getDescription() {
		return "List Channels";
	}

	public int getIndex() {
		return 7;
	}
}
