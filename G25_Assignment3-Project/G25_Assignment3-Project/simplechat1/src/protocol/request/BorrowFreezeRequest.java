
package protocol.request;

import messages.Message;
import messages.MessageType;

public class BorrowFreezeRequest implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7228862891624675294L;
	/**
	 * 
	 */
	private String ID;

	public BorrowFreezeRequest() 
	{
		super();
	}
	
	public BorrowFreezeRequest(String ID) {
		this.ID=ID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.Message#getMessageType()
	 */
	@Override
	public MessageType getMessageType() {
		return MessageType.BORROW_FREEZE_REQUEST;
	}

}

