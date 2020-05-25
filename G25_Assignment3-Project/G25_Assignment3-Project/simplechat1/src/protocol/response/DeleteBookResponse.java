package protocol.response;

import client.entities.Book;
import messages.Message;
import messages.MessageType;

public class DeleteBookResponse implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9049884160899051035L;
	private Book book;
	private String text;
	
	public DeleteBookResponse(String text) {
		this.text = text;
	}
	
	public static DeleteBookResponse Book_Existence() {
		return new DeleteBookResponse("The Book doesn`t exist in the library");
	}
	public static DeleteBookResponse ERROR() {
		return new DeleteBookResponse("ERROR");
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book =  book;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.DELETE_BOOK_RESPONSE;
	}
	
}
