
package protocol.response;

import java.util.ArrayList;

import com.mysql.jdbc.Blob;

import client.entities.Book;
import client.entities.HistoryIntity;
import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Shams
 *
 */
public class ShowBorrowedBooksResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private ArrayList<Book> borrowedBooks;

	public ShowBorrowedBooksResponse(ArrayList<Book> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}
	public ShowBorrowedBooksResponse() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Book> getBorrowedBooks() {
		return borrowedBooks;
	}
	
	public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * response message : This is about Return Book
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.SHOW_BORROWED_BOOKS_RESPONSE;
	}
}

