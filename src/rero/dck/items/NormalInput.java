package rero.dck.items;

import rero.dck.DItem;
import rero.dck.DParent;

import javax.swing.*;
import java.awt.*;

public class NormalInput extends JPanel implements DItem {
	protected JLabel label;

	public NormalInput(String text, int align) {
		setLayout(new FlowLayout(align));

		label = new JLabel(text);

		add(label);
	}

	public void setEnabled(boolean b) {
	}

	public void save() {

	}

	public void refresh() {
	}

	public int getEstimatedWidth() {
		return 0;
	}

	public void setAlignWidth(int width) {
	}

	public void setParent(DParent parent) {

	}

	public void setText(String text) {
		label.setText(text);
	}

	public JComponent getComponent() {
		return this;
	}
}

