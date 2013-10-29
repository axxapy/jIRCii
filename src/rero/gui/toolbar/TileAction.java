package rero.gui.toolbar;

import rero.gui.SessionManager;
import rero.gui.mdi.ClientDesktop;

import java.awt.event.MouseEvent;


public class TileAction implements ToolAction {
	public void actionPerformed(MouseEvent ev) {
		((ClientDesktop) SessionManager.getGlobalCapabilities().getActiveSession().getDesktop()).tileWindows();
	}

	public String getDescription() {
		return "Tile Windows";
	}

	public int getIndex() {
		return 31;
	}
}
