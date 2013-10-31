package rero.client;

public interface ClientEventsListener {
	public void onConnected(Client client);
	public void onDisconnect(Client client);
	public void onMsgReceived(Client client, String msg);
}
