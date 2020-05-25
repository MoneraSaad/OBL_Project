package protocol.response;

import java.util.ArrayList;

import client.entities.Book;
import messages.Message;
import messages.MessageType;

public class SearchBookByNameResponse implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Book> recievedBooks;

	/**
	 * response message : This is about SearchBook
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate response text
	 */
	public SearchBookByNameResponse(ArrayList<Book> books) {
		this.recievedBooks = books;
	}

	/** 
	 * This is a static method
	 * 
	 * @return User found response
	 */

	@Override
	public MessageType getMessageType() {
		return MessageType.SEARCH_BOOK_BY_NAME_RESPONSE;
	}

	public ArrayList<Book> getRecievedBooks() {
		return recievedBooks;
	}

	public void setRecievedBooks(ArrayList<Book> recievedBooks) {
		this.recievedBooks = recievedBooks;
	}

}
