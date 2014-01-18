package rero.config;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Set;

public class Config {
	protected Set changes; // keeps track of changes made to this version of the "state"
	protected Properties state;   // all of the properties we're going to load from the jerk.cfg file.

	private static Config instance = null;

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	private Config() {
		state = new Properties();
		try {
			FileInputStream istream = new FileInputStream(new File(ClientState.getBaseDirectory(), "jirc.prop"));
			state.load(istream);
			istream.close();
		} catch (Exception ex) {
		}
	}

	/**
	 * sync the file system config file with the current client state
	 */
	public void sync() {
		try {

			FileOutputStream ostream = new FileOutputStream(new File(ClientState.getBaseDirectory(), "jirc.prop"));
			state.save(ostream, "Java IRC Configuration");
			ostream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Config setString(String key, String value) {
		state.setProperty(key, value);
		ClientState.getInstance().fireChange(key);
		return this;
	}

	public String getString(String key, String defaultValue) {
		if (key == null) return defaultValue;
		String temp = state.getProperty(key);

		if (temp == null || temp.length() == 0) {
			return defaultValue;
		}

		return temp;
	}

	public float getFloat(String key, float defaultValue) {
		String temp = state.getProperty(key);

		if (temp == null || temp.length() == 0) {
			return defaultValue;
		}

		try {
			return Float.parseFloat(temp);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	public Config setFloat(String key, float value) {
		setString(key, value + "");
		return this;
	}

	public int getInteger(String key, int defaultValue) {
		String temp = state.getProperty(key);

		if (temp == null || temp.length() == 0) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(temp);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	public Config setInteger(String key, int value) {
		setString(key, value + "");
		return this;
	}

	public Config setBoolean(String key, boolean value) {
		if (value) {
			setString(key, "true");
		} else {
			setString(key, "false");
		}
		ClientState.getInstance().fireChange(key);
		return this;
	}

	public boolean getBoolean(String key, boolean defaultBoolean) {
		String temp = getString(key, null);

		if (temp == null) {
			return defaultBoolean;
		}

		if (temp.equals("true")) {
			return true;
		}

		return false;
	}

	public Color getColor(String key, Color defaultColor) {
		String temp = getString(key, null);

		if (temp == null) {
			return defaultColor;
		}

		return Color.decode(temp);
	}

	public Config setColor(String key, Color color) {
/*      long value = 0;
	  value = (color.getRed() << 16) | value;
      value = (color.getGreen() << 8) | value;
      value = (color.getBlue() << 0) | value; */

		setString(key, color.getRGB() + "");
		return this;
	}

	public Font getFont(String key, Font defaultValue) {
		String fname = getString(key, null);

		if (fname == null) {
			return defaultValue;
		}

		return Font.decode(fname);
	}

	public Config setFont(String key, Font value) {
		setString(key, rero.util.ClientUtils.encodeFont(value));
		return this;
	}

	public Config setBounds(String key, Rectangle value) {
		StringBuffer bounds = new StringBuffer();
		bounds.append((int) value.getX());
		bounds.append('x');
		bounds.append((int) value.getY());
		bounds.append('x');
		bounds.append((int) value.getWidth());
		bounds.append('x');
		bounds.append((int) value.getHeight());

		setString(key, bounds.toString());
		return this;
	}

	public Rectangle getBounds(String key, Dimension areaSize, Dimension mySize) {
		String temp = state.getProperty(key);

		if (temp == null) {
			int x = (int) (areaSize.getWidth() - mySize.getWidth()) / 2;
			int y = (int) (areaSize.getHeight() - mySize.getHeight()) / 2;

			if (x <= 0 || y <= 0) {
				x = 0;
				y = 0;
			}

			return new Rectangle(x, y, (int) mySize.getWidth(), (int) mySize.getHeight());
		}

		String[] values = temp.split("x");

		Rectangle tempr = new Rectangle(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
		return tempr;
	}

	public StringList getStringList(String key) {
		return new StringList(key);
	}

	/*public ServerParams[] getServers() {
		;
	}

	public void saveServers(ServerParams[] list) {
		for (ServerParams i : list) {
			String str = i.
		}
	}*/
}
