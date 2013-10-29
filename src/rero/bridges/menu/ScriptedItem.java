package rero.bridges.menu;

import rero.script.ScriptCore;
import sleep.engine.Block;
import sleep.runtime.ScriptInstance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ScriptedItem extends JMenuItem implements ActionListener {
	protected ScriptInstance owner;
	protected Block code;

	public ScriptedItem(ScriptInstance _owner, String _label, Block _code) {
		if (_label.indexOf('&') > -1) {
			setText(_label.substring(0, _label.indexOf('&')) + _label.substring(_label.indexOf('&') + 1, _label.length()));
			setMnemonic(_label.charAt(_label.indexOf('&') + 1));
		} else {
			setText(_label);
		}

		owner = _owner;
		code = _code;

		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		HashMap locals = ScriptedPopupMenu.getMenuData();

		if (locals == null) {
			locals = new HashMap();
		}
		locals.put("$command", e.getActionCommand());

		ScriptCore.runCode(owner, code, locals);
	}
}
