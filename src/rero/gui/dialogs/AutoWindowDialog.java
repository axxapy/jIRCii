package rero.gui.dialogs;

import rero.config.ClientDefaults;
import rero.gui.dck.DGroup;
import rero.gui.dck.DMain;

import java.awt.*;

public class AutoWindowDialog extends DMain {
	public String getTitle() {
		return "Auto /Window";
	}

	public String getDescription() {
		return "Auto Window Options";
	}

	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();

		addDialogGroup(new DGroup("Create a window when...", 15) {
			public void setupDialog() {
				addCheckboxInput("auto.join", ClientDefaults.auto_option, "I join a channel.", 'c');
				addCheckboxInput("auto.query", ClientDefaults.auto_option, "I receive a message.", 'm');
				addCheckboxInput("auto.chat", ClientDefaults.auto_option, "A dcc chat connects.", 'd');
			}
		});

		addBlankSpace();

		addDialogGroup(new DGroup("When I close a window...", 15) {
			public void setupDialog() {
				addCheckboxInput("auto.part", ClientDefaults.auto_option, "leave the channel.", 'l');
				addCheckboxInput("auto.chatclose", ClientDefaults.auto_option, "disconnect the dcc chat.", 'a');
			}
		});

		addBlankSpace();
		addCheckboxInput("auto.hide", ClientDefaults.auto_option, "Open query/chat windows minimized", 'm', FlowLayout.CENTER);
	}
}



