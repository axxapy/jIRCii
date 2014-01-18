package rero.gui.components.toolbar;

import rero.client.Capabilities;
import rero.gui.SessionManager;

import java.awt.event.MouseEvent;

public class ActionNotify implements Action {
	public void actionPerformed(MouseEvent ev) {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		SessionManager.getGlobalCapabilities().getActiveSession().executeCommand("/NOTIFY");
	}

	public String getDescription() {
		return "Show notify list";
	}

	public int getIndex() {
		return 27;
	}
}
