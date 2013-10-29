package rero.dialogs;

import rero.config.ClientDefaults;
import rero.dck.DMain;
import rero.dck.items.FileListInput;

import java.awt.*;

public class ScriptDialog extends DMain {
	public String getTitle() {
		return "Script Manager";
	}

	public String getDescription() {
		return "Script Manager";
	}

	public void setupDialog() {
		addLabel("The following scripts are currently loaded:", 30);
		addBlankSpace();
		addOther(new FileListInput("script.files", "Select Script", "Load Script", 'L', "Unload Script", 'U', 80, 125));
		addCheckboxInput("script.ignoreWarnings", ClientDefaults.script_ignoreWarnings, "Ignore script warnings", 'I', FlowLayout.LEFT);
		addCheckboxInput("script.verboseLoad", ClientDefaults.script_verboseLoad, "Verbose script loading/unloading", 'V', FlowLayout.LEFT);
	}
}



