package rero.gui.components.toolbar;

import rero.gui.SessionManager;

import java.awt.event.MouseEvent;

public class ActionIgnore implements Action {
	public void actionPerformed(MouseEvent ev) {
		SessionManager.getGlobalCapabilities().showOptionDialog("Ignore Setup");
	}

	public String getDescription() {
		return "Edit Ignore List";
	}

	public int getIndex() {
		return 28;
	}
}
