package rero.gui.windows;

import rero.client.Capabilities;
import rero.client.notify.Lag;
import rero.config.ClientDefaults;
import rero.config.ClientState;
import rero.config.ClientStateListener;
import rero.config.Config;
import rero.gui.IRCAwareComponent;
import rero.gui.background.BackgroundToolBar;
import text.LabelDisplay;
import text.TextSource;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.HashMap;

public class WindowStatusBar extends BackgroundToolBar implements IRCAwareComponent, ChangeListener, ClientStateListener {
	protected LabelDisplay contents;
	protected HashMap event;
	protected StatusWindow parent;
	protected Capabilities capabilities;
	protected long lastRehash;

	public void installCapabilities(Capabilities c) {
		capabilities = c;

		Lag temp = (Lag) capabilities.getDataStructure("lag");
		temp.addChangeListener(this);
	}

	public void stateChanged(ChangeEvent ev) {
		rehash();
		repaint();
	}

	public WindowStatusBar(StatusWindow _parent) {
		contents = new LabelDisplay();

		setFloatable(false);

		setLayout(new BorderLayout());
		add(contents, BorderLayout.CENTER);

		event = new HashMap();

		parent = _parent;

		setOpaque(false);

		setBorder(BorderFactory.createEmptyBorder(0, TextSource.UNIVERSAL_TWEAK, 0, TextSource.UNIVERSAL_TWEAK));
//      setBorder(null); 

		rehashValues();

		ClientState.getInstance().addClientStateListener("ui.sbarlines", this);
		ClientState.getInstance().addClientStateListener("ui.showsbar", this);
		ClientState.getInstance().addClientStateListener("ui.font", this);
	}

	public Dimension getPreferredSize() {
		if (contents.getTotalLines() == 0) {
			return new Dimension(Integer.MAX_VALUE, 1);
		}
		return super.getPreferredSize();
	}

	public void rehashValues() {
		if (Config.getInstance().isOption("ui.showsbar", true)) {
			int lines = Config.getInstance().getInteger("ui.sbarlines", ClientDefaults.ui_sbarlines);
			contents.setNumberOfLines(lines);
		} else {
			contents.setNumberOfLines(0);
		}
	}

	public void propertyChanged(String var, String parms) {
		if (var.equals("statusbar")) {
			repaint();
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					rehashValues();
					rehash();
					revalidate();
					repaint();
				}
			});
		}
	}

	public void rehash() {
		if (capabilities == null)
			return;

		event.put("$query", parent.getQuery());
		event.put("$window", parent.getName());

		for (int x = 0; x < contents.getTotalLines(); x++) {
			event.put("$line", "" + x);

			String lhs = capabilities.getOutputCapabilities().parseSet(event, "SBAR_LEFT");
			String rhs = capabilities.getOutputCapabilities().parseSet(event, "SBAR_RIGHT");

			if (lhs == null) {
				lhs = "";
			}
			if (rhs == null) {
				rhs = "";
			}

			contents.setLine(lhs, rhs, x);
		}

		lastRehash = System.currentTimeMillis();
	}

	public void paint(Graphics g) {
		if ((System.currentTimeMillis() - lastRehash) > 10000) {
			rehash();  // force a rehash every 10 seconds regardless...
		}

		super.paint(g);
	}

/*   protected void finalize()
   {
     System.out.println("Finalizing Window Status Bar");
   } */
}
