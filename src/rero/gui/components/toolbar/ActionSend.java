package rero.gui.components.toolbar;

import rero.client.Capabilities;
import rero.gui.SessionManager;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class ActionSend implements Action {
	public void actionPerformed(MouseEvent ev) {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		String nick = JOptionPane.showInputDialog(SessionManager.getGlobalCapabilities().getFrame(), "Send a file to:", "DCC Send", JOptionPane.QUESTION_MESSAGE);

		if (nick != null)
			SessionManager.getGlobalCapabilities().getActiveSession().executeCommand("/DCC send " + nick);
	}

	public String getDescription() {
		return "Send a file via DCC";
	}

	public int getIndex() {
		return 22;
	}
}
