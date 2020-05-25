package protocol.request;

import messages.Message;
import messages.MessageType;
import client.entities.User;

/**
 * @author Monera
 */
public class LoginRequest implements Message {

	/**
	 *  Asking to login
	 */
	private static final long serialVersionUID = 7228862891624675294L;
	/**
	 * 
	 */
	private User user;

	public LoginRequest(User user) {
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
		return MessageType.LOGIN_REQUEST;
	}

}
