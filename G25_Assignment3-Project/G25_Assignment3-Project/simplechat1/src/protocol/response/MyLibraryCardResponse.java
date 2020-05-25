
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
public class MyLibraryCardResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private User user;

	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.MY_LIBRARY_CARD_RESPONSE;
	}

	/**
	 * response message : This is about Home page
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	public MyLibraryCardResponse(String text) {
		this.text = text;
	}
	
	public MyLibraryCardResponse(String text,User user) {
		this.text = text;
		this.user=user;
	}
	
	/**
	 * This is a static method
	 * 
	 * @return User response
	 */
	
	/**
	 * This is a static method
	 * 
	 * @return Manager response
	 */

	/**
	 * This is a static method
	 * 
	 * @return Reader response
	 */
	public static MyLibraryCardResponse user_Reader() {
		return new MyLibraryCardResponse("Reader");
	}
	
	/**
	 * This is a static method
	 * 
	 * @return Librarian response
	 */


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

