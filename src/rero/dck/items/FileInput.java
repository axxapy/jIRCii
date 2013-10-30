package rero.dck.items;

import rero.config.ClientState;
import rero.dck.SmallButton;
import rero.dck.SuperInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileInput extends SuperInput implements ActionListener {
	protected JLabel label;
	protected JTextField text;
	protected SmallButton button;
	protected String value;
	protected JFileChooser chooser;

	protected boolean directory;

	public FileInput(String _variable, String _value, String _label, char mnemonic, boolean _directory, int inset) {
		text = new JTextField();
		button = new SmallButton(text.getBorder(), "Click to open a file chooser");
		label = new JLabel(_label);

		button.addActionListener(this);

		setLayout(new BorderLayout(2, 2));

//      button.setPreferredSize(new Dimension((int)button.getPreferredSize().getWidth(), (int)text.getPreferredSize().getHeight()));

		add(label, BorderLayout.WEST);
		add(text, BorderLayout.CENTER);

		add(button, BorderLayout.EAST);

		label.setLabelFor(button);
		label.setDisplayedMnemonic(mnemonic);

		variable = _variable;
		value = _value;

		directory = _directory;

		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, inset));
	}

	public void actionPerformed(ActionEvent ev) {
		if (chooser == null) {
			chooser = new JFileChooser();
		}

		if (directory) {
			chooser.setApproveButtonText("Select Directory");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else {
			chooser.setApproveButtonText("Select File");
		}

		if (chooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION) {
			text.setText(chooser.getSelectedFile().getAbsolutePath());
			text.requestFocus();
			//button.setPreferredSize(new Dimension((int)button.getPreferredSize().getWidth(), (int)text.getPreferredSize().getHeight()));
		}

		notifyParent();
	}

	public void save() {
		ClientState.getInstance().setString(getVariable(), text.getText());
	}

	public int getEstimatedWidth() {
		return (int) label.getPreferredSize().getWidth();
	}

	public void setAlignWidth(int width) {
		label.setPreferredSize(new Dimension(width, 0));
		revalidate();
	}

	public JComponent getComponent() {
		return this;
	}

	public void refresh() {
		text.setText(ClientState.getInstance().getString(getVariable(), value));
	}
}


