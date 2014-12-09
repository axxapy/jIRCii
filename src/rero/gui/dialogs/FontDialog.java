package rero.gui.dialogs;

import rero.config.ClientDefaults;
import rero.gui.dck.DMain;

import java.awt.*;

public class FontDialog extends DMain {
	public String getTitle() {
		return "Font Setup";
	}

	public String getDescription() {
		return "Font Settings";
	}

	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();
		addBlankSpace();
		addBlankSpace();
		addBlankSpace();

		addLabelNormal("Client Font:", FlowLayout.LEFT);
		addFontInput("ui.font", ClientDefaults.ui_font);

		addBlankSpace();

		addCheckboxInput("ui.antialias", ClientDefaults.ui_antialias, "Enable text anti-aliasing", 'A', FlowLayout.CENTER);

		addBlankSpace();

		addCharsetInput("client.encoding", "Use Charset:", 'c', 75);

		addBlankSpace();
		addBlankSpace();
		addBlankSpace();
		addBlankSpace();
	}
}



