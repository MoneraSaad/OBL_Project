/**
 * 
 */
package messages.handlers;

import java.sql.SQLException;

import messages.Message;
import protocol.request.HomeRequest;
import protocol.request.LoginRequest;
import protocol.response.HomeResponse;
import protocol.response.LoginResponse;
import Server.Database;

/**
 * @author Monera
 *
 */
public class HomeRequestHandler extends AbstractRequestHandler {

	/**
	 * If the message request kind of HomeRequest we call the appropriate method
	 * from the database .
	 * After that we build HomeResponse message and return it.
	 * 
	 */

	public HomeRequestHandler(Database dbCon) {
		super(dbCon);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) {
		return msg;
	
	

		//return msg;		//REMOVE THIS!!!!
//
//		try {
//
//			// Check if user exists
//			if (dbCon.fillUser(((HomeRequest) msg).getUser())) {
//				return HomeResponse.user_user();
//			} else {
//				if (dbCon.checkPassword(((HomeRequest) msg).getUser()) == false) {
//					return HomeResponse.user_manager();
//				} else {
//					if (dbCon.isLoggedIn(((HomeRequest) msg).getUser()) == true) {
//						//System.out.println("xxxxxxxxxxxxxxxxxxxxx");
//						return HomeResponse.user_Librarian();
//						
//					} else {
//						if (dbCon.isLoggedIn(((HomeRequest) msg).getUser()) == true) {
//							//System.out.println("xxxxxxxxxxxxxxxxxxxxx");
//							return HomeResponse.user_Reader();
//							
//						}
//
//				}
//				}
//			}
//		
//				System.out.println("wwwwwwwwwwwwwwwwwwwwwwww");
//				dbCon.markAsLoggedIn(((HomeRequest) msg).getUser());
//				HomeResponse response = new HomeResponse("OK");
//				response.setUser(dbCon.fillUser(((HomeRequest) msg).getUser()));
//				//System.out.println(response);///////////////
//				return response;
//			
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return LoginResponse.ERROR();
//		}
//	
	}

}
