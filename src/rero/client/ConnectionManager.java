package rero.client;

import rero.config.models.ServerConfig;

import java.util.HashMap;

public class ConnectionManager {
	private HashMap<ServerConfig, Connection> connections = new HashMap<ServerConfig, Connection>();

	public Connection getConnection(ServerConfig params) {
		if (connections.containsKey(params)) return connections.get(params);
		Connection connection = new Connection(params);
		connections.put(params, connection);
		return connection;
	}
}
