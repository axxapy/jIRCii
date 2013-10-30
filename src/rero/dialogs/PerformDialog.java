package rero.dialogs;

import rero.config.ClientState;
import rero.gui.dck.DItem;
import rero.gui.dck.DMain;
import rero.gui.dck.DParent;
import rero.gui.dck.items.CheckboxInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PerformDialog extends DMain implements DParent, ActionListener {
	public String getTitle() {
		return "Perform";
	}

	public String getDescription() {
		return "Perform on Connect";
	}

	public void actionPerformed(ActionEvent ev) {
		itemc.save();
	}

	public void notifyParent(String variable) {
		ClientState.getInstance().fireChange("perform");
		itemc.refresh();
	}

	public String getVariable(String variable) {
		return "perform.all";
	}

	protected DItem itemc;
	protected CheckboxInput itema;

	public JComponent getDialog() {
		JPanel dialog = new JPanel();

		setupLayout(dialog);
		setupDialog();

		dialog.add(itema.getComponent(), BorderLayout.SOUTH);

		dialog.add(itemc.getComponent(), BorderLayout.CENTER);

		return dialog;
	}

	public JComponent setupLayout(JComponent component) {
		component.setLayout(new BorderLayout(3, 3));
		component.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		return component;
	}

	public void setupDialog() {
		itema = addCheckboxInput("perform.enabled", false, "Perform these commands when connecting", 'P', FlowLayout.LEFT);

		itemc = addTextInput(".perform", 5); // doesn't really matter
		itemc.setParent(this);

		itema.addDependent(itemc);
	}

	public void refresh() {
		itemc.refresh();
		super.refresh();
	}
}



