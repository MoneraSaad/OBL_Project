package messages.handlers;

import java.sql.SQLException;
import java.text.ParseException;

import Server.Database;
import client.controller.ReaderInfoController;
import messages.Message;
import protocol.request.ManualExtendDeadlineRequest;
import protocol.response.BorrowFormSubmitRespone;
import protocol.response.ExtendDeadlineResponse;

public class ExtendDeadlineRequestHandler extends AbstractRequestHandler {

	public ExtendDeadlineRequestHandler(Database dbCon) {
		super(dbCon);
		// TODO Auto-generated constructor stub
	}



	@Override
	public Message handle(Message msg) {
		try {

			if(!dbCon.checkLenderToIfExistsAutomatic(((ManualExtendDeadlineRequest) msg).getUserWhoseRequestedDeadlineExtension()))
				return ExtendDeadlineResponse.lenderToDoesNotExist();
		if(!dbCon.checkLessThanWeek(((ManualExtendDeadlineRequest) msg).getUserWhoseRequestedDeadlineExtension(),((ManualExtendDeadlineRequest) msg).getRequestedBookToExtend()))
				 	return ExtendDeadlineResponse.ExtendCannotBeDone();
		if(!dbCon.checkIfThereIsOrders(((ManualExtendDeadlineRequest) msg).getRequestedBookToExtend()))
					return ExtendDeadlineResponse.ordersOnBook();
		if(dbCon.updateField(((ManualExtendDeadlineRequest) msg).getUserWhoseRequestedDeadlineExtension(),((ManualExtendDeadlineRequest) msg).getRequestedBookToExtend())== true) {
		{
			ExtendDeadlineResponse response = new ExtendDeadlineResponse("OK");
			if(((ManualExtendDeadlineRequest) msg).getFlag()!=0)
			{System.out.println("OMGYEEEEEEEEEEEEEE");
				dbCon.insertDetailsForBothRequesterAndMaker(((ManualExtendDeadlineRequest) msg).getUserWhoseRequestedDeadlineExtension(),((ManualExtendDeadlineRequest) msg).getUserWhoMakeTheDeadlineExtension(),((ManualExtendDeadlineRequest) msg).getRequestedBookToExtend());
			}
			return response;
		}
				 
		}else {
			ExtendDeadlineResponse response = new ExtendDeadlineResponse("can't extend deadline");
			return response;	
		}
		
		
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	
	}

}
