
package protocol.request;

import client.ClientConnectToServer;
import client.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import messages.Message;
import messages.MessageType;

public class ShowBorrowedBooksRequest implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7228862891624675294L;
	/**
	 * 
	 */
	private User user;
	
	
	public ShowBorrowedBooksRequest(User user) 
	{
		this.setUser(user);
	}
	public ShowBorrowedBooksRequest() 
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.Message#getMessageType()
	 */
	@Override
	public MessageType getMessageType() {
		return MessageType.SHOW_BORROWED_BOOKS_REQUEST;
	}

	public User getUser() {

		return this.user;
		
	}
	public void setUser(User user) {
		this.user = user;
	}

}

