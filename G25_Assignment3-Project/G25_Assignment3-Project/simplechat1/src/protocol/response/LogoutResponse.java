package protocol.response;

import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Monera
 *
 */
public class LogoutResponse implements Message {

	private static final long serialVersionUID = 1L;
	private String text;

	/**
	 * response message : This is about Logout
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	public LogoutResponse(String text) {
		this.text = text;
	}

	/**
	 * This is a static method
	 * 
	 * @return Logout Successfully response
	 */
	public static LogoutResponse OK() {
		return new LogoutResponse("Logout Successfull!");
	}

	/**
	 * This is a static method
	 * 
	 * @return Logout Error response
	 */
	public static LogoutResponse ERROR() {
		return new LogoutResponse("Logout ERROR!");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.LOGOUT_RESPONSE;
	}
}
