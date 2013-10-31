package rero.config.models;

import com.google.gson.Gson;

public class ServerConfig implements Comparable {
	protected String description;
	protected String host;
	protected String portRange;
	protected boolean isSSL;
	protected String password;
	protected String compare;

	public ServerConfig() {}

	public void setValues(String desc, String host, String port, boolean ssl, String pass) {
		this.description = desc;
		this.host = host;
		this.portRange = port;
		this.isSSL = ssl;
		this.password = pass;
		compare = host.toUpperCase();
	}

	public String toString() {
		return new Gson().toJson(this);
	}

	public String getCompare() {
		return compare;
	}

	public int compareTo(Object o) {
		ServerConfig arg = (ServerConfig) o;

		return getCompare().compareTo(arg.getCompare());
	}

	public String getPassword() {
		return password;
	}

	public String getPorts() {
		return portRange;
	}

	public String getHost() {
		return host;
	}

	public String getConnectPort() {
		String myPort = portRange;

		if (myPort.indexOf("-") > -1) {
			myPort = myPort.substring(0, myPort.indexOf("-"));
		}

		if (myPort.indexOf(",") > -1) {
			myPort = myPort.substring(0, myPort.indexOf(","));
		}

		return myPort.trim();
	}

	public boolean isSecure() {
		return isSSL;
	}

	public String getDescription() {
		return description;
	}

	// Build the built-in /server command to execute
	public String getCommand() {
		StringBuffer command = new StringBuffer("/server ");

		if (isSecure()) {
			command.append("-ssl ");
		}

		if (getPassword() != null && getPassword().length() > 0) {
			command.append("-pass ");
			command.append(getPassword());
			command.append(" ");
		}

		command.append(getHost());
		command.append(" ");
		command.append(getConnectPort());

		return command.toString();
	}
}
