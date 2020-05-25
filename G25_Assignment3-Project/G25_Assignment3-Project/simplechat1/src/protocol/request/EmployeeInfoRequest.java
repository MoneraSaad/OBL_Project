package protocol.request;

import messages.Message;
import messages.MessageType;

public class EmployeeInfoRequest implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7228862891624675294L;
	/**
	 * 
	 */

	public EmployeeInfoRequest() 
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.Message#getMessageType()
	 */
	@Override
	public MessageType getMessageType() {
		return MessageType.EMPLOYEE_INFO_REQUEST;
	}

}

