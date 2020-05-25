

/**
 * 
 */
package protocol.response;

import messages.Message;
import messages.MessageType;
import client.entities.User;

/**
 * 
 * @author Monera
 *
 */
public class HomeResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private User user;

	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.LOGIN_RESPONSE;
	}

	/**
	 * response message : This is about Home page
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	public HomeResponse(String text) {
		this.text = text;
	}
	
	/**
	 * This is a static method
	 * 
	 * @return User response
	 */
	public static HomeResponse user_user() {
		return new HomeResponse("User");
	}
	
	/**
	 * This is a static method
	 * 
	 * @return Manager response
	 */
	public static HomeResponse user_manager() {
		return new HomeResponse("Manager");
	}
	/**
	 * This is a static method
	 * 
	 * @return Reader response
	 */
	public static HomeResponse user_Reader() {
		return new HomeResponse("Reader");
	}
	
	/**
	 * This is a static method
	 * 
	 * @return Librarian response
	 */
	public static HomeResponse user_Librarian() {
		return new HomeResponse("Librarian");
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

