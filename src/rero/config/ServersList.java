package rero.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import rero.config.models.ServerConfig;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class ServersList {
	protected static ServersList instance = null;

	protected ArrayList<ServerConfig> servers;

	protected ServersList() {
		load();
	}

	public ServerConfig getServerByHost(String host) {
		Iterator i = servers.iterator();
		while (i.hasNext()) {
			ServerConfig temp = (ServerConfig) i.next();
			if (temp.getHost().equals(host)) {
				return temp;
			}
		}

		return null;
	}

	public static ServersList getInstance() {
		if (instance == null) {
			instance = new ServersList();
		}
		return instance;
	}

	public void update() {}

	public ArrayList<ServerConfig> getServers() {
		return servers;
	}

	public void addServer(ServerConfig server) {
		servers.add(server);
	}

	public void sort() {}

	public void removeServer(ServerConfig server) {
		servers.remove(server);
	}

	public void load() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(Resources.getInstance().getResourceAsStream("servers.json")));
			StringBuilder builder = new StringBuilder();
			String line;
			while((line = in.readLine()) != null) {
				builder.append(line);
			}
			line = builder.toString();

			servers = new Gson().fromJson(line, new TypeToken<ArrayList <ServerConfig>>(){}.getType());

			in.close();
		} catch (Exception ex) {
			servers = new ArrayList<ServerConfig>();
			ex.printStackTrace();
		}
	}

	public void save() {
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(Resources.getInstance().getFile("servers.json"), false));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			out.print(gson.toJson(servers));
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String args[]) {
		ServersList temp = new ServersList();
		temp.load();
	}
}
