package protocol.response;

import client.entities.Book;
import messages.Message;
import messages.MessageType;

public class AddBookResponse implements Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8818598592816468258L;
	private Book book;
	private String text;
	
	public AddBookResponse(String text) {
		this.text = text;
	}
	
	public static AddBookResponse Book_Exists() {
		return new AddBookResponse("The Book Already Exists In The Library");
	}
	
	public static AddBookResponse ERROR() {
		return new AddBookResponse("ERROR");
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
		return MessageType.ADD_BOOK_RESPONSE;
	}
	

}
