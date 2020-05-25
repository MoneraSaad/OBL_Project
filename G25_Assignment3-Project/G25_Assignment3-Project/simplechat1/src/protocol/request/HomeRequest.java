

package protocol.request;

import messages.Message;
import messages.MessageType;
import client.entities.User;

/**
 * @author Monera
 */
public class HomeRequest implements Message {

	/**
	 *  Asking to go to the home page
	 */
	private static final long serialVersionUID = -9206835604858770075L;
	/**
	 * 
	 */
	
	/**
	 * 
	 */
	private User user;
	
	public HomeRequest() {
		
	}

	public HomeRequest(User user) {
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
		return MessageType.HOME_REQUEST;
	}

}
