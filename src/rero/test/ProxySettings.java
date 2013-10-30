package rero.test;

import rero.config.ClientDefaults;
import rero.config.ClientState;
import rero.config.ClientStateListener;

public class ProxySettings implements ClientStateListener {
	private static ProxySettings settings;

	public static void initialize() {
		settings = new ProxySettings();
	}

	public void propertyChanged(String key, String value) {
		setup();
	}

	public ProxySettings() {
		ClientState.getInstance().addClientStateListener("proxy.enabled", this);
		setup();
	}

	public void setup() {
		if (ClientState.getInstance().isOption("proxy.enabled", false)) {
			System.setProperty("socksProxyHost", ClientState.getInstance().getString("proxy.server", ClientDefaults.proxy_server));
			System.setProperty("socksProxyPort", ClientState.getInstance().getString("proxy.port", ClientDefaults.proxy_port));
			System.setProperty("java.net.socks.username", ClientState.getInstance().getString("proxy.userid", ClientDefaults.proxy_userid));
			System.setProperty("java.net.socks.password", ClientState.getInstance().getString("proxy.password", ClientDefaults.proxy_password));
		} else {
			System.setProperty("socksProxyHost", "");
			System.setProperty("socksProxyPort", "");
			System.setProperty("java.net.socks.username", "");
			System.setProperty("java.net.socks.password", "");
		}
	}
}

