package messages.handlers;

/**
 * @author Sari
 *
 */
import java.sql.SQLException;

import messages.Message;
import protocol.request.BorrowFormSubmitRequest;
import protocol.request.LoginRequest;
import protocol.response.BorrowFormSubmitRespone;
import protocol.response.LoginResponse;
import Server.Database;

public class BorrowFormRequestHandler extends AbstractRequestHandler {
	/**

	 */
	public BorrowFormRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) {
		try {	
			if(dbCon.ifItIsAvailable(((BorrowFormSubmitRequest)msg).getBorrowedBook())==false)//new 
				return BorrowFormSubmitRespone.bookIsNotAvailable();
			else	
				if(dbCon.copyExists(((BorrowFormSubmitRequest)msg).getBorrowedBook())==false)
					return BorrowFormSubmitRespone.barcodeDoesNotExist();
			else 	
				if(dbCon.lenderToExists(((BorrowFormSubmitRequest)msg).getBorrowedBook())==false)
					return BorrowFormSubmitRespone.lenderToDoesnotExist();
				else 	
					if(dbCon.lenderExists(((BorrowFormSubmitRequest)msg).getBorrowedBook())==false)
							return BorrowFormSubmitRespone.lenderDoesnotExist();
						else if(dbCon.checkIfThereIsOrders(((BorrowFormSubmitRequest)msg).getBorrowedBook())==false) {
							if(dbCon.orderBelongsToLenderTo(((BorrowFormSubmitRequest)msg).getBorrowedBook())==false)// new //tazbet enu yo5ed wa7d mn l3 
								if(!dbCon.availabiltyOfTheBook(((BorrowFormSubmitRequest)msg).getBorrowedBook()))//new 
										return BorrowFormSubmitRespone.thereAreOrders();
						}	
							if(dbCon.updateBorrowedBook(((BorrowFormSubmitRequest)msg).getBorrowedBook())==false)
								return BorrowFormSubmitRespone.borrowExists();
							
			dbCon.updateFields(((BorrowFormSubmitRequest)msg).getBorrowedBook());
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		BorrowFormSubmitRespone response = new BorrowFormSubmitRespone("OK");
		return response;		
	}

}
/**
 * 
 */



