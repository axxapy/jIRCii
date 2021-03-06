package rero.gui.toolbar;

import rero.bridges.menu.MenuBridge;
import rero.client.Capabilities;
import rero.gui.SessionManager;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class ConnectAction implements ToolAction {
	public void actionPerformed(MouseEvent ev) {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		if (client.isConnected()) {
			SessionManager.getGlobalCapabilities().getActiveSession().executeCommand("/QUIT");
		} else {
			MenuBridge menuManager = (MenuBridge) client.getDataStructure("menuBridge");

			JPopupMenu menu = menuManager.getPrimaryPopup("&Connection");

			if (menu != null) {
				menu.show((JComponent) ev.getComponent(), ev.getX(), ev.getY());
				ev.consume();
			}
		}
	}

	public String getDescription() {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		if (client.isConnected()) {
			return "Disconnect from server";
		} else {
			return "Connect to a server";
		}
	}

	public int getIndex() {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		if (client.isConnected()) {
			return 1;
		} else {
			return 0;
		}
	}
}
