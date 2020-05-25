/**
 * 
 */
package client.controller;

import client.ChatClient;


/**
 * @author Monera
 *
 * 
 */
public class AbstractController {

	protected ChatClient client;

	/**
	 * Constructor
	 * 
	 * @param client
	 */
	public AbstractController(ChatClient client) {
		super();
		this.client = client;
	}
	public AbstractController() {
		
	}

}
