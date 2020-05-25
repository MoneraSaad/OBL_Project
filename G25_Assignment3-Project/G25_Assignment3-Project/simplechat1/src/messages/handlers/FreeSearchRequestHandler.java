
package messages.handlers;

import messages.Message;
import protocol.request.FreeSearchRequest;
import protocol.response.FreeSearchResponse;
import java.util.ArrayList;

import Server.Database;
import client.entities.Book;

/**
 * @author Monera
 *
 */
public class FreeSearchRequestHandler extends AbstractRequestHandler {

	/**
	 * If the message request kind of FreeSearchRequestHandler we call the appropriate
	 * method from the database . After that we build FreeSearchResponse message
	 * and return it.
	 * 
	 */
	public FreeSearchRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) {
		ArrayList<Book> recievedBooks = dbCon.freeSearchBook(((FreeSearchRequest)msg).getdescription());
		FreeSearchResponse response = new FreeSearchResponse(recievedBooks);
		return response;
	}
}