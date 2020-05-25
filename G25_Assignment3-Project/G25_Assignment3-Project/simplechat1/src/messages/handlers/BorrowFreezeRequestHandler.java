
package messages.handlers;

import java.util.ArrayList;
import Server.Database;
import client.entities.User;
import messages.Message;
import protocol.request.BorrowFreezeRequest;
import protocol.request.LoginRequest;
import protocol.response.BorrowFreezeResponse;

/**
 * @author Monera
 *
 */
public class BorrowFreezeRequestHandler extends AbstractRequestHandler {

	public BorrowFreezeRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) {
		if((((BorrowFreezeRequest) msg).getID())!=null) {
			dbCon.changeStatus(((BorrowFreezeRequest) msg).getID());
			BorrowFreezeResponse resp = new BorrowFreezeResponse("OK");
			dbCon.deleteTheUser(((BorrowFreezeRequest) msg).getID());
			return resp;
		}else {
		ArrayList<User> list = dbCon.getReaderWhoDidntReturn();
		BorrowFreezeResponse resp = new BorrowFreezeResponse(list);
		return resp;
		}
   }
}