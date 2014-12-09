package rero.gui.dialogs.help;

import rero.config.Resources;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;

public class HelpNormal extends HelperObject implements TreeSelectionListener {
	private static HashMap helpData = new HashMap();

	public void valueChanged(TreeSelectionEvent e) {
		JTree theTree = (JTree) e.getSource();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) theTree.getLastSelectedPathComponent();

		if (node == null)
			return;

		showHelpOn(node.getUserObject().toString());
	}

	protected String lastKey = "";

	public void showHelpOn(String key) {
		if (isTutorial(key)) {
			if (lastKey.equals("IRC Tutorial")) {
				help.scrollTo(helpData.get(key).toString());
			} else {
				help.updateText(getHelpFor("IRC Tutorial"));
			}
		} else if (isScriptTutorial(key)) {
			if (lastKey.equals("Script Tutorial")) {
				help.scrollTo(helpData.get(key).toString());
			} else {
				help.updateText(getHelpFor("Script Tutorial"));
			}
		} else {
			updateText(getHelpFor(key));
		}
	}

	private String getHelpFor(String key) {
		lastKey = key;

		if (helpData.get(key) == null) {
			String text = Resources.getInstance().getHelpString(key);

			if (text != null) {
				helpData.put(key, text);
				return text;
			}

			return null;
		} else {

			String data = helpData.get(key).toString();
			return data;
		}
	}

	private static boolean isTutorial(String key) {
		return key.equals("Introduction");
	}

	private static boolean isScriptTutorial(String key) {
		return key.equals("Aliases") || key.equals("Events") || key.equals("Resources") || key.equals("Introduction ");
	}

	private DefaultMutableTreeNode initHelp() {
		DefaultMutableTreeNode category, option, items;
		items = new DefaultMutableTreeNode("Help");

		category = new DefaultMutableTreeNode("About");
		items.add(category);
		option = new DefaultMutableTreeNode("Contributors");
		category.add(option);

		category = new DefaultMutableTreeNode("General");
		items.add(category);
		option = new DefaultMutableTreeNode("Colored Text");
		category.add(option);
		option = new DefaultMutableTreeNode("KB Shortcuts");
		category.add(option);

		category = new DefaultMutableTreeNode("Script Tutorial");
		items.add(category);
		option = new DefaultMutableTreeNode("Introduction ");
		category.add(option);
		option = new DefaultMutableTreeNode("Aliases");
		category.add(option);
		option = new DefaultMutableTreeNode("Events");
		category.add(option);
		option = new DefaultMutableTreeNode("Resources");
		category.add(option);

		helpData.put("Introduction ", "part1");
		helpData.put("Aliases", "part2");
		helpData.put("Events", "part3");
		helpData.put("Resources", "part4");

		return items;
	}

	public JComponent getNavigationComponent() {
		JTree genOptions = new JTree(initHelp());
		genOptions.setRootVisible(false);
		genOptions.setToggleClickCount(0); // 1 click to expand the tree...
		genOptions.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		for (int x = 0; x < genOptions.getRowCount(); x++)
			genOptions.expandPath(genOptions.getPathForRow(x));

		genOptions.addTreeSelectionListener(this);

		return genOptions;
	}
}



