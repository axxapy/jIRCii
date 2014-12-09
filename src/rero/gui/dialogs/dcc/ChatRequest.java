package rero.gui.dialogs.dcc;

import rero.dcc.Chat;
import rero.dcc.ConnectDCC;
import rero.gui.dialogs.toolkit.ADialog;
import rero.gui.dialogs.toolkit.APanel;
import rero.gui.dialogs.toolkit.LabelGroup;
import rero.gui.dialogs.toolkit.PlainLabel;

import javax.swing.*;
import java.awt.*;

public class ChatRequest extends APanel {
	public static boolean showDialog(Component component, ConnectDCC connection) {
		ChatRequest request = new ChatRequest();
		request.setupDialog(connection);

		ADialog dialog = new ADialog(component, "DCC Chat Request", request, null);
		dialog.pack();
		return (dialog.showDialog(component) != null);
	}

	public void setupDialog(Object value) {
		JPanel space = new JPanel();
		space.setPreferredSize(new Dimension(0, 15));

		JPanel space2 = new JPanel();
		space2.setPreferredSize(new Dimension(0, 15));

		LabelGroup labels = new LabelGroup();
		JLabel user, host, blank;

		user = new JLabel("User: ");
		host = new JLabel("Host: ");
		blank = new JLabel("");

		labels.addLabel(user);
		labels.addLabel(blank);
		labels.sync(); // lines the labels up

		ConnectDCC info1 = (ConnectDCC) value;
		Chat info2 = (Chat) info1.getImplementation();

		PlainLabel iuser, ihost;

		iuser = new PlainLabel(info2.getNickname());
		ihost = new PlainLabel(info1.getHost() + ":" + info1.getPort());

		addComponent(new PlainLabel("A user is requesting a direct chat"));

		addComponent(space2);
		addComponent(mergeComponents(user, iuser));
		addComponent(mergeComponents(host, ihost));
		addComponent(space);
	}

	public Object getValue(Object defvalue) {
		return "";
	}
}
