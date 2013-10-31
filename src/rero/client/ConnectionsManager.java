package rero.client;

import rero.config.models.ServerConfig;

import java.util.HashMap;

public class ConnectionsManager {
	private HashMap<ServerConfig, Client> connections = new HashMap<ServerConfig, Client>();

	public Client getConnection(ServerConfig params) {
		if (connections.containsKey(params)) return connections.get(params);
		Client client = new Client(params);
		connections.put(params, client);
		return client;
	}
}
