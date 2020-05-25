/**
 * 
 */
package Server;

import java.util.HashMap;
import java.util.Map;
import messages.MessageType;
import messages.handlers.AbstractRequestHandler;
import messages.handlers.HandlerFactory;

/**
 * Contain all handlers
 * 
 * @author Monera
 *
 */
public class Handlers {

	/**
	 * Instance of DB Connections
	 */
	private Database dbCon;

	/**
	 * Handlers Container
	 */
	private Map<MessageType, AbstractRequestHandler> handlers;

	/**
	 * Constructor
	 * 
	 * @param dbCon
	 */
	public Handlers(Database dbCon) {
		// super();
		this.dbCon = dbCon;
		handlers = new HashMap<MessageType, AbstractRequestHandler>();
	}

	/**
	 * Get Handler
	 * 
	 * @param msg
	 * @return
	 */
	public AbstractRequestHandler getHandler(MessageType msg) {
		AbstractRequestHandler handler = handlers.get(msg);

		if (handler == null) {

			handler = HandlerFactory.create(msg, dbCon);
			handlers.put(msg, handler);
		}
		return handler;
	}
}
