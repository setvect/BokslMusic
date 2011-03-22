package com.setvect.bokslmusic.ui.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SyncServiceAsync {
	void getSyncList(AsyncCallback<List<MusicDirectory>> callback) throws IllegalArgumentException;
}
