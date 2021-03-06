package rero.client.server;

import rero.config.ClientState;
import rero.config.ClientStateListener;
import rero.config.Config;
import rero.config.StringList;
import rero.util.StringUtils;

import java.util.Iterator;

/**
 * I don't currently have an "on raw" event that fires before normal processing occurs.  So this is called directly from the
 * ServerHandler class and that determines wether or not to halt processing of the event.   Kind of a hacked way to do this but
 * it works I guess...  *shrug*
 */
public class IgnoreHandler implements ClientStateListener {
	protected boolean checkIgnore;
	protected StringList ignoreMasks;

	public void propertyChanged(String prop, String parms) {
		hash();
	}

	public void hash() {
		ignoreMasks = Config.getInstance().getStringList("ignore.masks");
		checkIgnore = ignoreMasks.getList().size() > 0;
	}

	public IgnoreHandler() {
		hash();
		ClientState.getInstance().addClientStateListener("ignore.masks", this);
	}

	public boolean isCheckingIgnore() {
		return checkIgnore;
	}

	public boolean isIgnore(String nick, String host) {
		String temp = nick + "!" + host;

		Iterator i = ignoreMasks.getList().iterator();
		while (i.hasNext()) {
			String mask = (String) i.next();
			if (StringUtils.iswm(mask, temp))
				return true;
		}

		return false;
	}
}

