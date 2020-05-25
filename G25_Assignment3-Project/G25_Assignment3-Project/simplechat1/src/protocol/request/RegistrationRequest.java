package protocol.request;

import messages.Message;
import messages.MessageType;
import client.entities.User;

public class RegistrationRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7228862891624675294L;
	/**
	 * 
	 */
	private User user;

	public RegistrationRequest(User user) {
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
		return MessageType.REGISTRATION_REQUEST;
	}

}

