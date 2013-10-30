package rero.gui.windows;

import rero.config.ClientState;
import rero.config.ClientStateListener;
import rero.config.Config;

import javax.swing.*;
import java.awt.*;

public class ListBoxOptions implements ClientStateListener {
	protected JComponent container;
	protected JComponent listbox;

	public ListBoxOptions(JComponent c, JComponent l) {
		container = c;
		listbox = l;

		ClientState.getInstance().addClientStateListener("listbox.position", this);
		ClientState.getInstance().addClientStateListener("listbox.enabled", this);

		rehash();
	}

	public void rehash() {
		synchronized (listbox) {
			container.remove(listbox);

			boolean enabled = Config.getInstance().isOption("listbox.enabled", true);
			int position = Config.getInstance().getInteger("listbox.position", 1); // default to right...

			if (enabled) {
				if (position == 0) {
					container.add(listbox, BorderLayout.WEST);
				} else {
					container.add(listbox, BorderLayout.EAST);
				}
			}
		}
	}

	public void propertyChanged(String key, String value) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				rehash();
				container.revalidate();
			}
		});
	}
}

