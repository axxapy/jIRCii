package rero.gui.windows.components;

import rero.config.Config;
import rero.config.Resources;
import rero.gui.SessionManager;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class ServersTree extends JTree {
	protected static TreeModel getDefaultTreeModel() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		LinkedList<String> list = Config.getInstance().getStringList("saved.servers").getList();
		for (String server : list) {
			String name = server.substring(0, server.indexOf(" /"));
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
			node.setUserObject(server);
			node.setAllowsChildren(true);
			root.add(node);
		}
		return new DefaultTreeModel(root);
	}

	public ServersTree() {
		super(getDefaultTreeModel());
		DefaultTreeCellRenderer Renderer = new DefaultTreeCellRenderer();
		ImageIcon Icon = Resources.getInstance().getIcon(null, "ic_tree_server.png");
		Renderer.setLeafIcon(Icon);
		Renderer.setOpenIcon(Icon);
		Renderer.setClosedIcon(Icon);

		setCellRenderer(Renderer);
		setRootVisible(false);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = getRowForLocation(e.getX(), e.getY());
				TreePath selPath = getPathForLocation(e.getX(), e.getY());
				if(selRow != -1) {
					if(e.getClickCount() == 1) {
						//mySingleClick(selRow, selPath);
					}
					else if(e.getClickCount() == 2) {
						onDblClick(selRow, selPath);
					}
				}
			}
		});
	}

	public void onDblClick(int row, TreePath path) {
		DefaultMutableTreeNode node = ((DefaultMutableTreeNode)path.getLastPathComponent());
		String server = (String)node.getUserObject();
		String cmd = server.split(" ", 2)[1];
		SessionManager.getGlobalCapabilities().getSessionManager().getActiveSession().executeCommand(cmd);
		/*
		UserHandler commands = (UserHandler) getCapabilities().getDataStructure("commands");

		if (getCapabilities().isConnected())  // add some sort of check for isRegistered() as well.
		{
			ircData.reset();  // reset the data structures so we don't do an auto reconnect
			((NotifyData) getCapabilities().getDataStructure(DataStructures.NotifyData)).reset();
			getCapabilities().sendln("QUIT :switching servers");
		}

		getCapabilities().getSocketConnection().connect(host, port, 0, password, secure);
		getCapabilities().getOutputCapabilities().fireSetStatus(ClientUtils.getEventHashMap(host, host + " " + port + " " + password + " " + secure), "IRC_ATTEMPT_CONNECT");
		 */
	}
}