package rero.gui.components;

import rero.client.ClientEventsListener;
import rero.client.Connection;
import rero.gui.background.BackgroundPanel;
import rero.gui.input.InputField;
import rero.gui.windows.WindowStatusBar;
import rero.ircfw.Channel;
import rero.gui.text.WrappedDisplay;

import java.awt.*;

public class MainPanel extends BackgroundPanel implements ClientEventsListener {
	private TabbedPanel mPanel;
	private WrappedDisplay mDisplay;
	private InputField mInput;
	private WindowStatusBar statusbar;
	private Connection mConnection;
	private Channel mChannel;

	public MainPanel(TabbedPanel p, Connection conn, Channel c) {
		super();
		mPanel = p;
		mConnection = conn;
		mChannel = c;
		mDisplay = new WrappedDisplay();
		mInput = new InputField();

		//mDisplay.setBounds(getBounds());
		setLayout(new BorderLayout());

		add(mDisplay);//, BorderLayout.CENTER);
		add(mInput, BorderLayout.SOUTH);

		conn.addClientEventsListener(this);
	}

	public void setActive() {
		mPanel.setSelectedComponent(this);
	}

	@Override
	public void onConnected(Connection connection) {
		mDisplay.addText("Connected");
	}

	@Override
	public void onDisconnect(Connection connection) {
		mDisplay.addText("Disconnected");
	}

	@Override
	public void onMsgReceived(Connection connection, String msg) {
		mDisplay.addText(msg);
	}
}
