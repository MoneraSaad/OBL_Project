package protocol.response;

import messages.Message;
import messages.MessageType;
import client.entities.User;

/**
 * 
 * @author Shams
 *
 */
public class RegistrationResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private User user;

	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.REGISTRATION_RESPONSE;
	}

	/**
	 * response message : This is about Login
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	public RegistrationResponse(String text) {
		this.text = text;
	}

	/**
	 * This is a static method
	 * 
	 * @return Login Successfully response
	 */
	public static RegistrationResponse oK() {
		return new RegistrationResponse("Successful registration");
	}

	/**
	 * This is a static method
	 * 
	 * @return User found response
	 */
	public static RegistrationResponse user_Exist() {
		return new RegistrationResponse("User found!/try _ again!!");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
