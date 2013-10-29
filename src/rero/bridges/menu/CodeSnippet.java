package rero.bridges.menu;

import sleep.engine.Block;
import sleep.runtime.ScriptInstance;

public class CodeSnippet {
	protected ScriptInstance owner;
	protected Block code;

	public CodeSnippet(ScriptInstance _owner, Block _code) {
		owner = _owner;
		code = _code;
	}

	public Block getBlock() {
		return code;
	}

	public ScriptInstance getOwner() {
		return owner;
	}
}
