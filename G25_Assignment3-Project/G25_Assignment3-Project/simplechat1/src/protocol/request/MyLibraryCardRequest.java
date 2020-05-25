


package protocol.request;

import client.entities.Book;
import client.entities.User;
import messages.Message;
import messages.MessageType;

public class MyLibraryCardRequest implements Message{
	private int userCard;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3891539703035689497L;
	private User user;
	
public MyLibraryCardRequest() {
		
		

	}
	
	public MyLibraryCardRequest(User user, int userCard) {
		
		this.user=user;
		this.userCard=userCard;


	}

	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user =  user;
	}
	
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.MY_LIBRARY_CARD_REQUEST;
	}

	public int getUserCard() {
		return userCard;
	}

	public void setUserCard(int userCard) {
		this.userCard = userCard;
	}

}
