package com.setvect.bokslmusic.ui.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSession;

import net.zschech.gwt.comet.server.CometServlet;
import net.zschech.gwt.comet.server.CometSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.setvect.bokslmusic.ui.client.service.ChatMessage;
import com.setvect.bokslmusic.ui.client.service.ChatService;

/**
 * A simple implementation of {@link ChatService} which maintains all its state
 * in memory.
 * 
 * @author Richard Zschech
 */
public class ChatServiceImpl extends RemoteServiceServlet implements ChatService {

	/** */
	private static final long serialVersionUID = 8035293688996885129L;

	/**
	 * A mapping of user names to CometSessions used for routing messages.
	 */
	private ConcurrentMap<String, CometSession> users = new ConcurrentHashMap<String, CometSession>();

	public String getUsername() {
		// check if there is a HTTP session setup.
		HttpSession httpSession = getThreadLocalRequest().getSession(false);
		if (httpSession == null) {
			return null;
		}

		// return the user name
		return (String) httpSession.getAttribute("username");
	}

	/**
	 * @see net.zschech.gwt.chat.client.ChatService#login(java.lang.String)
	 */
	public void login(String username) {
		// Get or create the HTTP session for the browser
		HttpSession httpSession = getThreadLocalRequest().getSession();
		// Get or create the Comet session for the browser
		CometSession cometSession = CometServlet.getCometSession(httpSession);
		// Remember the user name for the
		httpSession.setAttribute("username", username);

		// setup the mapping of user names to CometSessions
		if (users.putIfAbsent(username, cometSession) != null) {
			// some one else has already logged in with this user name
			httpSession.invalidate();
		}
	}

	/**
	 * @see net.zschech.gwt.chat.client.ChatService#logout(java.lang.String)
	 */
	public void logout(String username) {
		// check if there is a HTTP session setup.
		HttpSession httpSession = getThreadLocalRequest().getSession(false);
		if (httpSession == null) {
		}

		// check if there is a Comet session setup. In a larger application the
		// HTTP session may have been
		// setup via other means.
		CometSession cometSession = CometServlet.getCometSession(httpSession, false);
		if (cometSession == null) {
		}

		// check the user name parameter matches the HTTP sessions user name
		if (!username.equals(httpSession.getAttribute("username"))) {
		}

		// remove the mapping of user name to CometSession
		users.remove(username, cometSession);
		httpSession.invalidate();
	}

	/**
	 * @see net.zschech.gwt.chat.client.ChatService#send(java.lang.String)
	 */
	public void send(String message) {
		// check if there is a HTTP session setup.
		HttpSession httpSession = getThreadLocalRequest().getSession(false);
		if (httpSession == null) {
		}

		// get the user name for the HTTP session.
		String username = (String) httpSession.getAttribute("username");
		if (username == null) {
		}

		// create the chat message
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setUsername(username);
		chatMessage.setMessage(message);

		for (Map.Entry<String, CometSession> entry : users.entrySet()) {
			entry.getValue().enqueue(chatMessage);
		}
	}

	public void setStatus(String status) {
		// Get or create the HTTP session for the browser
		HttpSession httpSession = getThreadLocalRequest().getSession();
		// Get or create the Comet session for the browser
		final CometSession cometSession = CometServlet.getCometSession(httpSession);
		final String st = status;
		Thread t = new Thread() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					String message = "#####" + st;
					cometSession.enqueue(message);
					System.out.println(message + " 보냄");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
	}
}
