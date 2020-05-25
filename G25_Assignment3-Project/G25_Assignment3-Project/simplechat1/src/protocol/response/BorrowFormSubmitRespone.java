package protocol.response;

import client.entities.BorrowedBook;
import client.entities.User;
import messages.Message;
import messages.MessageType;

public class BorrowFormSubmitRespone implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7531241539452918983L;
	private BorrowedBook borrowedBook;
	private String text;
	
	public BorrowFormSubmitRespone(String text) {
		this.text = text;
	}
	public BorrowedBook getBorrowedBook() {
		return borrowedBook;
	}

	public void setBorrowedBook(BorrowedBook borrowedBook) {
		this.borrowedBook = borrowedBook;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public static BorrowFormSubmitRespone bookIsNotAvailable() {
		return new BorrowFormSubmitRespone("Book is not available or the orders may be more than the book availabilty");
	}
	public static BorrowFormSubmitRespone barcodeDoesNotExist() {
		return new BorrowFormSubmitRespone("Barcode doesn't exist please try again");
	}
	public static BorrowFormSubmitRespone lenderToDoesnotExist() {
		return new BorrowFormSubmitRespone("Lender To doesn't exist please try again");
	}
	public static BorrowFormSubmitRespone lenderDoesnotExist() {
		return new BorrowFormSubmitRespone("Lender doesn't exist please try again");
	}
	public static BorrowFormSubmitRespone borrowExists() {
		return new BorrowFormSubmitRespone("The borrow is already made before on this copy please try available copy");
	}
	public static BorrowFormSubmitRespone successfulBorrowing() {
		return new BorrowFormSubmitRespone("Borrowing process is completed");
	}
	public static BorrowFormSubmitRespone thereAreOrders() {
		return new BorrowFormSubmitRespone("Borrowing failed because there are orders on this book");
	}
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.BORROW_FORM_RESPONE;
	}
	


}
