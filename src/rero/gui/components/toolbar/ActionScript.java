package rero.gui.components.toolbar;

import rero.gui.SessionManager;

import java.awt.event.MouseEvent;

public class ActionScript implements Action {
	public void actionPerformed(MouseEvent ev) {
		SessionManager.getGlobalCapabilities().showOptionDialog("Script Manager");
	}

	public String getDescription() {
		return "Script Manager";
	}

	public int getIndex() {
		return 10;
	}
}
