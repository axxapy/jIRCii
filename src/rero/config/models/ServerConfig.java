package rero.config.models;

import com.google.gson.Gson;
import rero.util.StringParser;

import java.util.regex.Pattern;

public class ServerConfig implements Comparable {
	protected String  description;
	protected String  host;
	protected String  portRange;
	protected boolean isSSL;
	protected String  password;
	protected String  compare;

	protected static Pattern isServerPassword = Pattern.compile("(\\S)\\S*=(.*)SERVER:(.*):(.*):(.*)GROUP:(.*)");
	protected static Pattern isServerNormal   = Pattern.compile("(\\S)\\S*=(.*)SERVER:(.*):(.*)GROUP:(.*)");


	public ServerConfig() {}

	public ServerConfig setValues(String desc, String host, String port, boolean ssl, String pass) {
		this.description = desc;
		this.host = host;
		this.portRange = port;
		this.isSSL = ssl;
		this.password = pass;
		compare = host.toUpperCase();
		return this;
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

	public static ServerConfig decodeMircServer(String text) {
		// Check for server with password
		StringParser check = new StringParser(text, isServerPassword);

		if (check.matches()) {
			String[] values = check.getParsedStrings();

			boolean secure = values[0].charAt(0) == 's';

			return new ServerConfig().setValues("[" + values[5] + "] " + values[1], values[2], values[3], secure, values[4]);
		}

		// Check for server without password
		check = new StringParser(text, isServerNormal);

		if (check.matches()) {
			// 0: s
			// 1: Random US DALnet server
			// 2: irc.dal.net
			// 3: 6660-6667
			// 4: 01

			String[] values = check.getParsedStrings();

			boolean secure = values[0].charAt(0) == 's';

			return new ServerConfig().setValues("[" + values[4] + "] " + values[1], values[2], values[3], secure, null);
		}

		return null;
	}

}
