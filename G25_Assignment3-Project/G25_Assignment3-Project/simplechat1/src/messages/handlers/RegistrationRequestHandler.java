package messages.handlers;

import java.sql.SQLException;

import messages.Message;
import protocol.request.RegistrationRequest;
import protocol.response.RegistrationResponse;
import Server.Database;

/**
 * @author Mickey
 *
 */
public class RegistrationRequestHandler extends AbstractRequestHandler {

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
	public RegistrationRequestHandler(Database dbCon) {
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
			  if(dbCon.isExistUser(((RegistrationRequest)msg).getUser()))
			  {
				  dbCon.isSuccessfulRegistration(((RegistrationRequest)msg).getUser());
				  return  RegistrationResponse.oK();
			  }
			  else
			  {
				  return RegistrationResponse.user_Exist();
			  }
		} catch (Exception e) {
			e.printStackTrace();
			return RegistrationResponse.user_Exist();
		}
	}
}

