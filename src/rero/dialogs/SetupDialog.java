package rero.dialogs;

import rero.config.ServersList;
import rero.gui.dck.DGroup;
import rero.gui.dck.DItem;
import rero.gui.dck.DMain;
import rero.gui.dck.items.ServerList;

import javax.swing.*;
import java.awt.*;

public class SetupDialog extends DMain {
	protected ServersList data = ServersList.getInstance();
	protected DItem itema, itemb;

	public String getTitle() {
		return "jIRCii Setup";
	}

	public String getDescription() {
		return "Setup jIRCii";
	}

	public JComponent getDialog() {
		JPanel dialog = new JPanel();

		setupLayout(dialog);
		setupDialog();

		dialog.add(itema.getComponent(), BorderLayout.CENTER);
		dialog.add(itemb.getComponent(), BorderLayout.SOUTH);

		return dialog;
	}

	public JComponent setupLayout(JComponent component) {
		component.setLayout(new BorderLayout(3, 3));
		component.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		return component;
	}

	public void setupDialog() {
		itema = addOther(new ServerList(data, 0, 150, getCapabilities()));

		itemb = addDialogGroup(new DGroup("User Information", 0) {
			public void setupDialog() {
				addStringInput("user.rname", "", " Real Name:  ", 'R', 10);
				addStringInput("user.email", "", " E-mail:  ", 'E', 60);
				addStringInput("user.nick", "", " Nickname:   ", 'N', 60);
				addStringInput("user.altnick", "", " Alt. Nick:  ", 'A', 60);
			}
		});
	}
}



