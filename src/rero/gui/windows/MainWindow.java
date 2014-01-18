package rero.gui.windows;

import rero.ApplicationContext;
import rero.config.ClientDefaults;
import rero.config.ClientState;
import rero.config.Config;
import rero.config.Resources;
import rero.gui.GlobalCapabilities;
import rero.gui.KeyBindings;
import rero.gui.SessionManager;
import rero.gui.components.MainMenu;
import rero.gui.components.ServersTree;
import rero.gui.components.TabbedPanel;
import rero.gui.components.toolbar.Toolbar;
import rero.gui.mdi.ClientDesktop;
import rero.gui.sdi.ClientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
	private ApplicationContext mContext;
	private  WindowManager mWindowManager;
	private KeyBindings mKeyBindings;

	public ApplicationContext getContext() {
		return mContext;
	}

	public WindowManager getWindowManager() {
		if (mWindowManager == null) {
			if (Config.getInstance().getBoolean("ui.sdi", ClientDefaults.ui_sdi)) {
				mWindowManager = new ClientPanel();
			} else {
				mWindowManager = new ClientDesktop();
				((ClientDesktop)mWindowManager).addMouseListener(new PopupManager());
			}
		}
		return mWindowManager;
	}

	public MainWindow(ApplicationContext Context) {
		super("jIRCii");
		mContext = Context;

		//getWindowManager();

		GlobalCapabilities.frame = this;

		mKeyBindings = new KeyBindings(this);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(mKeyBindings);

		if (Config.getInstance().getBoolean("ui.showbar", ClientDefaults.ui_showbar)) {
			setJMenuBar(new JMenuBar());
		}

		JSplitPane root = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		root.add(new ServersTree(this));
		root.add(new TabbedPanel(this));
		//root.add(new SessionManager(this));
		getContentPane().add(root);
		getContentPane().add(new Toolbar(), BorderLayout.NORTH);


		setJMenuBar(new MainMenu(this));

		//getContentPane().add(new SessionManager(this), BorderLayout.CENTER);
		//getContentPane().add(new ServersTree(), BorderLayout.WEST);

		setIconImage(Resources.getInstance().getIcon("jirc.icon", "jicon.jpg").getImage());

		int inset = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;

		setBounds(Config.getInstance().getBounds("desktop.bounds", Toolkit.getDefaultToolkit().getScreenSize(), new Dimension(640, 480)));

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				try {//exit4shure
					SessionManager.getGlobalCapabilities().QuitClient();
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
			}
		});

		addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent ev) {
				if (Config.getInstance().getBoolean("desktop.relative", false) ||
						Config.getInstance().getBoolean("window.relative", false) ||
						Config.getInstance().getBoolean("statusbar.relative", false)
						) {
					validate();
					ClientState.getInstance().fireChange("desktop");
					ClientState.getInstance().fireChange("window");
					ClientState.getInstance().fireChange("statusbar");
					repaint();
				}
			}
		});
	}

	protected class PopupManager extends MouseAdapter {
		public void maybeShowPopup(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				System.out.println("PopupManager.maybeShowPopup called");
				/*MenuBridge bridge = (MenuBridge) client.getCapabilities().getDataStructure("menuBridge");
				JPopupMenu menu = bridge.getPopupMenu("background", null);

				if (menu == null) {
					return;
				}

				menu.show((JComponent) ev.getComponent(), ev.getX(), ev.getY());
				ev.consume();*/
			}
		}

		public void mouseClicked(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}
	}
}
