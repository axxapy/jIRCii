package rero.client.server;

import rero.client.Feature;
import rero.client.notify.NotifyData;
import rero.config.ClientDefaults;
import rero.config.ClientState;
import rero.ident.IdentDaemon;
import rero.ident.IdentListener;
import rero.ircfw.Channel;
import rero.ircfw.InternalDataList;
import rero.ircfw.User;
import rero.ircfw.interfaces.ChatListener;
import rero.ircfw.interfaces.FrameworkConstants;
import rero.net.SocketConnection;
import rero.net.SocketEvent;
import rero.net.interfaces.SocketStatusListener;
import rero.util.ClientUtils;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Responsible for the following:
 * - miscellaneous features i.e. responding to server PING's etc
 * - send altnick IF we're not connected yet and get a reply of nick in use.
 */
public class ServerHandler extends Feature implements FrameworkConstants, SocketStatusListener, ChatListener, IdentListener {
	protected NotifyData notify;
	protected InternalDataList data;
	protected SocketConnection socket;

	protected User restoreInformation = null;
	protected String restoreServer;

	protected IgnoreHandler ignoreHandler;
	protected NickInUseListener nickListener = null;

	public void init() {
		getCapabilities().getChatFramework().getProtocolDispatcher().setInternalListener(this); // make sure we are the first
		// listener to handle this stuff...
		getCapabilities().getSocketConnection().addSocketStatusListener(this);
		socket = getCapabilities().getSocketConnection();

		data = (InternalDataList) getCapabilities().getDataStructure("clientInformation");

		notify = (NotifyData) getCapabilities().getDataStructure("notify");

		ignoreHandler = new IgnoreHandler(); // handles processing for the ignore list

		IdentDaemon.getIdentDaemon().addIdentListener(this);
	}

	public void identRequest(String host, String text) {
		getCapabilities().getOutputCapabilities().fireSetStatus(ClientUtils.getEventHashMap(host, text), "IDENT_REQUEST");
	}

	public void cleanup() {
		data.reset();
		notify.reset();
		data.setMyNick("<Unknown>");

		restoreInformation = null;
	}

	public int fireChatEvent(HashMap eventDescription) {
		String event = (String) eventDescription.get($EVENT$);

		if (event.equals("376")) // end of MOTD command.
		{
			if (restoreInformation != null) {
				Iterator i = restoreInformation.getChannels().iterator();
				while (i.hasNext()) {
					Channel temp = (Channel) i.next();
					getCapabilities().sendln("JOIN " + temp.getName() + " :" + temp.getKey()); // TODO: Investigate -- is this right? With a colon before the key? Because JOIN in BuiltInCommands.java doesn't use the colon.
				}

				restoreInformation = null;
			}

			return EVENT_DONE;
		}

		if (event.equals("PING")) {
			getCapabilities().sendln("PONG :" + eventDescription.get($PARMS$));
			return EVENT_HALT;
		}

		if (ignoreHandler.isIgnore((String) eventDescription.get($NICK$), (String) eventDescription.get($ADDRESS$))) {
			return EVENT_HALT;
		} else {
			return EVENT_DONE;
		}
	}

	public boolean isChatEvent(String eventId, HashMap eventDescription) {
		if (ignoreHandler.isCheckingIgnore() && (eventId.equals("NOTICE") || eventId.equals("PRIVMSG") || eventId.equals("REPLY") || eventId.equals("REQUEST") || eventId.equals("ACTION"))) {
			return true;
		}
		if (eventId.equals("376")) {
			return true;
		}
		if (eventId.equals("PING")) {
			return true;
		}  // yeaperz, we handle PING events.

		return false;
	}

	public void socketStatusChanged(SocketEvent ev) {
		if (ev.data.isConnected) {
			getCapabilities().getOutputCapabilities().fireSetStatus(ClientUtils.getEventHashMap(ev.data.hostname, "connected"), "IRC_CONNECT");

			String[] parms = ClientState.getInstance().getString("user.email", "jircii@127.0.0.1").split("@");

			if (parms.length == 1) {
				parms = new String[]{parms[0], "127.0.0.1"};

				if (parms[0].length() == 0) {
					parms[0] = "jircii";
				}
			}

			if (ev.data.password != null) {
				getCapabilities().sendln("PASS " + ev.data.password);
			}

			String user, nick;

			if (rero.test.QuickConnect.IsQuickConnect()) {
				user = ClientState.getInstance().getString("user.rname", "jIRCii Web User: http://www.jircii.org/");
				nick = ClientState.getInstance().getString("user.nick", rero.test.QuickConnect.GetInformation().getNickname());
			} else if ((System.currentTimeMillis() % 5) == 0) // haveing some more fun...
			{
				user = ClientState.getInstance().getString("user.rname", "I'm to lame to read mIRC.hlp");
				nick = ClientState.getInstance().getString("user.nick", "madgoat");
			} else {
				user = ClientState.getInstance().getString("user.rname", ClientUtils.tagline());
				nick = ClientState.getInstance().getString("user.nick", "IRCFrEAK");
			}

			if (restoreInformation != null) {
				nick = restoreInformation.getNick();
			}

			getCapabilities().sendln("USER " + parms[0] + " " + parms[1] + " " + parms[1] + " :" + user);
			getCapabilities().sendln("NICK " + nick);

			if (nickListener == null || !nickListener.isArmed()) {
				nickListener = new NickInUseListener();
				getCapabilities().addTemporaryListener(nickListener);
			}
		} else {
			boolean isDone = false;

			getCapabilities().getOutputCapabilities().fireSetAll(ClientUtils.getEventHashMap(ev.data.hostname, ev.message), "IRC_DISCONNECT");

			if (ClientState.getInstance().attentionEnabledActions())
				ClientUtils.getAttention(); // Get attention for disconnect

			if (data.getMyUser().getChannels().size() > 0) {
				if (ClientState.getInstance().isOption("option.reconnect", ClientDefaults.option_reconnect)) {
					//System.out.println("Reconnecting is an option");

					restoreInformation = data.getMyUser().copy();
					restoreServer = ev.data.hostname;

					getCapabilities().getOutputCapabilities().fireSetAll(ClientUtils.getEventHashMap(ev.data.hostname, ev.message), "IRC_RECONNECT");
					getCapabilities().getGlobalCapabilities().setTabTitle(getCapabilities(), "reconnecting");
					socket.connect(ev.data.hostname, ev.data.port, ClientState.getInstance().getInteger("reconnect.time", ClientDefaults.reconnect_time) * 1000, ev.data.password, ev.data.isSecure);

					isDone = true;
				}
			}

			data.reset();
			notify.reset();
			data.setMyNick("<Unknown>");

			if (restoreInformation != null && !isDone) {
				getCapabilities().getOutputCapabilities().fireSetStatus(ClientUtils.getEventHashMap(ev.data.hostname, ev.message), "IRC_RECONNECT");
				getCapabilities().getGlobalCapabilities().setTabTitle(getCapabilities(), "reconnecting");
				socket.connect(ev.data.hostname, ev.data.port, ClientState.getInstance().getInteger("reconnect.time", ClientDefaults.reconnect_time) * 1000, ev.data.password, ev.data.isSecure);
			}
		}
	}

	protected class NickInUseListener implements ChatListener {
		protected String altNick = ClientState.getInstance().getString("user.altnick", "lamer" + System.currentTimeMillis());
		protected boolean armed = true;

		public boolean isChatEvent(String event, HashMap data) {
			return event.equals("001") || event.equals("433");
		}

		public boolean isArmed() {
			return armed;
		}

		public int fireChatEvent(HashMap desc) {
			String event = (String) desc.get($EVENT$);

			if (event.equals("433")) {
				if (rero.test.QuickConnect.IsQuickConnect() && rero.test.QuickConnect.GetInformation().getURL().getUserInfo() != null) {
					// set a new alternate alt nick iff a user is quick connecting and a default nickname has been specified...
					// otherwise lamer will do just fine...

					altNick = ClientState.getInstance().getString("user.nick", rero.test.QuickConnect.GetInformation().getNickname()) + System.currentTimeMillis();
				}
				getCapabilities().sendln("NICK " + altNick);
			}

			armed = false;
			return EVENT_DONE | REMOVE_LISTENER;
		}
	}
}
