package rero.gui.components.toolbar;

import java.awt.event.MouseEvent;

public interface Action {
	public void actionPerformed(MouseEvent ev);

	public String getDescription();

	public int getIndex();
}
