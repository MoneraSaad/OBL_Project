package protocol.response;

import client.entities.Book;
import messages.Message;
import messages.MessageType;

public class EditBookResponse implements Message {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3281199157389482225L;
	private Book book;
	private String text;
	
	public EditBookResponse(String text) {
		this.text = text;
	}
	
	/*public static EditBookResponse Book_Exists() {
		return new EditBookResponse("The Book already exists in the library");
	}*/
	
	public static EditBookResponse ERROR() {
		return new EditBookResponse("ERROR");
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
		return MessageType.EDIT_BOOK_RESPONSE;
	}
	

}
