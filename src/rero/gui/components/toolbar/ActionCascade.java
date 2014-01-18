package rero.gui.components.toolbar;

import rero.gui.SessionManager;
import rero.gui.mdi.ClientDesktop;

import java.awt.event.MouseEvent;


public class ActionCascade implements Action {
	public void actionPerformed(MouseEvent ev) {
		((ClientDesktop) SessionManager.getGlobalCapabilities().getActiveSession().getDesktop()).cascadeWindows();
	}

	public String getDescription() {
		return "Cascade Windows";
	}

	public int getIndex() {
		return 32;
	}
}
