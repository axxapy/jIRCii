package rero.client.functions;

import rero.client.Feature;
import rero.client.notify.NotifyData;
import rero.client.notify.NotifyUser;
import rero.ircfw.InternalDataList;
import sleep.interfaces.Function;
import sleep.interfaces.Loadable;
import sleep.interfaces.Predicate;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

import java.util.Stack;


public class NotifyOperators extends Feature implements Predicate, Function, Loadable {
	protected InternalDataList data;
	protected NotifyData notify;

	public void init() {
		getCapabilities().getScriptCore().addBridge(this);

		data = (InternalDataList) getCapabilities().getDataStructure("clientInformation");
		notify = (NotifyData) getCapabilities().getDataStructure("notify");
	}

	public void scriptLoaded(ScriptInstance script) {
		String[] contents = new String[]{
				"-isnotify",
				"-issignedon",
				"-issignedoff",
				"&getNotifyUsers",
				"&getSignedOnUsers",
				"&getSignedOffUsers",
				"&onlineFor",
				"&getAddressFromNotify"
		};

		for (int x = 0; x < contents.length; x++) {
			script.getScriptEnvironment().getEnvironment().put(contents[x], this);
		}
	}

	public void scriptUnloaded(ScriptInstance script) {
	}

	public Scalar evaluate(String function, ScriptInstance script, Stack locals) {
		if (function.equals("&onlineFor")) {
			if (locals.size() != 1) {
				return SleepUtils.getEmptyScalar();
			}

			NotifyUser temp = notify.getUserInfo(((Scalar) locals.pop()).getValue().toString());
			return SleepUtils.getScalar(temp.getTimeOnline());
		} else if (function.equals("&getAddressFromNotify")) {
			if (locals.size() != 1) {
				return SleepUtils.getEmptyScalar();
			}

			String temps = locals.pop().toString();

			NotifyUser temp = notify.getUserInfo(temps);

			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}

			return SleepUtils.getScalar(temp.getAddress());
		}

		if (function.equals("&getNotifyUsers")) {
			return SleepUtils.getArrayWrapper(notify.getNotifyUsers());
		} else if (function.equals("&getSignedOnUsers")) {
			return SleepUtils.getArrayWrapper(notify.getSignedOnUsers());
		} else if (function.equals("&getSignedOffUsers")) {
			return SleepUtils.getArrayWrapper(notify.getSignedOffUsers());
		}

		return null;
	}

	public boolean decide(String predicate, ScriptInstance script, Stack terms) {
		if (terms.size() != 1) {
			return false;
		}

		String nick = ((Scalar) terms.pop()).getValue().toString();

		NotifyUser user = notify.getUserInfo(nick);

		if (user == null) {
			return false;  // also acts as -isnotify, since the user will be null if not in the notify list.
		}

		if (predicate.equals("-signedon")) {
			return user.isSignedOn();
		}

		if (predicate.equals("-signedoff")) {
			return !user.isSignedOn();
		}

		return false;
	}
}
