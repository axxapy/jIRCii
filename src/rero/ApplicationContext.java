package rero;

import rero.client.ConnectionsManager;

public class ApplicationContext {
	ConnectionsManager mConnectionsManager = new ConnectionsManager();

	public ApplicationContext() {}

	public ConnectionsManager getConnectionsManager() {
		return mConnectionsManager;
	}

	public void executeCommand(String command) {
		//((UserHandler) getCapabilities().getDataStructure("commands")).processCommand(command);
	}
}
