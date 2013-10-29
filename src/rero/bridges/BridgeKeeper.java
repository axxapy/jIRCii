package rero.bridges;

import rero.bridges.alias.AliasEnvironment;
import rero.bridges.bind.BindEnvironment;
import rero.bridges.event.EventBridge;
import rero.bridges.menu.MenuBridge;
import rero.bridges.set.SetEnvironment;
import rero.bridges.subs.SubroutineEnvironment;
import rero.ircfw.ChatFramework;
import sleep.interfaces.Loadable;

import java.util.WeakHashMap;

public class BridgeKeeper {
	protected Loadable scriptBridges[];

	public BridgeKeeper() {
		scriptBridges = new Loadable[6];
		scriptBridges[0] = new EventBridge();
		scriptBridges[1] = new AliasEnvironment();
		scriptBridges[2] = new SetEnvironment();
		scriptBridges[3] = new BindEnvironment();
		scriptBridges[4] = new MenuBridge();
		scriptBridges[5] = new SubroutineEnvironment();
	}

	// === Process Imports ===================================================================================

	public void announceFramework(ChatFramework ircfw) {
		((EventBridge) scriptBridges[0]).announceFramework(ircfw);
	}

	// === Export Data Structures ============================================================================

	public void storeDataStructures(WeakHashMap centralDataRepository) {
		centralDataRepository.put("eventBridge", scriptBridges[0]);
		centralDataRepository.put("aliasBridge", scriptBridges[1]);
		centralDataRepository.put("setBridge", scriptBridges[2]);
		centralDataRepository.put("bindBridge", scriptBridges[3]);
		centralDataRepository.put("menuBridge", scriptBridges[4]);
	}

	// === Export Capabilities ===============================================================================

	public Loadable[] getScriptBridges() {
		return scriptBridges;
	}
}
