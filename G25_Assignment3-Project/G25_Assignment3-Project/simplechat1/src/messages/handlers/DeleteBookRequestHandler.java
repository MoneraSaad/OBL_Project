
package messages.handlers;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import messages.Message;
import protocol.request.AddBookRequest;
import protocol.request.DeleteBookRequest;
import protocol.request.ReturnBookRequest;
import protocol.response.AddBookResponse;
import protocol.response.DeleteBookResponse;
import Server.Database;

/**
 * @author Rania
 *
 */
public class DeleteBookRequestHandler extends AbstractRequestHandler {

	/**
	
	 * 
	 */
	public DeleteBookRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) throws SQLException { 
		
		if(dbCon.deleteBook(((DeleteBookRequest)msg).getBook()) == false)
		{
			return DeleteBookResponse.Book_Existence();
		}
		dbCon.deleteCopy(((DeleteBookRequest)msg).getBook());
		DeleteBookResponse response = new DeleteBookResponse("OK");
			return response;
			
	}
}


