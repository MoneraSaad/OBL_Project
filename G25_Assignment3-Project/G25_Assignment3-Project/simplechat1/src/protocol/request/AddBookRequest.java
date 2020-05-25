
package protocol.request;

import client.entities.Book;
import messages.Message;
import messages.MessageType;

public class AddBookRequest implements Message{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3891539703035689497L;
	private Book book;
	
	
	public AddBookRequest(Book book) {
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
		return MessageType.ADD_BOOK_REQUEST;
	}

}
