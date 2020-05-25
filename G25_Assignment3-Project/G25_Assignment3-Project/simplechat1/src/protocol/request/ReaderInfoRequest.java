package protocol.request;

import client.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import messages.Message;
import messages.MessageType;

public class ReaderInfoRequest implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7228862891624675294L;
	/**
	 * 
	 */

	public ReaderInfoRequest() 
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
		return MessageType.READER_INFO_REQUEST;
	}

}

