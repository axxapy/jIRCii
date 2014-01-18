package rero.gui.components.toolbar;

import rero.config.*;
import rero.gui.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Toolbar extends JToolBar {
	public static ImageIcon[] images = new ImageIcon[37];
	protected LinkedList<ToolButton> buttons = new LinkedList<ToolButton>();

	private static Toolbar toolbar = null;
	private static StateListener state;       // we have to keep a reference or else it will get garbage collected

	public static void startup() {
		// set myself up... why make the other classes do all of the work, eh?

		state = new StateListener();
		state.propertyChanged("ui.usetoolbar", "");

		ClientState.getInstance().addClientStateListener("ui.usetoolbar", state);
	}

	/**
	 * this method is cheating and I should feel very guilty for doing my toolbar code like this, I'll probably pay for it later
	 */
	public static void stateChanged() {
		if (toolbar != null)
			toolbar.refresh();
	}

	public void refresh() {
		for (ToolButton b : buttons) {
			b.displayIcon();
		}
	}

	private static class StateListener implements ClientStateListener {
		public void propertyChanged(String property, String parameter) {
			if (toolbar != null)
				SessionManager.getGlobalCapabilities().getFrame().getContentPane().remove(toolbar);

			if (Config.getInstance().getBoolean("ui.usetoolbar", ClientDefaults.ui_usetoolbar)) {
				if (toolbar == null)
					toolbar = new Toolbar();

				SessionManager.getGlobalCapabilities().getFrame().getContentPane().add(toolbar, BorderLayout.NORTH);
			}

			if (toolbar != null)
				SessionManager.getGlobalCapabilities().getFrame().getContentPane().validate();
		}
	}

	public Toolbar() {
		int[] tiles;
		Action[] actions;

		if (Config.getInstance().getBoolean("ui.sdi", ClientDefaults.ui_sdi)) {
			tiles = new int[]{0, -1, 4, 7, -1, 10, -1, 22, 23, 24, -1, 26, 28, -1, 35, 36};
			actions = new Action[]{
					new ActionConnect(),
					null,
					new ActionOptions(),
					new ActionScript(), // popup menus aka script manager
					null,
					new ActionList(), // list action
					null,
					new ActionSend(), // dcc send
					new ActionChat(), // dcc chat
					new ActionDCC(), // dcc options
					null,
					new ActionNotify(), // notify list options
					new ActionNotify2(), // notify list options
					null,
					new ActionHelp(),
					new ActionAbout(),
					new ActionEvil()
			};
		} else {
			tiles = new int[]{0, -1, 4, 7, -1, 10, -1, 22, 23, 24, -1, 26, 28, -1, 31, 32, -1, 35, 36};
			actions = new Action[]{
					new ActionConnect(),
					null,
					new ActionOptions(),
					new ActionScript(), // popup menus aka script manager
					null,
					new ActionList(), // list action
					null,
					new ActionSend(), // dcc send
					new ActionChat(), // dcc chat
					new ActionDCC(), // dcc options
					null,
					new ActionNotify(), // notify list options
					new ActionNotify2(), // notify list options
					null,
					new ActionCascade(),
					new ActionTile(),
					null,
					new ActionHelp(),
					new ActionAbout(),
					new ActionEvil()
			};
		}

		BufferedImage all = LoadToolbarImage();

		for (int x = 0; x < 37; x++) {
			images[x] = new ImageIcon(all.getSubimage(x * 16, 0, 16, 16));
		}

		for (int x = 0; x < actions.length; x++) {
			if (actions[x] == null) {
				addSeparator(new Dimension(12, 12));
			} else {
				ToolButton temp = new ToolButton(actions[x]);
				add(temp);
				buttons.add(temp);
			}
		}

		setFloatable(false);
		setBorderPainted(true);
		setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	}

	private static class ToolButton extends JLabel {
		private ImageIcon normal;
		private ImageIcon pressed;
		private Action mAction;

		public ToolButton(Action mAction) {
			setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

			addMouseListener(new Listeners());

			this.mAction = mAction;
			displayIcon();
		}

		public void displayIcon() {
			normal = images[mAction.getIndex()];
			pressed = new ImageIcon(GrayFilter.createDisabledImage(normal.getImage()));
			setToolTipText(mAction.getDescription());

			setIcon(normal);
		}

		private class Listeners extends MouseAdapter {
			public void mouseClicked(MouseEvent ev) {
				mAction.actionPerformed(ev);
			}

			public void mousePressed(MouseEvent ev) {
				setIcon(pressed);
			}

			public void mouseReleased(MouseEvent ev) {
				setIcon(normal);
			}
		}
	}

	private static BufferedImage LoadToolbarImage() {
		ImageIcon original = Resources.getInstance().getIcon("jirc.toolbar", "toolbar.gif");

		Image image = original.getImage();
		BufferedImage value = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		Graphics g = value.getGraphics();
		g.drawImage(image, 0, 0, null);

		original.getImage().flush();

		return value;
	}
}
