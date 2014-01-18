package rero.gui.components.toolbar;

import rero.gui.SessionManager;

import java.awt.event.MouseEvent;

public class ActionAbout implements Action {
	public void actionPerformed(MouseEvent ev) {
		if (ev.isShiftDown() && ev.isControlDown()) {
			SessionManager.getGlobalCapabilities().showCoolAbout();
		} else {
			SessionManager.getGlobalCapabilities().showAboutDialog();
		}
	}

	public String getDescription() {
		return "About: jIRCii, the ultimate irc client";
	}

	public int getIndex() {
		return 36;
	}
}
