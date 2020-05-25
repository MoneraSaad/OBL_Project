

package messages.handlers;

import messages.Message;
import protocol.request.GetClosestDeadlineRequest;
import protocol.request.SearchBookByAuthorRequest;
import protocol.request.SearchBookByNameRequest;
import protocol.response.GetClosestDeadlineResponse;
import protocol.response.SearchBookByAuthorResponse;
import protocol.response.SearchBookByNameResponse;

import java.sql.SQLException;
import java.util.ArrayList;

import Server.Database;
import client.entities.Book;

/**
 * @author Monera
 *
 */
public class GetClosestDeadlineRequestHandler extends AbstractRequestHandler {

	/**
	 * If the message request kind of GetClosestDeadlineRequest we call the appropriate
	 * method from the database . After that we build GetClosestDeadlineRespone message
	 * and return it.
	 * 
	 */
	public GetClosestDeadlineRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) {
		String deadline;
		try {
			
			deadline = dbCon.getClosestDeadline(((GetClosestDeadlineRequest)msg).getBook()); 
			if(deadline!="No") {
			GetClosestDeadlineResponse response = new GetClosestDeadlineResponse(deadline);
			return response;
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		

	}
}