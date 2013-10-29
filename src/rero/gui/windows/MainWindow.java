package rero.gui.windows;

import rero.config.ClientState;
import rero.gui.GlobalCapabilities;
import rero.gui.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
	public MainWindow() {
		super("jIRCii");

		GlobalCapabilities.frame = this;

		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(new SessionManager(this), BorderLayout.CENTER);

		setIconImage(ClientState.getClientState().getIcon("jirc.icon", "jicon.jpg").getImage());

		int inset = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;

		setBounds(ClientState.getClientState().getBounds("desktop.bounds", Toolkit.getDefaultToolkit().getScreenSize(), new Dimension(640, 480)));

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				SessionManager.getGlobalCapabilities().QuitClient();
			}
		});

		addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent ev) {
				if (ClientState.getClientState().isOption("desktop.relative", false) ||
						ClientState.getClientState().isOption("window.relative", false) ||
						ClientState.getClientState().isOption("statusbar.relative", false)
						) {
					validate();
					ClientState.getClientState().fireChange("desktop");
					ClientState.getClientState().fireChange("window");
					ClientState.getClientState().fireChange("statusbar");
					repaint();
				}
			}
		});
	}
}
