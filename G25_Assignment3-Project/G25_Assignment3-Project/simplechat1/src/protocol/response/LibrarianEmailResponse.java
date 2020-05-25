package protocol.response;

import java.util.ArrayList;

import client.entities.User;
import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Monera
 *
 */
public class LibrarianEmailResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private ArrayList<String> Emails;

	public LibrarianEmailResponse(ArrayList<String> Emails) {
		this.Emails = Emails;
	}
	public ArrayList<String> getEmails() {
		return Emails;
	}
	
	public void setEmails(ArrayList<String> Emails) {
		this.Emails = Emails;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * response message : This is about Librarians Email
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.LIBRARIAN_EMAIL_RESPONSE;
	}
}

