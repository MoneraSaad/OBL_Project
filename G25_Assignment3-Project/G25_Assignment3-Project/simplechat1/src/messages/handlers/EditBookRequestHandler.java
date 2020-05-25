/**
 * 
 */
package messages.handlers;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import messages.Message;
import protocol.request.AddBookRequest;
import protocol.request.EditBookRequest;
import protocol.response.AddBookResponse;
import protocol.response.EditBookResponse;
import Server.Database;

/**
 * @author Rania
 *
 */
public class EditBookRequestHandler extends AbstractRequestHandler {

	
	public EditBookRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg)  {
		try {
			dbCon.editBook(((EditBookRequest)msg).getBook());
			EditBookResponse response = new EditBookResponse("OK");
			return response;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	
		
	
	
	}
}


