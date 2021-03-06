package rero.ircfw.interfaces;

import rero.ircfw.Channel;
import rero.ircfw.User;

public interface ChannelDataWatch {
	public void userAdded(User user);

	public void userChanged(); // nick change or a mode change.

	public void userRemoved(User user);

	public void createChannel(Channel channel);
}
