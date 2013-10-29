package rero.client;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.ListIterator;

public class WatchableData {
	protected LinkedList listeners;
	protected ChangeEvent eventObject;

	public WatchableData() {
		listeners = new LinkedList();
		eventObject = new ChangeEvent(this);
	}

	public void addChangeListener(ChangeListener l) {
		listeners.add(new WeakReference(l));
	}

	public void removeChangeListener(ChangeListener l) {
		listeners.remove(l);
	}

	public void fireEvent() {
		ListIterator i = listeners.listIterator();
		while (i.hasNext()) {
			ChangeListener temp = (ChangeListener) (((WeakReference) i.next()).get());

			if (temp == null)
				i.remove();
			else
				temp.stateChanged(eventObject);
		}
	}
}
