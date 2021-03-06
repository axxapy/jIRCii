package rero.gui.dck.items;

import rero.config.Config;
import rero.gui.dck.DItem;
import rero.gui.dck.SuperInput;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class CheckboxInput extends SuperInput implements ChangeListener {
	protected LinkedList enabledTrue = new LinkedList();
	protected LinkedList enabledFalse = new LinkedList();
	protected JCheckBox box;

	protected boolean defaultVal;

	public CheckboxInput(String var, boolean defaultVar, String _label, char mnemonic) {
		this(var, defaultVar, _label, mnemonic, FlowLayout.LEFT);
	}

	public CheckboxInput(String var, boolean defaultVar, String _label, char mnemonic, int alignment) {
		setLayout(new FlowLayout(alignment, 0, 0));

		box = new JCheckBox(_label);
		box.addChangeListener(this);

		add(box);
		box.setMnemonic(mnemonic);

		setPreferredSize(box.getPreferredSize());

		variable = var;
		defaultVal = defaultVar;
	}

	public void stateChanged(ChangeEvent ev) {
		handleDependents();
		notifyParent();
	}

	public void addDependent(DItem item) {
		enabledTrue.add(item);
	}

	public void addAntiDependent(DItem item) {
		enabledFalse.add(item);
	}

	public void save() {
		//System.out.println("Saving: " + getVariable());

		Config.getInstance().setOption(getVariable(), box.isSelected());
	}

	public int getEstimatedWidth() {
		return 0;
	}

	public void setAlignWidth(int width) {
	}

	public JComponent getComponent() {
		return this;
	}

	public void refresh() {
		//System.out.println("Refreshing: " + getVariable());

		box.setSelected(Config.getInstance().isOption(getVariable(), defaultVal));
		handleDependents();
	}

	public void handleDependents() {
		Iterator i = enabledTrue.iterator();
		while (i.hasNext()) {
			DItem temp = (DItem) i.next();
			temp.setEnabled(box.isSelected());
		}

		i = enabledFalse.iterator();
		while (i.hasNext()) {
			DItem temp = (DItem) i.next();
			temp.setEnabled(!box.isSelected());
		}
	}

	public JCheckBox getCheckBox() {
		return box;
	}
}


