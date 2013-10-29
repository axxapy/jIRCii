package rero.gui.toolbar;

import rero.gui.SessionManager;

import java.awt.event.MouseEvent;

public class AboutAction implements ToolAction {
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
