package rero.dialogs.toolkit;

import javax.swing.*;

public class PlainLabel extends JTextField {
	public PlainLabel(String text) {
		setBorder(null);
		setEditable(false);
		setOpaque(false);
		setText(text);
	}
}
