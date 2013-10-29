package rero.dialogs;

import rero.config.ClientDefaults;
import rero.dck.DGroup;
import rero.dck.DMain;

public class IRCOptions extends DMain {
	public String getTitle() {
		return "IRC Options";
	}

	public String getDescription() {
		return "IRC Options";
	}

	public void setupDialog() {
		addBlankSpace();

		addDialogGroup(new DGroup("Show in active...", 15) {
			public void setupDialog() {
				addCheckboxInput("active.reply", ClientDefaults.active_option, "CTCP Replies", 'l');
				addCheckboxInput("active.ctcp", ClientDefaults.active_option, "CTCP Requests", 'q');
				addCheckboxInput("active.notice", ClientDefaults.active_option, "Notices", 'n');
				addCheckboxInput("active.notify", ClientDefaults.active_option, "Notify Signon/off", 's');
				addCheckboxInput("active.query", ClientDefaults.active_option, "Queries", 'q');
				addCheckboxInput("active.whois", ClientDefaults.active_option, "Whois Information", 'w');
			}
		});

		addBlankSpace();

		addCheckboxInput("option.showmotd", ClientDefaults.option_showmotd, "Show MOTD", 'M');
		addCheckboxInput("option.timestamp", ClientDefaults.option_timestamp, "Timestamp Events.", 'a');
		addCheckboxInput("client.stripcodes", ClientDefaults.client_stripcodes, "Strip formatting codes from IRC messages", 'f');
	}
}



