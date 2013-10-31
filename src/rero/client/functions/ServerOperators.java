package rero.client.functions;

import rero.client.Feature;
import rero.config.ServersList;
import sleep.bridges.BridgeUtilities;
import sleep.interfaces.Function;
import sleep.interfaces.Loadable;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

import java.util.Stack;


public class ServerOperators extends Feature implements Loadable {
	public void init() {
		getCapabilities().getScriptCore().addBridge(this);
	}

	public void scriptLoaded(ScriptInstance script) {
		script.getScriptEnvironment().getEnvironment().put("&getAllServers", new getAllServers());
		script.getScriptEnvironment().getEnvironment().put("&getServerInfo", new getServerInfo());
	}

	public void scriptUnloaded(ScriptInstance script) {
	}

	private static class getAllServers implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			return SleepUtils.getArrayWrapper(ServersList.getServerData().getServers());
		}
	}

	private static class getServerInfo implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			String temp = BridgeUtilities.getString(locals, "");
			if (temp == null) return SleepUtils.getEmptyScalar();
			return SleepUtils.getScalar(ServersList.getServerData().getServerByName(temp).toString());
		}
	}
}

