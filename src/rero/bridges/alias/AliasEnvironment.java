package rero.bridges.alias;

import sleep.engine.Block;
import sleep.interfaces.Environment;
import sleep.interfaces.Loadable;
import sleep.runtime.ScriptInstance;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class AliasEnvironment implements Loadable, Environment {
	public HashMap aliases; // table to store the actual block associated with the function.

	public AliasEnvironment() {
		aliases = new HashMap();
	}

	//
	// this code is much simpler than the code in DefaultEnvironment mainly because I know that all scripts
	// in this case will be sharing the same environment so I'm safe to do this.   Sleep itself assumes that
	// scripts may end up being isolated from eachother.
	//
	public void scriptUnloaded(ScriptInstance si) {
		String key;

		Iterator i = aliases.keySet().iterator();
		while (i.hasNext()) {
			key = (String) i.next();

			ScriptAlias alias = (ScriptAlias) aliases.get(key);

			if (!alias.isValid()) {
				while (alias != null && !alias.isValid()) {
					alias = alias.getPredecessor();
				}

				if (alias == null) {
					i.remove(); // remove the current key
				} else {
					aliases.put(key, alias);
				}
			}
		}
	}

	public void scriptLoaded(ScriptInstance si) {
		Hashtable env = si.getScriptEnvironment().getEnvironment(); // assuming the environment is shared. hah right

		//
		// tell the environment that we want aliases to be bound here
		//
		env.put("alias", this);
	}

	public void bindFunction(ScriptInstance si, String type, String name, Block code) {
		ScriptAlias temp = null;

		name = name.toUpperCase();

		if (aliases.get(name) != null) {
			temp = (ScriptAlias) aliases.get(name);
		}

		aliases.put(name, new ScriptAlias(si, code, temp));
	}

	public boolean isAlias(String name) {
		return (aliases.get(name) != null);
	}

	public ScriptAlias getAlias(String name) {
		return (ScriptAlias) aliases.get(name);
	}

	public Collection getAliasList() {
		return aliases.keySet();
	}
}
