package rero.dialogs;

import rero.config.ClientDefaults;
import rero.gui.dck.DGroup;
import rero.gui.dck.DItem;
import rero.gui.dck.DMain;
import rero.gui.dck.items.CheckboxInput;

import java.awt.*;

public class LoggingDialog extends DMain {
	public String getTitle() {
		return "Setup Logs";
	}

	public String getDescription() {
		return "Message Logging Setup";
	}

	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();

		DGroup temp = addDialogGroup(new DGroup("Logging Options", 15) {
			public void setupDialog() {


				addBlankSpace();
				DItem tempc = addDirectoryInput("log.saveto", ClientDefaults.log_saveto, "Log Directory: ", 'D', 10);

				addBlankSpace();

				DItem tempa = addCheckboxInput("log.strip", ClientDefaults.log_strip, "Strip colors from text", 'S', FlowLayout.LEFT);
				DItem tempb = addCheckboxInput("log.timestamp", ClientDefaults.log_timestamp, "Timestamp logged messages", 'T', FlowLayout.LEFT);
				addBlankSpace();
			}
		});

		addBlankSpace();

		CheckboxInput boxed = addCheckboxInput("log.enabled", ClientDefaults.log_enabled, "Enable Logging", 'E', FlowLayout.CENTER);
		boxed.addDependent(temp);

		addBlankSpace();
	}
}
