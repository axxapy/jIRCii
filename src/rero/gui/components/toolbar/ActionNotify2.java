package rero.gui.components.toolbar;

import rero.gui.SessionManager;

import java.awt.event.MouseEvent;

public class ActionNotify2 implements Action {
	public void actionPerformed(MouseEvent ev) {
		SessionManager.getGlobalCapabilities().showOptionDialog("Notify Setup");
	}

	public String getDescription() {
		return "Edit Notify List";
	}

	public int getIndex() {
		return 26;
	}
}
