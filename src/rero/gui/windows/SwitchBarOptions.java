package rero.gui.windows;

import rero.config.ClientDefaults;
import rero.config.ClientState;
import rero.config.ClientStateListener;

import javax.swing.*;
import java.awt.*;

public class SwitchBarOptions implements ClientStateListener {
	protected JComponent container;
	protected JComponent switchbar;
	protected JComponent panel;

	protected static ColorListener color = null;

	public SwitchBarOptions(JComponent c, JComponent s) {
		container = c;
		switchbar = s;

		ClientState.getInstance().addClientStateListener("switchbar.position", this);
		ClientState.getInstance().addClientStateListener("switchbar.enabled", this);

		rehash();

		if (color == null) {
			color = new ColorListener();
		}
	}

	public static Color getHighlightColor() {
		return color.getColor();
	}

	public static boolean isHilightOn() {
		return color.isHilightOn();
	}

	public void rehash() {
		container.remove(switchbar);
		boolean enabled = ClientState.getInstance().isOption("switchbar.enabled", true);
		int position = ClientState.getInstance().getInteger("switchbar.position", 0);

		if (enabled) {
			switch (position) {
				case 0:
					container.add(switchbar, BorderLayout.NORTH);
					break;
				case 1:
					container.add(switchbar, BorderLayout.SOUTH);
					break;
				case 2:
					container.add(switchbar, BorderLayout.WEST);
					break;
				case 3:
					container.add(switchbar, BorderLayout.EAST);
					break;
			}
		}
	}

	public void propertyChanged(String key, String value) {
		rehash();
		container.revalidate();
	}

	private static class ColorListener implements ClientStateListener {
		protected Color theColor;
		protected boolean hilight;

		public ColorListener() {
			ClientState.getInstance().addClientStateListener("switchbar.color", this);
			ClientState.getInstance().addClientStateListener("switchbar.hilight", this);

			propertyChanged(null, null);
		}

		public void propertyChanged(String key, String value) {
			theColor = ClientState.getInstance().getColor("switchbar.color", ClientDefaults.switchbar_color);
			hilight = ClientState.getInstance().isOption("switchbar.hilight", ClientDefaults.switchbar_hilight);
		}

		public boolean isHilightOn() {
			return hilight;
		}

		public Color getColor() {
			return theColor;
		}
	}
}

