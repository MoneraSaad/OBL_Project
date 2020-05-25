

package messages.handlers;

import java.util.ArrayList;
import Server.Database;
import client.entities.User;
import messages.Message;
import protocol.request.BorrowFreezeRequest;
import protocol.request.LockRequest;
import protocol.response.BorrowFreezeResponse;
import protocol.response.LockResponse;

/**
 * @author Monera
 *
 */
public class LockRequestHandler extends AbstractRequestHandler {

	public LockRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) {
		if((((LockRequest) msg).getID())!=null) {
			dbCon.changeStatusLock(((LockRequest) msg).getID());
			dbCon.deleteTheReader(((LockRequest) msg).getID());
			LockResponse resp = new LockResponse("OK");
			return resp;
		}else {
		ArrayList<User> list = dbCon.getThreeDaysLate();
		LockResponse resp = new LockResponse(list);
		return resp;
		}
   }
}