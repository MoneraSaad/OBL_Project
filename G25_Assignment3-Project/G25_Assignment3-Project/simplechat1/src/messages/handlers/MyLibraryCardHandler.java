
package messages.handlers;


import java.sql.SQLException;

import Server.Database;
import messages.Message;
import protocol.request.MyLibraryCardRequest;
import protocol.response.MyLibraryCardResponse;

/**
 * 
 * @author Monera
 */
public class MyLibraryCardHandler extends AbstractRequestHandler {
	/**
	 * If the message request kind of LogoutRequest we call the appropriate
	 * method from the database , <markAsLoggedOut()> make the user logged out.
	 * After that we build LogoutResponse message and return it.
	 * 
	 */
	public MyLibraryCardHandler(Database dbCon) {
		super(dbCon);
	}

	@Override
	public Message handle(Message msg) {
		

		try {
			if(((MyLibraryCardRequest)msg).getUserCard()!=5)
			{
				dbCon.saveEditForReader(((MyLibraryCardRequest)msg).getUser());
				return new MyLibraryCardResponse("Details has been saved successfully",((MyLibraryCardRequest) msg).getUser());
			}
			if(((MyLibraryCardRequest)msg).getUserCard()==5)
			{
				dbCon.managerEditDetailsForReader(((MyLibraryCardRequest)msg).getUser());
				return new MyLibraryCardResponse("Details has been saved successfully",((MyLibraryCardRequest) msg).getUser());
			}
			
			
 
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new MyLibraryCardResponse("Error in the program",((MyLibraryCardResponse) msg).getUser());
		}
		return msg;
	}

}
