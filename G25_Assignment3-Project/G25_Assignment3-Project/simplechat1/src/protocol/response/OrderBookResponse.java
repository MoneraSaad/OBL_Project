
package protocol.response;

import messages.Message;
import messages.MessageType;
import client.entities.Book;
import client.entities.User;

/**
 * 
 * @author Karam
 *
 */
public class OrderBookResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private User user;
	private Book book;

	public OrderBookResponse(String text)
	{
		this.text=text;
	}
	


	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.ORDER_BOOK_RESPONSE;
	}

	
	public  OrderBookResponse(String text,User user,Book book) {
		this.book = book;
		this.user = user;
		this.text = text;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
