

package protocol.response;

import java.util.ArrayList;

import client.entities.Book;
import messages.Message;
import messages.MessageType;
/**
 * 
 * @author Monera
 *
 */

public class SearchBookByAuthorResponse implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Book> recievedBooks;
	String text;
	/**
	 * response message : This is about SearchBook
	 * <p>
	 * This message includes appropriate AarrayList response .
	 * 
	 * @param appropriate response text
	 */
	public SearchBookByAuthorResponse(ArrayList<Book> books) {
		this.recievedBooks = books;
	}



	public SearchBookByAuthorResponse(String text) {
		this.text=text;
	}



	public ArrayList<Book> getRecievedBooks() {
		return recievedBooks;
	}
 
	public void setRecievedBooks(ArrayList<Book> recievedBooks) {
		this.recievedBooks = recievedBooks;
	}
	/**
	 * This is a static method
	 * 
	 * @return book found response
	 */

	@Override
	public MessageType getMessageType() {
		return MessageType.SEARCH_BOOK_BY_AUTHOR_RESPONSE;
	}

}

