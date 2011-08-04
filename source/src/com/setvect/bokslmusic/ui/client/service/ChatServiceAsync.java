package com.setvect.bokslmusic.ui.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChatServiceAsync {
	public void getUsername(AsyncCallback<String> callback);

	public void login(String username, AsyncCallback<Void> callback);

	public void logout(String username, AsyncCallback<Void> callback);

	public void send(String message, AsyncCallback<Void> callback);

	public void setStatus(String status, AsyncCallback<Void> callback);
}
