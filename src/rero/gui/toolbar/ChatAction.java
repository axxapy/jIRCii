package rero.gui.toolbar;

import rero.client.Capabilities;
import rero.gui.SessionManager;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class ChatAction implements ToolAction {
	public void actionPerformed(MouseEvent ev) {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		String nick = JOptionPane.showInputDialog(SessionManager.getGlobalCapabilities().getFrame(), "Request dcc chat from:", "DCC Chat", JOptionPane.QUESTION_MESSAGE);

		if (nick != null)
			SessionManager.getGlobalCapabilities().getActiveSession().executeCommand("/DCC chat " + nick);
	}

	public String getDescription() {
		return "Request a DCC Chat";
	}

	public int getIndex() {
		return 23;
	}
}
