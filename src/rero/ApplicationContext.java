package rero;

import rero.client.ConnectionManager;

public class ApplicationContext {
	ConnectionManager mConnectionManager = new ConnectionManager();

	public ApplicationContext() {}

	public ConnectionManager getConnectionManager() {
		return mConnectionManager;
	}

	public void executeCommand(String command) {
		//((UserHandler) getCapabilities().getDataStructure("commands")).processCommand(command);
	}
}
