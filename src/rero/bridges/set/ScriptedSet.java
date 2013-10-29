package rero.bridges.set;

import rero.bridges.alias.ScriptAlias;
import rero.script.LocalVariables;
import sleep.engine.Block;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptInstance;
import sleep.runtime.ScriptVariables;
import sleep.runtime.SleepUtils;

import java.util.HashMap;

public class ScriptedSet extends ScriptAlias implements rero.ircfw.interfaces.FrameworkConstants {
	protected boolean timestamp = true;

	public void setTimeStamp(boolean b) {
		timestamp = b;
	}

	public ScriptedSet(ScriptInstance si, Block _code) {
		super(si, _code, null);
	}

	public ScriptedSet(ScriptInstance si, Block _code, ScriptAlias _predecessor) {
		super(si, _code, _predecessor);
	}

	public String parseSet(HashMap data) {
		Scalar rv;

		synchronized (owner.getScriptVariables()) {
			ScriptVariables vars = owner.getScriptVariables();

			vars.pushLocalLevel();

			LocalVariables localLevel = (LocalVariables) vars.getLocalVariables();
			localLevel.setDataSource(data);

			//
			// execute the block of code
			//
			rv = SleepUtils.runCode(code, owner.getScriptEnvironment());

			vars.popLocalLevel();

			if (rv == null) {
				return null;
			}

			return rv.stringValue();
		}
	}

	public boolean isTimeStamped() {
		return timestamp;
	}
}
