package rero.client;

import rero.config.models.ServerConfig;

import java.util.LinkedList;

public class ConnectionsManager {
	private LinkedList<InternetRelayChatClient> connections = new LinkedList<InternetRelayChatClient>();

	public InternetRelayChatClient getConnection(int id) {
		return connections.size() > 0 && connections.size() < id ? connections.get(id) : null;
	}

	public InternetRelayChatClient newConnection(ServerConfig params) {
		int id = connections.size() + 1;
		InternetRelayChatClient client = new InternetRelayChatClient(id);
		connections.add(client);
		return client;
	}
}
