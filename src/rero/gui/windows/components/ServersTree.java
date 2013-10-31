package rero.gui.windows.components;

import rero.ApplicationContext;
import rero.config.Resources;
import rero.config.ServersList;
import rero.config.models.ServerConfig;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ServersTree extends JTree {
	ApplicationContext mContext;

	protected static TreeModel getDefaultTreeModel() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		ArrayList<ServerConfig> servers = ServersList.getInstance().getServers();
		for (ServerConfig server : servers) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(server.getDescription());
			node.setAllowsChildren(true);
			root.add(node);
		}
		return new DefaultTreeModel(root);
	}

	public ServersTree(ApplicationContext Context) {
		super(getDefaultTreeModel());
		mContext = Context;

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

		ServerConfig server = ServersList.getInstance().getServers().get(row);

		mContext.getConnectionsManager().getConnection(server).Connect();

		/*String server = (String)node.getUserObject();
		String cmd = server.split(" ", 2)[1];
		SessionManager.getGlobalCapabilities().getSessionManager().getActiveSession().executeCommand(cmd);*/


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