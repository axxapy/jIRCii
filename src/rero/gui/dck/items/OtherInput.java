package rero.gui.dck.items;

import rero.gui.dck.DItem;
import rero.gui.dck.DParent;

import javax.swing.*;

public class OtherInput implements DItem {
	protected JComponent other;

	public OtherInput(JComponent _other) {
		other = _other;
	}

	public void setEnabled(boolean b) {
		other.setEnabled(b);
	}

	public void save() {
	}

	public int getEstimatedWidth() {
		return 0;
	}

	public void setAlignWidth(int width) {
	}

	public void setParent(DParent parent) {

	}

	public JComponent getComponent() {
		return other;
	}

	public void refresh() {
	}
}


