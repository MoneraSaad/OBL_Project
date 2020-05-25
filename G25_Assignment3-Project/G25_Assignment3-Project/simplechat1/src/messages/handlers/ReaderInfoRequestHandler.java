/**
 * 
 */
package messages.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import Server.Database;
import client.entities.User;
import messages.Message;
import protocol.response.ReaderInfoResponse;

/**
 * @author shams
 *
 */
public class ReaderInfoRequestHandler extends AbstractRequestHandler {

	public ReaderInfoRequestHandler(Database dbCon) {
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
			ArrayList<User> list = dbCon.getReaderInfo();
			ReaderInfoResponse resp = new ReaderInfoResponse(list);
			return resp;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
   }
}