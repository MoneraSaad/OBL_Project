package protocol.request;

import client.entities.Book;
import client.entities.User;
import messages.Message;
import messages.MessageType;


/**
 * 
 * @author Karam
 *
 */
public class OrderBookRequest implements Message {
	
	private static final long serialVersionUID = 7228862891624675294L;
	/**
	 * 
	 */
	private User user;
	private Book book;
	
	

	public OrderBookRequest(User user,Book book) {
		this.user = user;
		this.book = book;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.Message#getMessageType()
	 */
	@Override
	public MessageType getMessageType() {
		return MessageType.ORDER_BOOK_REQUEST;
	}

}
