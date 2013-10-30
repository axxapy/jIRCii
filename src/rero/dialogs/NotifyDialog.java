package rero.dialogs;

import rero.gui.dck.DMain;

public class NotifyDialog extends DMain {
	public String getTitle() {
		return "Notify Setup";
	}

	public String getDescription() {
		return "Notify List Setup";
	}

	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();
		addLabel("The following users are on your notify list:", 30);
		addBlankSpace();
		addListInput("notify.users", "Add Notify User", "User to add to notify list?", 80, 125);
	}
}



