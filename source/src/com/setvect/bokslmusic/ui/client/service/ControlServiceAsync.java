package com.setvect.bokslmusic.ui.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ControlServiceAsync {

	void echo(String message, AsyncCallback<String> callback);

}
