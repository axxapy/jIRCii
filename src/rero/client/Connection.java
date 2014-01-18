package rero.client;

import rero.config.models.ServerConfig;
import rero.ircfw.ChatFramework;
import rero.net.SocketConnection;
import rero.net.SocketEvent;
import rero.net.interfaces.SocketDataListener;
import rero.net.interfaces.SocketStatusListener;

import javax.swing.*;
import java.util.LinkedList;

public class Connection implements SocketStatusListener, SocketDataListener {
	private ServerConfig mServerConfig;
	private ChatFramework mChatFramework = new ChatFramework();
	private SocketConnection mSocketConnection = new SocketConnection();

	private LinkedList<ClientEventsListener> listeners = new LinkedList<ClientEventsListener>();

	JTextArea debug;

	public Connection(ServerConfig config) {
		mServerConfig = config;

		/* socket events are fired in a first in first out fashion.
		 * so the framework will be the last thing to touch the socket
		 * event... */
		mSocketConnection.addSocketDataListener(mChatFramework.getProtocolHandler());
		mSocketConnection.addSocketStatusListener(this);
		mSocketConnection.addSocketDataListener(this);

		debug = new JTextArea();
		JFrame frame = new JFrame("");
		frame.setSize(200, 200);
		frame.add(debug);
		frame.setVisible(true);
	}

	public ServerConfig getServerConfig() {
		return mServerConfig;
	}

	public void addClientEventsListener(ClientEventsListener listener) {
		if (listeners.contains(listener)) return;
		listeners.add(listener);
	}

	public boolean isConnected() {
		return mSocketConnection.getSocketInformation().isConnected;
	}

	public void Connect() {
		if (isConnected()) return;
		mSocketConnection.connect(
				mServerConfig.getHost(),
				mServerConfig.getConnectPort(),
				0,
				mServerConfig.getPassword(),
				mServerConfig.isSecure()
		);
	}

	@Override
	public void socketStatusChanged(SocketEvent ev) {
		debug.setText(debug.getText() + "\n" + ev.message);
	}

	@Override
	public void socketDataRead(SocketEvent ev) {
		debug.setText(debug.getText() + "\n" + ev.message);
	}
}
