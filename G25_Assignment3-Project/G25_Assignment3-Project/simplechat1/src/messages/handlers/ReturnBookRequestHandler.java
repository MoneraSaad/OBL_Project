
package messages.handlers;

import java.sql.SQLException;
import java.text.ParseException;

import messages.Message;
import protocol.request.ReturnBookRequest;
import protocol.response.ReturnBookResponse;
import Server.Database;
/**
 * @author shams
 *
 */
public class ReturnBookRequestHandler extends AbstractRequestHandler {
		/**
		 * If the message request kind of ReturnBookRequest we call the appropriate
		 * method from the database .
		 * <P>
		 * <isLoggedIn()> check if the user is logged in .
		 * <P>
		 * After that we build LoginResponse message and return it.
		 * 
		 */
		public ReturnBookRequestHandler(Database dbCon) {
			super(dbCon);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see messages.RequestHandler#handle(messages.Message)
		 */
		@Override
		public Message handle(Message msg) throws SQLException, ParseException {
			
			try {
				  if(!dbCon.isExistBarcode(((ReturnBookRequest)msg).getBarcode())) 
				  {
					  return ReturnBookResponse.NO_Barcode();
				  }
				  else
			      {
					  if(dbCon.isBookReturned(((ReturnBookRequest)msg).getBarcode()))
					  {
						  return ReturnBookResponse.isReturned();
					  }
					   dbCon.insertNewReturning(((ReturnBookRequest)msg).getBarcode());
					   dbCon.lessNumOfBorrowedBook(((ReturnBookRequest)msg).getBarcode());
					   dbCon.updateCopiesInBorrowAndAvailableCopies(((ReturnBookRequest)msg).getBarcode());
					   dbCon.updateStatusOfBorrowedBook(((ReturnBookRequest)msg).getBarcode());
					if(dbCon.isDate(((ReturnBookRequest)msg).getBarcode()) == false) 
					{
						dbCon.increaseNumOfLateReturning(((ReturnBookRequest)msg).getBarcode());
						if(dbCon.checkDateOfAllBooksIfLateReturningBook(((ReturnBookRequest)msg).getBarcode()) == false)
						{
							dbCon.updateStatusOfReader(dbCon.getReaderID(((ReturnBookRequest)msg).getBarcode()),"Active","","");
						}
					    
					}
				  
			}
				    ReturnBookResponse response = new ReturnBookResponse("OK");
					return response;
			} catch (Exception e) {
				e.printStackTrace();
				return ReturnBookResponse.NO_Barcode();
			}

}
}
