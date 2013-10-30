package rero.gui.dck;

public interface DParent {
	public String getVariable(String variable);

	public void notifyParent(String variable);
}
