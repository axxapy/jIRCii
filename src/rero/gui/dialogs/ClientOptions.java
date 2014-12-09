package rero.gui.dialogs;

import rero.config.ClientDefaults;
import rero.gui.dck.DMain;

public class ClientOptions extends DMain {
	public String getTitle() {
		return "Client Options";
	}

	public String getDescription() {
		return "Client Options";
	}

	public void setupDialog() {
		addBlankSpace();

		addCheckboxInput("update.ial", ClientDefaults.option_showmotd, "Update IAL on channel join", 'I');
		addCheckboxInput("option.reconnect", ClientDefaults.option_reconnect, "Auto-reconnect when disconnected", 'r');
	}
}



