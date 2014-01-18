package rero.gui.windows;

import rero.bridges.menu.MenuBridge;
import rero.client.Capabilities;
import rero.gui.input.InputField;
import rero.gui.text.WrappedDisplay;
import rero.gui.text.event.ClickEvent;
import rero.gui.text.event.ClickListener;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.ListIterator;

public abstract class EmptyWindow extends StatusWindow implements ClientWindowListener {
	public void touch() {
	}

	public void installCapabilities(Capabilities c) {
		capabilities = c;
		menuManager = (MenuBridge) c.getDataStructure("menuBridge");

		init();
	}

	public InputField getInput() {
		return null;
	}

	public WrappedDisplay getDisplay() {
		return null;
	}

	public WindowStatusBar getStatusBar() {
		return null;
	}

	public void init(ClientWindow _frame) {
		frame = _frame;
		frame.addWindowListener(new ClientWindowStuff());
		frame.addWindowListener(this);

		frame.setContentPane(this);

		setTitle(getName());
		frame.setIcon(getImageIcon());
	}

	public abstract void init();

	public String getQuery() {
		return "Nada";
	}

	public void setQuery(String q) {
	}

	public void setTitle(String title) {
		frame.setTitle(title);
	}

	public ClientWindow getWindow() {
		return frame;
	}

	public String getTitle() {
		return frame.getTitle();
	}

	public abstract String getName();

	public abstract ImageIcon getImageIcon();

	public String getWindowType() {
		return "Other";
	}

	public boolean isLegalWindow() {
		return false;
	}

	private boolean isOpen = true;

	public boolean isOpen() {
		return isOpen;
	}

	public void onActive(ClientWindowEvent ev) {
	}

	public void onOpen(ClientWindowEvent ev) {
		isOpen = true;
	}

	public void onInactive(ClientWindowEvent ev) {
	}

	public void onMinimize(ClientWindowEvent ev) {
	}

	public void onClose(ClientWindowEvent ev) {
		isOpen = false;
	}

	protected LinkedList listeners = new LinkedList();

	public void addClickListener(ClickListener l) {
		listeners.add(l);
	}

	public void fireClickEvent(String text, MouseEvent mev) {
		ClickEvent ev = new ClickEvent(text, getName(), mev);

		ListIterator i = listeners.listIterator();
		while (i.hasNext() && !ev.isConsumed()) {
			ClickListener l = (ClickListener) i.next();
			l.wordClicked(ev);
		}
	}

	public int compareWindowType() {
		return 4;
	}
}
