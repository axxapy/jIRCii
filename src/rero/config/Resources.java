package rero.config;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Resources {
	private static Resources instance = null;

	public static Resources getInstance() {
		if (instance == null) {
			instance = new Resources();
		}
		return instance;
	}

	private Resources() {}

	public static File getFile(String filename) {
		return new File(ClientState.getInstance().getBaseDirectory(), filename);
	}

	public URL getResource(String fileName) {
		return getPackagedResource(fileName, "resource");
//      return this.getClass().getResource("/resource/"+fileName);
	}

	public URL getPackagedResource(String fileName, String subDir) {
		try {
			File check = new File(ClientState.getInstance().getBaseDirectory(), fileName);
			if (check.exists()) {
				return check.toURL();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return this.getClass().getResource("/" + subDir + "/" + fileName);
	}

	public InputStream getResourceAsStream(String fileName) {
		try {
			File realf = new File(fileName);
			if (realf.exists()) {
				return realf.toURL().openStream();
			}

			File check = new File(ClientState.getInstance().getBaseDirectory(), fileName);
			if (check.exists()) {
				return check.toURL().openStream();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		URL temp = getResource(fileName);
		if (temp == null) {
			return null;
		}

		try {
			return temp.openStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public ImageIcon getIcon(String key, String defaultResource) {
		String temp = Config.getInstance().getString(key, null);

		if (temp == null) {
			System.out.println(defaultResource);
			return new ImageIcon(getResource(defaultResource));
		} else {
			return new ImageIcon(Toolkit.getDefaultToolkit().getImage(temp));
		}
	}

	public String getHelpString(String topic) {
		topic = topic.replaceAll("\\'", "").replaceAll("\\?", "").replaceAll(" ", "_");

		try {
			URL url = getPackagedResource(topic, "help");

			if (url == null) {
				return null;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			StringBuffer temp = new StringBuffer();

			String text;
			while ((text = in.readLine()) != null) {
				temp.append(text);
				temp.append("\n");
			}

			return temp.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}
