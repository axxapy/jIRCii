package rero.gui.components;

import rero.dialogs.AboutWindow;
import rero.gui.windows.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JMenuBar implements ActionListener {
	private class mMenuItem extends JMenuItem {
		public mMenuItem(String title) {
			super(title);
			addActionListener(MainMenu.this);
		}
	}

	public MainMenu(MainWindow Window) {
		JMenu menu = new JMenu("Connection");
		{
			mMenuItem item = new mMenuItem("New Server");
			menu.add(item);

			item = new mMenuItem("Connect");
			menu.add(item);

			item = new mMenuItem("Disconnect");
			menu.add(item);

			menu.addSeparator();

			item = new mMenuItem("Close");
			menu.add(item);

			item = new mMenuItem("Exit");
			menu.add(item);
		}
		add(menu);

		menu = new JMenu("View");
		{
			mMenuItem item = new mMenuItem("DCC Sessions");
			menu.add(item);

			item = new mMenuItem("Options");
			menu.add(item);
		}
		add(menu);

		menu = new JMenu("Commands");
		{
			JMenu submenu = new JMenu("Away");
			{
				mMenuItem item = new mMenuItem("Set Away...");
				submenu.add(item);

				item = new mMenuItem("Set Back");
				submenu.add(item);
			}
			menu.add(submenu);

			submenu = new JMenu("Join");
			{
				/*mMenuItem item = new mMenuItem("Set Away...");
				submenu.add(item);

				item = new mMenuItem("Set Back");
				submenu.add(item);*/
			}
			menu.add(submenu);

			mMenuItem item = new mMenuItem("List");
			menu.add(item);
		}
		add(menu);

		menu = new JMenu("Window");
		{
			mMenuItem item = new mMenuItem("Tile");
			menu.add(item);

			item = new mMenuItem("Cascade");
			menu.add(item);

			menu.addSeparator();

			item = new mMenuItem("Status");
			menu.add(item);
		}
		add(menu);

		menu = new JMenu("Help");
		{
			mMenuItem item = new mMenuItem("Help");
			menu.add(item);

			menu.addSeparator();

			item = new mMenuItem("Homepage");
			menu.add(item);

			item = new mMenuItem("Report Bugs");
			menu.add(item);

			menu.addSeparator();

			item = new mMenuItem("About");
			menu.add(item);
		}
		add(menu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("About")) {
			AboutWindow.showDialog(null);
		}
		System.out.println("actionPerformed: " + e.toString());
	}
}
