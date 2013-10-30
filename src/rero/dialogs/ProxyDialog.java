package rero.dialogs;

import rero.config.ClientDefaults;
import rero.gui.dck.DGroup;
import rero.gui.dck.DMain;
import rero.gui.dck.items.CheckboxInput;

import java.awt.*;

public class ProxyDialog extends DMain {
	public String getTitle() {
		return "Proxy Setup";
	}

	public String getDescription() {
		return "SOCKS Proxy Settings";
	}

	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();

		DGroup temp = addDialogGroup(new DGroup("SOCKS Proxy Settings", 30) {
			public void setupDialog() {
				addStringInput("proxy.server", ClientDefaults.proxy_server, "  Hostname: ", 'h', 60);
				addStringInput("proxy.port", ClientDefaults.proxy_port, "  Port:     ", 'o', 120);
				addBlankSpace();
				addStringInput("proxy.userid", ClientDefaults.proxy_userid, "  Username: ", 'u', 60);
				addStringInput("proxy.password", ClientDefaults.proxy_password, "  Password: ", 'p', 60);
			}
		});

		CheckboxInput boxed = addCheckboxInput("proxy.enabled", false, "Use Proxy Server", 'e', FlowLayout.CENTER);
		boxed.addDependent(temp);
	}
}



