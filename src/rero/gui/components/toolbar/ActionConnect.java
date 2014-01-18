package rero.gui.components.toolbar;

import rero.bridges.menu.MenuBridge;
import rero.client.Capabilities;
import rero.gui.SessionManager;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class ActionConnect implements Action {
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
		if (SessionManager.getGlobalCapabilities() == null) return "@todo: need to be fixed";
		try {
			Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();
			return client.isConnected() ? "Disconnect from server" : "Connect to a server";
		} catch (NullPointerException ex) {
			return "Connect to a server";
		}
	}

	public int getIndex() {
		if ( SessionManager.getGlobalCapabilities() == null) return 0;
		try {
			Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();
			return client.isConnected() ? 1 : 0;
		} catch (NullPointerException ex) {
			return 0;
		}
	}
}
