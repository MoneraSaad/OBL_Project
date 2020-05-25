package protocol.request;

import messages.Message;
import messages.MessageType;

public class ReturnBookRequest implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7228862891624675294L;
	/**
	 * 
	 */
	private String barcode;

	public ReturnBookRequest(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.Message#getMessageType()
	 */
	@Override
	public MessageType getMessageType() {
		return MessageType.RETURN_BOOK_REQUEST;
	}

}
