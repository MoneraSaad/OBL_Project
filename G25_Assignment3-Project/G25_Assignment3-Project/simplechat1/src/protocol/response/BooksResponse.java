package protocol.response;

import java.util.ArrayList;

import client.entities.Book;
import client.entities.User;
import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Monera
 *
 */
public class BooksResponse implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;

	/**
	 * response message : This is about the get all books
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	private ArrayList<Book> books;

	public BooksResponse(ArrayList<Book> books) {
		super();
		this.books = books;
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}
	@Override
	public MessageType getMessageType() {
		return MessageType.BOOKS_RESPONSE;
	}


}
