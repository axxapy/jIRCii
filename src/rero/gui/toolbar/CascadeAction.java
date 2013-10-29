package rero.gui.toolbar;

import rero.gui.SessionManager;
import rero.gui.mdi.ClientDesktop;

import java.awt.event.MouseEvent;


public class CascadeAction implements ToolAction {
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
