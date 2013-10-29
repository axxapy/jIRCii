package rero.bridges.event;

import rero.script.LocalVariables;
import sleep.engine.atoms.Check;
import sleep.runtime.ScriptEnvironment;

import java.util.HashMap;

public class PredicateChatListener extends EventChatListener {
	protected ScriptEnvironment env;
	protected Check predicate;
	protected CodeSnippet code;

	public PredicateChatListener(ScriptEnvironment _env, Check _predicate, CodeSnippet c) {
		env = _env;
		predicate = _predicate;
		code = c;


		addListener(c);
	}

	public boolean isChatEvent(String eventId, HashMap eventDescription) {
		if (!code.isValid()) {
			return false;
		}

		env.getScriptVariables().pushLocalLevel();

		LocalVariables locals = (LocalVariables) env.getScriptVariables().getLocalVariables();
		locals.setDataSource(eventDescription);

		boolean check = predicate.check(env);

		env.getScriptVariables().popLocalLevel();

		return check;
	}
}
