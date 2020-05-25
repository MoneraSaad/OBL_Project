package protocol.request;


import client.entities.BorrowedBook;

import client.entities.User;
import messages.Message;
import messages.MessageType;

public class ManualExtendDeadlineRequest implements Message{
	/**
	 * 
	 */

	private static final long serialVersionUID = -5063368804627967066L;
	private BorrowedBook requestedBookToExtend;
	private User userWhoseRequestedDeadlineExtension;
	private User userWhoMakeTheDeadlineExtension;
	private int flag;
	public ManualExtendDeadlineRequest(BorrowedBook requestedBookToExtendDeadline,User userWhoseRequestedDeadlineExtension) {
		this.requestedBookToExtend = requestedBookToExtendDeadline;
		this.userWhoseRequestedDeadlineExtension=userWhoseRequestedDeadlineExtension;
	}
	public ManualExtendDeadlineRequest(BorrowedBook book, User wantedUser, User currUser) {
		setRequestedBookToExtend(book);

		this.userWhoseRequestedDeadlineExtension=wantedUser;
		this.userWhoMakeTheDeadlineExtension=currUser;
		// TODO Auto-generated constructor stub
	}
	public BorrowedBook getRequestedBookToExtend() {
		return requestedBookToExtend;
	}
	public void setRequestedBookToExtend(BorrowedBook requestedBookToExtend) {
		this.requestedBookToExtend = requestedBookToExtend;
	}
	public User getUserWhoseRequestedDeadlineExtension() {
		return userWhoseRequestedDeadlineExtension;
	}
	public void setUserWhoseRequestedDeadlineExtension(User userWhoseRequestedDeadlineExtension) {
		this.userWhoseRequestedDeadlineExtension = userWhoseRequestedDeadlineExtension;
	}

	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.DEADLINE_EXTENSION_REQUEST;
	}
	public User getUserWhoMakeTheDeadlineExtension() {
		return userWhoMakeTheDeadlineExtension;
	}
	public void setUserWhoMakeTheDeadlineExtension(User userWhoMakeTheDeadlineExtension) {
		this.userWhoMakeTheDeadlineExtension = userWhoMakeTheDeadlineExtension;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

}
