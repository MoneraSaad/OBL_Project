package protocol.request;

import client.entities.Book;
import client.entities.User;
import messages.Message;
import messages.MessageType;


/**
 * 
 * @author Rania
 *
 */
public class ViewTOCRequest implements Message {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3193307025505910367L;
	private Book book;
	
	

	public ViewTOCRequest(Book book) {
		this.book = book;
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
		return MessageType.VIEW_TOC_REQUEST;
	}

}
