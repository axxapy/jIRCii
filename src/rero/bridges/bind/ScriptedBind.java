package rero.bridges.bind;

import rero.bridges.alias.ScriptAlias;
import sleep.engine.Block;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

public class ScriptedBind extends ScriptAlias {
	public ScriptedBind(ScriptInstance si, Block _code) {
		super(si, _code, null);
	}

	public ScriptedBind(ScriptInstance si, Block _code, ScriptAlias _predecessor) {
		super(si, _code, _predecessor);
	}

	public void process() {
		synchronized (owner.getScriptVariables()) {
			SleepUtils.runCode(code, owner.getScriptEnvironment());
		}
	}
}
