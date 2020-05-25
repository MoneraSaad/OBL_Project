
/**
 * 
 */
package messages.handlers;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import messages.Message;
import protocol.request.AddBookRequest;
import protocol.response.AddBookResponse;
import Server.Database;

/**
 * @author Rania
 *
 */
public class AddBookRequestHandler extends AbstractRequestHandler {

	/**
	
	 * 
	 */
	public AddBookRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) throws SQLException { 
		try {
		if(dbCon.addBook(((AddBookRequest)msg).getBook()) == false)
			return AddBookResponse.Book_Exists();
		dbCon.insertBookCopies(((AddBookRequest)msg).getBook());//change in controller
			AddBookResponse response = new AddBookResponse("OK");
			return response;
		}catch(SQLException ex)
		{
			ex.printStackTrace();
			return AddBookResponse.ERROR();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
			
	}
}


