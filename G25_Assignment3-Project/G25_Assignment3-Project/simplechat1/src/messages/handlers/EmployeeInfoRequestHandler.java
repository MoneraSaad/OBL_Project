/**
 * 
* */package messages.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import Server.Database;
import client.entities.User;
import messages.Message;
import protocol.response.EmployeeInfoResponse;

/**
 * @author shams
 *
 */
public class EmployeeInfoRequestHandler extends AbstractRequestHandler {

	public EmployeeInfoRequestHandler(Database dbCon) {
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
			ArrayList<User> list = dbCon.getEmployeeInfo();
			EmployeeInfoResponse resp = new EmployeeInfoResponse(list);
			return resp;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
   }
}