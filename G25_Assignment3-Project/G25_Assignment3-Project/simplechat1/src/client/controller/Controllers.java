/**
 * 
 */
package client.controller;

import java.util.HashMap;
import java.util.Map;

import client.ChatClient;


/**
 * @author Monera
 *
 */
public class Controllers {

	/**
	 * Singleton instance
	 */
	private static Controllers instance;

	/**
	 * Client instance
	 */
	private ChatClient client;

	/**
	 * Constructor
	 * 
	 * @param client
	 */
	private Controllers(ChatClient client) {
		super();
		this.client = client;
		controllers = new HashMap<ControllerType, AbstractController>();
	}

	/**
	 * Create new instance
	 * 
	 * @param client
	 * @return
	 */
	public synchronized static Controllers newInstance(ChatClient client) {
		
			if (instance == null) {
				instance = new Controllers(client);
			}
	
		return instance;
	}

	/**
	 * Get Instance
	 * 
	 * @return
	 */
	public static Controllers getInstance() {
		if (instance == null) {
			System.out.println("ERROR: Controller instance not found");
		}
		return instance;
	}

	/**
	 * Controllers Container
	 */
	private Map<ControllerType, AbstractController> controllers;

	/**
	 * GetController
	 * 
	 * @param controllerType
	 * @return
	 */
	public AbstractController getController(ControllerType controllerType) {
		AbstractController controller = controllers.get(controllerType);
		if (controller == null) {
			controller = ControllerFactory.create(controllerType, client);
			controllers.put(controllerType, controller);
		}
		return controller;
	}

}
