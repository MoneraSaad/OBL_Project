
package protocol.response;

import java.util.ArrayList;
import client.entities.User;
import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Shams
 *
 */
public class LockResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private ArrayList<User> Reader;

	public LockResponse(ArrayList<User> reader) {
		this.Reader = reader;
	}
	
	public LockResponse(String text) {
		this.text = text;
	}
	
	
	public ArrayList<User> getReader() {
		return Reader;
	}
	
	public void setReader(ArrayList<User> reader) {
		this.Reader = reader;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * response message : This is about if someone had borrowed a book and didn't retun it 
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.LOCK_RESPONSE;
	}
}


