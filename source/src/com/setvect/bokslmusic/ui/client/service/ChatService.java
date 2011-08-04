package com.setvect.bokslmusic.ui.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * This is the interface for the chat communicating with the chat server.
 * 
 * @author Richard Zschech
 */
@RemoteServiceRelativePath("chat")
public interface ChatService extends RemoteService {

	/**
	 * Gets the currently logged on user name.
	 * 
	 * @return
	 * @throws ChatException
	 */
	public String getUsername();

	/**
	 * Login and setup a CometSession on the chat server.
	 * 
	 * @param username
	 * @throws ChatException
	 */
	public void login(String username);

	/**
	 * Logout and destroy the CometSession on the chat server.
	 * 
	 * @param username
	 * @throws ChatException
	 */
	public void logout(String username);

	/**
	 * Send a message to all users on the chat server.
	 * 
	 * @param message
	 * @throws ChatException
	 */
	public void send(String message);

	/**
	 * Send a status update message to all users on the chat server.
	 * 
	 * @param status
	 * @throws ChatException
	 */
	public void setStatus(String status);
}
