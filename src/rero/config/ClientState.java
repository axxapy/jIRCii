package rero.config;

import java.io.*;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ClientState {
	protected static HashMap listeners = new HashMap();
	// ^-- container for the listeners for property changes.  This way
	// components won't be checking themselves all the time, they
	// can be notified when something happens.
	// listeners{"property.name"} = LinkedList ( WeakReference(listener1),
	//                                           WeakReference(null),
	//                                           WeakReference(listener2))
	// The weak references are for a server connection that gets closed,
	// this way those can be garbage collected.  Trying to make jIRC more
	// memory friendly.

	// REPLACE: Using .jIRCdevel directory for devel so it doesn't interfere with non-development jIRCii. (replace to .jIRC)
	protected static File baseDirectory = new File(System.getProperty("user.home"), ".config/jIRC");

	public static void setBaseDirectory(String directory) {
		baseDirectory = new File(directory);
	}

	public void fireChange(String property) {
		fireChange(property, null);
	}

	public void addClientStateListener(String property, ClientStateListener l) {
		LinkedList temp = (LinkedList) listeners.get(property);
		if (temp == null) {
			temp = new LinkedList();
			listeners.put(property, temp);
		}

		temp.add(new WeakReference(l));
	}

	public void fireChange(String property, String parameter) {
		if (listeners.get(property) == null) {
			return; // ain't no thang sh'bang.
		}

		Iterator i = ((LinkedList) listeners.get(property)).iterator();
		while (i.hasNext()) {
			WeakReference temp = (WeakReference) i.next();
			if (temp.get() == null) {
				i.remove();
			} else {
				ClientStateListener l = (ClientStateListener) temp.get();
				l.propertyChanged(property, parameter);
			}
		}
	}

	public static InputStreamReader getProperInputStream(InputStream stream) {
		if (Config.getInstance().getString("client.encoding", rero.gui.dck.items.CharsetInput.DEFAULT_CHARSET).equals(rero.gui.dck.items.CharsetInput.DEFAULT_CHARSET)) {
			return new InputStreamReader(stream);
		} else {
			try {
				return new InputStreamReader(stream, Config.getInstance().getString("client.encoding", rero.gui.dck.items.CharsetInput.DEFAULT_CHARSET));
			} catch (Exception ex) {
				ex.printStackTrace();
				return new InputStreamReader(stream);
			}
		}
	}

	public static PrintStream getProperPrintStream(OutputStream stream) {
		if (Config.getInstance().getString("client.encoding", rero.gui.dck.items.CharsetInput.DEFAULT_CHARSET).equals(rero.gui.dck.items.CharsetInput.DEFAULT_CHARSET)) {
			return new PrintStream(stream, true);
		} else {
			try {
				return new PrintStream(stream, true, Config.getInstance().getString("client.encoding", rero.gui.dck.items.CharsetInput.DEFAULT_CHARSET));
			} catch (Exception ex) {
				ex.printStackTrace();
				return new PrintStream(stream, true);
			}
		}
	}

	public static File getBaseDirectory() {
		if (!baseDirectory.exists() || !baseDirectory.isDirectory()) {
			baseDirectory.delete();
			baseDirectory.mkdirs();
		}

		return baseDirectory;
	}

	private static ClientState instance = null;

	public static ClientState getInstance() {
		if (instance == null) {
			instance = new ClientState();
		}
		return instance;
	}

	private ClientState() {}

	// TODO: These will be per-operating system; right now it does just OS X, but it will also check the value for Windows and Linux.
	// Returns whether or not a notification preference is enabled for private messages.
	public boolean attentionEnabledMsg() {
		return Config.getInstance().isOption("option.attention.osx.bouncedock.msg", ClientDefaults.attention_osx_bouncedock_msg);
	}

	// Returns whether or not a notification preference is enabled for private notices.
	public boolean attentionEnabledNotice() {
		return Config.getInstance().isOption("option.attention.osx.bouncedock.notice", ClientDefaults.attention_osx_bouncedock_notice);
	}

	// Returns whether or not a notification preference is enabled for channel chat/notices
	public boolean attentionEnabledChannelChat() {
		return Config.getInstance().isOption("option.attention.osx.bouncedock.channelchat", ClientDefaults.attention_osx_bouncedock_channelchat);
	}

	// Returns whether or not a notification preference is enabled for server disconnect and kills, kicks from channels
	public boolean attentionEnabledActions() {
		return Config.getInstance().isOption("option.attention.osx.bouncedock.actions", ClientDefaults.attention_osx_bouncedock_actions);
	}
}

