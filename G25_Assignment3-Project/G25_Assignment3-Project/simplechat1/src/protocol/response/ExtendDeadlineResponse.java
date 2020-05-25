package protocol.response;

import client.entities.BorrowedBook;
import client.entities.User;
import messages.Message;
import messages.MessageType;

public class ExtendDeadlineResponse implements Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8251481962939013509L;
	private BorrowedBook borrowedBook;
	private User user;
	private String text;
	
	public ExtendDeadlineResponse(String text) {
		this.text = text;
	}
	public BorrowedBook getBorrowedBook() {
		return borrowedBook;
	}

	public void setBorrowedBook(BorrowedBook borrowedBook) {
		this.borrowedBook = borrowedBook;
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
	public static ExtendDeadlineResponse ExtendCannotBeDone() {
		return new ExtendDeadlineResponse("Sorry Extention cannot be done while between today and the deadline more than 7 days");
	}
	public static ExtendDeadlineResponse ordersOnBook() {
		return new ExtendDeadlineResponse("Sorry Extention cannot be done while there are orders on the book");
	}
	public static ExtendDeadlineResponse lenderToDoesNotExist() {
		return new ExtendDeadlineResponse("Sorry Extention cannot be done while account is not active");
	}
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.DEADLINE_EXTENSION_RESPONSE;
	}


}
