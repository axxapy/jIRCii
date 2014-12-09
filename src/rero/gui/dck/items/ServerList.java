package rero.gui.dck.items;

import rero.config.Config;
import rero.config.ServersList;
import rero.config.StringList;
import rero.config.models.ServerConfig;
import rero.gui.dialogs.toolkit.ADialog;
import rero.gui.dialogs.toolkit.APanel;
import rero.gui.dialogs.toolkit.LabelGroup;
import rero.gui.SessionManager;
import rero.gui.dck.DCapabilities;
import rero.gui.dck.DItem;
import rero.gui.dck.DParent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServerList extends JPanel implements DItem {
	protected int height;
	protected int width;

	protected JButton connect, edit, add, remove;

	protected JList list;

	protected ServersList data;
	protected StringList autoConnect;
	protected JComponent component;

	protected DCapabilities capabilities;

	public ServerList(ServersList _data, int _width, int _height, DCapabilities _capabilities) {
		data = _data;

		autoConnect = Config.getInstance().getStringList("auto.connect");

		capabilities = _capabilities;

		width = _width;
		height = _height;

		component = this;

		setBorder(BorderFactory.createEmptyBorder(0, width, 0, width));

		setLayout(new BorderLayout());

		connect = new JButton("Connect");
		connect.setMnemonic('C');

		edit = new JButton("Edit");
		edit.setMnemonic('E');

		add = new JButton("Add");
		add.setMnemonic('A');

		remove = new JButton("Remove");
		remove.setMnemonic('R');

		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));

		buttons.add(connect);
		buttons.add(add);
		buttons.add(edit);
		buttons.add(remove);

		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				handleConnectAction();
			}
		});

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				EditServerInfo editor = new EditServerInfo();
				editor.setupDialog(null);

				ADialog dialog = new ADialog(component, "New ServerConfig", editor, null);
				dialog.setSize(new Dimension(330, 280));

				ServerConfig temp = (ServerConfig) dialog.showDialog(component);
				if (temp != null) {
					data.addServer(temp);
					data.update();
					((ServerListModel) list.getModel()).fireChange();
				}
			}
		});

		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				EditServerInfo editor = new EditServerInfo();
				editor.setupDialog(list.getSelectedValue());

				ADialog dialog = new ADialog(component, "Edit ServerConfig", editor, list.getSelectedValue());
				dialog.setSize(new Dimension(330, 280));
				if (dialog.showDialog(component) != null) // the returned value is modified directly.
				{
					((ServerListModel) list.getModel()).fireChange();
				}
			}
		});

		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (list.getSelectedValue() != null) {
					data.removeServer((ServerConfig) list.getSelectedValue());
					data.update();
					((ServerListModel) list.getModel()).fireChange();
				}
			}
		});

		add(buttons, BorderLayout.SOUTH);

		ServerListModel lmodel = new ServerListModel();

		list = new JList(lmodel);
		list.setCellRenderer(lmodel);

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				if (ev.getClickCount() >= 2) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							handleConnectAction();
						}
					});
					ev.consume();
				}
			}
		});

		add(new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		setPreferredSize(new Dimension(0, height));
	}

	public void handleConnectAction() {
		capabilities.forceSave();

		if (SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities().isConnected()) {
			SessionManager.getGlobalCapabilities().createNewServer();
		}

		if (list.getSelectedValue() != null) {
			ServerConfig connectToMe = (ServerConfig) list.getSelectedValue();
			SessionManager.getGlobalCapabilities().getActiveSession().executeCommand(connectToMe.getCommand());
			capabilities.closeDialog();
		} else {
			JOptionPane.showMessageDialog(null, "Please select a server", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void setParent(DParent parent) {

	}

	public void setEnabled(boolean b) {
	}

	public void save() {
		data.save();
	}

	public void refresh() {
		try {
			data.update();
			((ServerListModel) list.getModel()).fireChange();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int getEstimatedWidth() {
		return 0;
	}

	public void setAlignWidth(int width) {
	}

	public void setParentVariable(String parent) {

	}

	public JComponent getComponent() {
		return this;
	}

	protected class ServerListModel extends AbstractListModel implements ListCellRenderer {
		protected JLabel cell = new JLabel();

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			ServerConfig svalue = (ServerConfig) value;

			if (index < 0 || index > getSize()) {
				return cell;
			}

			cell.setToolTipText(svalue.getHost() + ":" + svalue.getPorts());

			Color fore = UIManager.getColor("TextField.foreground");

			if (autoConnect.isValue(svalue.getHost())) {
				fore = Color.blue;
			}

			if (isSelected) {
				cell.setOpaque(true);
				cell.setBackground(UIManager.getColor("TextField.selectionBackground"));

				if (fore == Color.blue) {
					cell.setForeground(fore);
				} else {
					cell.setForeground(UIManager.getColor("TextField.selectionForeground"));
				}

				cell.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));

				cell.setText(svalue.getHost() + ":" + svalue.getPorts());
			} else {
				cell.setOpaque(false);
				cell.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
				cell.setForeground(fore);

				String description = svalue.getDescription();

				if (description == null || description.equals("")) {
					description = svalue.getHost();
				}

				cell.setText(description);
			}

			return cell;
		}


		public void fireChange() {
			fireContentsChanged(this, 0, -1);
		}

		public Object getElementAt(int index) {
			return data.getServers().get(index);
		}

		public int getSize() {
			return data.getServers().size();
		}
	}

	protected class EditServerInfo extends APanel {
		protected JTextField description = new JTextField();
		protected JTextField host = new JTextField();
		protected JTextField portRange = new JTextField();
		protected JTextField network = new JTextField();
		protected JCheckBox isSSL = new JCheckBox("ServerConfig requires SSL");
		protected JCheckBox isStartup = new JCheckBox("Connect to server on client startup");
		protected JPasswordField password = new JPasswordField();

		public void setupDialog(Object value) {
			if (value == null) {
				setBorder(BorderFactory.createTitledBorder(" Create New ServerConfig "));
			} else {
				setBorder(BorderFactory.createTitledBorder(" Edit ServerConfig Information "));
			}

			JLabel description_l, host_l, portRange_l, network_l, password_l;

			LabelGroup labels = new LabelGroup();

			description_l = new JLabel(" Description: ");
			host_l = new JLabel(" Hostname: ");
			portRange_l = new JLabel(" Port(s): ");
			network_l = new JLabel(" Network: ");
			password_l = new JLabel(" Password: ");

			labels.addLabel(description_l);
			labels.addLabel(host_l);
			labels.addLabel(portRange_l);
			labels.addLabel(network_l);
			labels.addLabel(password_l);

			addComponent(mergeComponents(description_l, description, 20));
			addComponent(mergeComponents(host_l, host, 20));
			addComponent(mergeComponents(portRange_l, portRange, 100));
			addComponent(isSSL);
			addComponent(mergeComponents(password_l, password, 20));
			addComponent(isStartup);

			labels.sync();

			if (value != null) {
				ServerConfig temp = (ServerConfig) value;
				description.setText(temp.getDescription());
				host.setText(temp.getHost());
				portRange.setText(temp.getPorts());
				//network.setText(temp.getNetwork());
				isSSL.setSelected(temp.isSecure());

				isStartup.setSelected(autoConnect.isValue(temp.getHost()));

				password.setText(temp.getPassword());
			}
		}

		public Object getValue(Object value) {
			ServerConfig server;

			if (value != null) {
				server = (ServerConfig) value;
			} else {
				server = new ServerConfig();
			}

			if (isStartup.isSelected() && !autoConnect.isValue(host.getText())) {
				//System.out.println("Adding: " + host.getText());

				autoConnect.add(host.getText());
				autoConnect.save();
			} else if (!isStartup.isSelected() && autoConnect.isValue(host.getText())) {
				//System.out.println("Removing: " + host.getText());

				autoConnect.remove(host.getText());
				autoConnect.save();
			}

			server.setValues(description.getText(), host.getText(), portRange.getText(), isSSL.isSelected(), password.getText());
			return server;
		}
	}
}

