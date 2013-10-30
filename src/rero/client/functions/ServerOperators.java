package rero.client.functions;

import rero.client.Feature;
import rero.config.models.ServerConfig;
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
		//script.getScriptEnvironment().getEnvironment().put("&getAllNetworks", new getAllNetworks());

		script.getScriptEnvironment().getEnvironment().put("&getServerInfo", new getServerInfo());

		script.getScriptEnvironment().getEnvironment().put("&serverInfoHost", new serverInfoHost());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoPortRange", new serverInfoPorts());
		//script.getScriptEnvironment().getEnvironment().put("&serverInfoNetwork", new serverInfoNetwork());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoIsSecure", new serverInfoIsSecure());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoPassword", new serverInfoPassword());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoDescription", new serverInfoDescription());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoConnectPort", new serverInfoConnectPort());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoCommand", new serverInfoCommand());
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

	private static class serverInfoPassword implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			ServerConfig temp = ServerConfig.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) return SleepUtils.getEmptyScalar();
			return SleepUtils.getScalar(temp.getPassword());
		}
	}

	private static class serverInfoDescription implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			ServerConfig temp = ServerConfig.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) return SleepUtils.getEmptyScalar();
			return SleepUtils.getScalar(temp.getDescription());
		}
	}

	private static class serverInfoConnectPort implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			ServerConfig temp = ServerConfig.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) return SleepUtils.getEmptyScalar();
			return SleepUtils.getScalar(temp.getConnectPort());
		}
	}

	private static class serverInfoCommand implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			ServerConfig temp = ServerConfig.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) return SleepUtils.getEmptyScalar();
			return SleepUtils.getScalar(temp.getCommand());
		}
	}

	private static class serverInfoHost implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			ServerConfig temp = ServerConfig.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) return SleepUtils.getEmptyScalar();
			return SleepUtils.getScalar(temp.getHost());
		}
	}

	private static class serverInfoPorts implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			ServerConfig temp = ServerConfig.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) return SleepUtils.getEmptyScalar();
			return SleepUtils.getScalar(temp.getPorts());
		}
	}

	private static class serverInfoIsSecure implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			ServerConfig temp = ServerConfig.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) return SleepUtils.getEmptyScalar();

			if (temp.isSecure()) {
				return SleepUtils.getScalar(1);
			}

			return SleepUtils.getEmptyScalar();
		}
	}
}

