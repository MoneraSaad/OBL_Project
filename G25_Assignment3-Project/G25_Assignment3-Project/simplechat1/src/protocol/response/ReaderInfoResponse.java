/**
 * 
 */
package protocol.response;

import java.util.ArrayList;

import com.mysql.jdbc.Blob;
import client.entities.User;
import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Shams
 *
 */
public class ReaderInfoResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private ArrayList<User> Reader;

	public ReaderInfoResponse(ArrayList<User> reader) {
		this.Reader = reader;
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
	 * response message : This is about Return Book
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.READER_INFO_RESPONSE;
	}
}

