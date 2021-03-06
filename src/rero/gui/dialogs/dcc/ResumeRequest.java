package rero.gui.dialogs.dcc;

import rero.dcc.ConnectDCC;
import rero.dcc.Receive;
import rero.gui.dialogs.toolkit.ADialog;
import rero.gui.dialogs.toolkit.APanel;
import rero.gui.dialogs.toolkit.LabelGroup;
import rero.gui.dialogs.toolkit.PlainLabel;
import rero.util.ClientUtils;

import javax.swing.*;
import java.awt.*;

public class ResumeRequest extends APanel {
	protected Receive receive;   // the *receiving* end - *uNF*
	protected JComboBox options;

	public static int showDialog(Component component, ConnectDCC connect) {
		ResumeRequest request = new ResumeRequest();
		request.setupDialog(connect);

		ADialog dialog = new ADialog(component, "File Exists", request, null);
		dialog.pack();

		Integer temp = (Integer) dialog.showDialog(component);
		if (temp == null) {
			return -1;
		}

		return temp.intValue();
	}

	public void setupDialog(Object value) {
		JPanel space = new JPanel();
		space.setPreferredSize(new Dimension(0, 15));

		JPanel space2 = new JPanel();
		space2.setPreferredSize(new Dimension(0, 15));

		LabelGroup labels = new LabelGroup();
		JLabel file, size, action;

		file = new JLabel("File: ");
		size = new JLabel("Size: ");
		action = new JLabel("Action: ");

		labels.addLabel(file);
		labels.addLabel(size);
		labels.addLabel(action);
		labels.sync(); // lines the labels up

		ConnectDCC info1 = (ConnectDCC) value;
		Receive info2 = (Receive) info1.getImplementation();

		PlainLabel ifile, isize;

		ifile = new PlainLabel(info2.getFile().getName());
		isize = new PlainLabel(ClientUtils.formatBytes((int) info2.getFile().length()) + " of " + ClientUtils.formatBytes(info2.getExpectedSize()));

		addComponent(new PlainLabel("File already exists..."));

		addComponent(space2);

		addComponent(mergeComponents(file, ifile));
		addComponent(mergeComponents(size, isize));

		addComponent(space);

		options = new JComboBox(new String[]{"Resume", "Rename", "Overwrite"});

		addComponent(mergeComponents(action, options));
	}

	public Object getValue(Object defvalue) {
		return new Integer(options.getSelectedIndex());
	}
}
