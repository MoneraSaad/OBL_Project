package protocol.request;

import client.entities.Book;
import messages.Message;
import messages.MessageType;

public class EditBookRequest implements Message{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8978634558854469542L;
	private Book book;
	
	
	public EditBookRequest(Book book) {
		this.book=book;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book =  book;
	}
	
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.EDIT_BOOK_REQUEST;
	}

}
