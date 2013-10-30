package rero.dialogs.help;

import rero.config.Resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

public class HelpData {
	protected LinkedList aliases = new LinkedList();
	protected HashMap data = new HashMap();

	public Object[] getData() {
		return aliases.toArray();
	}

	public LinkedList getAliases() {
		return aliases;
	}

	public String getCommand(String command) {
		return ((HelpCommand) data.get(command)).toString();
	}

	public boolean isCommand(String command) {
		return data.containsKey(command);
	}

	public HelpData() {
		URL url = Resources.getInstance().getPackagedResource("aliases", "help");

		if (url != null) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

				String text;
				while ((text = in.readLine()) != null) {
					String[] temp = text.split("\\:\\:");
					aliases.add(temp[0]);
					data.put(temp[0], new HelpCommand(temp));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}
}
