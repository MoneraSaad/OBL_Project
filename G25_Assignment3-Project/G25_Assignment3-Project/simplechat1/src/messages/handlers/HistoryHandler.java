
package messages.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import Server.Database;
import client.entities.HistoryIntity;
import client.entities.User;
import messages.Message;
import protocol.request.HistoryRequest;
import protocol.response.HistoryResponse;

/**
 * @author shams
 *
 */
public class HistoryHandler extends AbstractRequestHandler {

	public HistoryHandler(Database dbCon) {
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
			ArrayList<HistoryIntity> list = dbCon.getreaderHistory(((HistoryRequest) msg).getUser());
			HistoryResponse resp = new HistoryResponse(list);
			return resp;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
   }
}