package rero.gui.toolbar;

import rero.gui.SessionManager;

import java.awt.event.MouseEvent;

public class DCCAction implements ToolAction {
	public void actionPerformed(MouseEvent ev) {
		SessionManager.getGlobalCapabilities().showOptionDialog("DCC Options");
	}

	public String getDescription() {
		return "DCC Options";
	}

	public int getIndex() {
		return 24;
	}
}
