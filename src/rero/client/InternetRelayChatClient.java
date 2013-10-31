package rero.client;

import rero.client.script.ScriptManager;
import rero.gui.UICapabilities;
import rero.ircfw.ChatFramework;
import rero.net.SocketSystem;
import rero.script.ScriptCore;

import java.util.WeakHashMap;

public class InternetRelayChatClient {
	private int id;

	protected WeakHashMap data = new WeakHashMap();   // Client central data repository.

	//
	// Frameworks and Such
	//
	protected ChatFramework ircfw = new ChatFramework();
	protected SocketSystem sock = new SocketSystem();
	protected ScriptCore script = new ScriptCore();

	protected Capabilities actions;

	public InternetRelayChatClient(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void init(UICapabilities gui) {
		ircfw.storeDataStructures(data);
		sock.storeDataStructures(data);
		script.storeDataStructures(data);

		//
		// import systems into eachother.
		//
		script.announceFramework(ircfw);  // this has to be done early on to get the bridges to be created.

		sock.addSocketDataListener(ircfw.getProtocolHandler()); /* socket events are fired in a first in first out fashion.
															  /* so the framework will be the last thing to touch the socket
                                                              /* event... */

		actions = new Capabilities(ircfw, sock.getSocket(), script, gui, data);
	}

	public void post() {
		//
		// do other fun stuff... i.e. script loading and such
		//
		((ScriptManager) getCapabilities().getDataStructure(DataStructures.ScriptManager)).loadScripts();
	}

	public Capabilities getCapabilities() {
		return actions;
	}
	public WeakHashMap getData() {
		return data;
	}
}
