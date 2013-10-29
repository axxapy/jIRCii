package rero.dck.items;

import rero.dck.DItem;
import rero.dck.DParent;

import javax.swing.*;
import java.awt.*;

public class BlankInput extends JPanel implements DItem {
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

	public JComponent getComponent() {
		return this;
	}

	public Dimension getPreferredSize() {
		return new Dimension(0, 5);
	}
}

