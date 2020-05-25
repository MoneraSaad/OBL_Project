package protocol.request;

import client.entities.Book;
import messages.Message;
import messages.MessageType;

public class DeleteBookRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6355008326023979095L;
	private Book book;
	
	
	public DeleteBookRequest(Book book) {
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
		return MessageType.DELETE_BOOK_REQUEST;
	}
}
