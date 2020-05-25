package protocol.request;

import messages.Message;
import messages.MessageType;

import java.util.ArrayList;

import client.entities.User;

/**
 * @author Monera
 */
public class LogoutRequest implements Message {
	/**
	 * Asking to logout
	 */
	private static final long serialVersionUID = 1L;
	private User user;



	public LogoutRequest(User user) {
		this.user = user;
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.Message#getMessageType()
	 */
	@Override
	public MessageType getMessageType() {
		return MessageType.LOGOUT_REQUEST;
	}


}
