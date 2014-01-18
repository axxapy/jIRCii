package rero.gui.components;

import rero.config.ClientDefaults;
import rero.config.Config;
import rero.gui.input.InputListener;
import rero.gui.input.UserInputEvent;
import rero.gui.toolkit.MinimalTabUI;
import rero.gui.windows.*;
import rero.ircfw.Channel;
import text.event.ClickEvent;
import text.event.ClickListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.HashMap;

public class TabbedPanel extends JTabbedPane implements InputListener, ClickListener, ClientWindowListener, ChangeListener {
	HashMap<Channel, StatusWindow> windows = new HashMap<Channel, StatusWindow>();

	public TabbedPanel(MainWindow Window) {
		addChangeListener(this);
		createStatusWindow();

		setTabPlacement(JTabbedPane.BOTTOM);

		if (!Config.getInstance().getBoolean("ui.showtabs", ClientDefaults.ui_showtabs)) {
			setUI(new MinimalTabUI());
		}
	}

	public StatusWindow createStatusWindow() {
		StatusWindow window = new StatusWindow();
		windows.put(new Channel(""), window);

		//desktop.addWindow(window, true);

		window.init(new ClientWindow() {
			@Override
			public void setTitle(String title) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void setContentPane(Container c) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void setIcon(ImageIcon i) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void show() {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void addWindowListener(ClientWindowListener l) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public String getTitle() {
				return null;  //To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public boolean isSelected() {
				return false;  //To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void closeWindow() {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void setMaximum(boolean b) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void setIcon(boolean b) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public boolean isMaximum() {
				return false;  //To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public boolean isIcon() {
				return false;  //To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void activate() {
				//To change body of implemented methods use File | Settings | File Templates.
			}
		});
		window.getInput().addInputListener(this); // we'll just do the input assignment ourselves thank you very much.
		//window.installCapabilities(client.getCapabilities());

		return window;
	}

	public ChannelWindow createChannelWindow(rero.ircfw.Channel channel) {
		ChannelWindow window = new ChannelWindow(channel);
		windows.put(channel, window);

		//desktop.addWindow(window, true);

		window.getInput().addInputListener(this); // we'll just do the input assignment ourselves thank you very much.
		//window.installCapabilities(client.getCapabilities());
		//windows.put(channel.getName().toUpperCase(), window);

		//scriptBridge.windowCreated(window);
		window.getDisplay().addClickListener(this);

		window.getWindow().addWindowListener(this);

		return window;
	}

	@Override
	public void onInput(UserInputEvent ev) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void wordClicked(ClickEvent ev) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onOpen(ClientWindowEvent ev) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onClose(ClientWindowEvent ev) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onActive(ClientWindowEvent ev) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onInactive(ClientWindowEvent ev) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onMinimize(ClientWindowEvent ev) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
