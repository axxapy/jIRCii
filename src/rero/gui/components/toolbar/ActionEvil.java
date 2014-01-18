package rero.gui.components.toolbar;

import rero.client.DataStructures;
import rero.client.script.ScriptManager;
import rero.config.Config;
import rero.gui.SessionManager;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class ActionEvil implements Action {
	public void actionPerformed(MouseEvent ev) {
		if (ev.getClickCount() > 2) {
			boolean lame = !Config.getInstance().getBoolean("load.lame", false);
			String message = "";

			if (lame) {
				message = "Hunting for easter eggs?\nRight click on a nick (in the nicklist) and\nlook for an extra surprise.";
				((ScriptManager) SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities().getDataStructure(DataStructures.ScriptManager)).loadLameScripts();
			} else {
				message = "Ok, ok, that feature is not all it's cracked up\nto be.  Restart jIRCii to disable the lame menus";
			}

			JOptionPane.showMessageDialog(null, message, "Your favorite holiday...", JOptionPane.INFORMATION_MESSAGE);
			Config.getInstance().setBoolean("load.lame", lame);
		}
	}

	public String getDescription() {
		return null;
	}

	public int getIndex() {
		return 2;
	}
}
