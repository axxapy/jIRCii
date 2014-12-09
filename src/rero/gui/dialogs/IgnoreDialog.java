package rero.gui.dialogs;

import rero.gui.dck.DMain;

public class IgnoreDialog extends DMain {
	public String getTitle() {
		return "Ignore Setup";
	}

	public String getDescription() {
		return "Ignore Mask Setup";
	}

	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();
		addLabel("The following nick/host masks will be ignored:", 30);
		addBlankSpace();
		addListInput("ignore.masks", "Ignore this mask (nick!user@host):", "Add Ignore Mask", 80, 125);
	}
}



