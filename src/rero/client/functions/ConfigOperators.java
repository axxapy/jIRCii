package rero.client.functions;

import rero.client.Feature;
import rero.config.ClientState;
import rero.config.Config;
import rero.config.StringList;
import sleep.interfaces.Function;
import sleep.interfaces.Loadable;
import sleep.interfaces.Predicate;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

import java.util.Iterator;
import java.util.Stack;

public class ConfigOperators extends Feature implements Loadable {
	public void init() {
		getCapabilities().getScriptCore().addBridge(this);
	}

	public void scriptLoaded(ScriptInstance script) {
		script.getScriptEnvironment().getEnvironment().put("&getProperty", new getProperty());
		script.getScriptEnvironment().getEnvironment().put("&getPropertyList", new getPropertyList());
		script.getScriptEnvironment().getEnvironment().put("&setProperty", new setProperty());
		script.getScriptEnvironment().getEnvironment().put("&setPropertyList", new setPropertyList());
		script.getScriptEnvironment().getEnvironment().put("&baseDirectory", new getBaseDirectory());


		script.getScriptEnvironment().getEnvironment().put("-isSetT", new isSet1());
		script.getScriptEnvironment().getEnvironment().put("-isSetF", new isSet2());
	}

	public void scriptUnloaded(ScriptInstance script) {
	}

	private static class getPropertyList implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			String a = locals.pop().toString();

			Scalar value = SleepUtils.getArrayScalar();
			Iterator i = Config.getInstance().getStringList(a).getList().iterator();
			while (i.hasNext()) {
				value.getArray().push(SleepUtils.getScalar(i.next().toString()));
			}

			return value;
		}
	}

	private static class setPropertyList implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			String a = locals.pop().toString();
			Scalar b = (Scalar) locals.pop();

			StringList list = Config.getInstance().getStringList(a);

			list.clear();

			Iterator i = b.getArray().scalarIterator();
			while (i.hasNext()) {
				list.add(i.next().toString());
			}
			list.save();

			return SleepUtils.getEmptyScalar();
		}
	}

	private static class getProperty implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			String a = locals.pop().toString();
			String def = "";

			if (!locals.isEmpty()) {
				def = locals.pop().toString();
			}

			return SleepUtils.getScalar(Config.getInstance().getString(a, def));
		}
	}

	private static class setProperty implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			String a = locals.pop().toString();
			String b = locals.pop().toString();

			Config.getInstance().setString(a, b);

			return SleepUtils.getEmptyScalar();
		}
	}

	private static class isSet1 implements Predicate {
		public boolean decide(String f, ScriptInstance si, Stack locals) {
			String a = locals.pop().toString();

			return Config.getInstance().getBoolean(a, true);
		}
	}

	private static class isSet2 implements Predicate {
		public boolean decide(String f, ScriptInstance si, Stack locals) {
			String a = locals.pop().toString();

			return Config.getInstance().getBoolean(a, false);
		}
	}

	private static class getBaseDirectory implements Function {
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			return SleepUtils.getScalar(ClientState.getInstance().getBaseDirectory().getAbsolutePath());
		}
	}
}
