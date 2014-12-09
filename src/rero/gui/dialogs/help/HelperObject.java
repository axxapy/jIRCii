package rero.gui.dialogs.help;

import rero.gui.dialogs.HelpWindow;

import javax.swing.*;
import java.awt.*;

public abstract class HelperObject {
	public abstract JComponent getNavigationComponent();

	protected HelpWindow help;

	public JComponent getNavigation() {
		JPanel general = new JPanel();
		general.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		general.setLayout(new BorderLayout(5, 5));

		JScrollPane genScroller = new JScrollPane(getNavigationComponent());
		genScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		general.add(new JLabel("Contents:"), BorderLayout.NORTH);
		general.add(genScroller, BorderLayout.CENTER);

		return general;
	}

	public void setHelp(HelpWindow _help) {
		help = _help;
	}

	public void updateText(String newText) {
		help.updateText(newText);
	}
}
