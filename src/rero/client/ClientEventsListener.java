package rero.client;

public interface ClientEventsListener {
	public void onConnected(Connection connection);
	public void onDisconnect(Connection connection);
	public void onMsgReceived(Connection connection, String msg);
}
