package protocol.request;

import client.entities.BorrowedBook;
import messages.Message;
import messages.MessageType;

public class BorrowFormSubmitRequest implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4455845219680695277L;
	private BorrowedBook borrowedBook;
	
	public BorrowFormSubmitRequest(BorrowedBook borrowedBook) {
		this.borrowedBook = borrowedBook;
	}
	public BorrowedBook getBorrowedBook() {
		return borrowedBook;
	}
	public void setBorrowedBook(BorrowedBook borrowedBook) {
		this.borrowedBook = borrowedBook;
	}
	@Override
	public MessageType getMessageType() {
		return MessageType.BORROW_FORM_REQUEST;
	}
	

}
