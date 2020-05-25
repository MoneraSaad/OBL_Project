/**
 * 
 */
package messages.handlers;

import java.sql.SQLException;
import java.util.ArrayList;

import messages.Message;
import protocol.request.LoginRequest;
import protocol.request.SearchBookByAuthorRequest;
import protocol.response.LoginResponse;
import protocol.response.SearchBookByAuthorResponse;
import Server.Database;
import client.entities.Book;
import client.entities.User;

/**
 * @author Monera
 *
 */
public class LoginRequestHandler extends AbstractRequestHandler {

	/**
	 * If the message request kind of LoginRequest we call the appropriate
	 * method from the database .
	 * <P>
	 * <isUser()> check if the user ID exist .
	 * <P>
	 * <checkPassword()> check if the user password is correct .
	 * <P>
	 * <isLoggedIn()> check if the user is logged in .
	 * <P>
	 * After that we build LoginResponse message and return it.
	 * 
	 */
	public LoginRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) {
		//return msg;		//REMOVE THIS!!!!

		try {

			// Check if user exists
			if (dbCon.isUser(((LoginRequest) msg).getUser()) == false) {
				return LoginResponse.NO_User();
			} else {
				if (dbCon.checkPassword(((LoginRequest) msg).getUser()) == false) {
					return LoginResponse.WRONG_PASSWORD();
				} else {
					if (dbCon.isLoggedIn(((LoginRequest) msg).getUser()) == true) {
						
						return LoginResponse.LOGGED_IN();
					}
						else {
							if (dbCon.isLocked(((LoginRequest) msg).getUser()) == false) {
								return LoginResponse.Locked();
							}
						}
					

				}
				
				dbCon.markAsLoggedIn(((LoginRequest) msg).getUser());
				
				User currUser = dbCon.fillUser(((LoginRequest) msg).getUser());
				LoginResponse response = new LoginResponse(currUser);
				response.setUser(dbCon.fillUser(((LoginRequest) msg).getUser()));
				
				return response;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return LoginResponse.ERROR();
		}
	
	}
}


