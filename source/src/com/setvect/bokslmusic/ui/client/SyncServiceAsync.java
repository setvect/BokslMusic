package com.setvect.bokslmusic.ui.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SyncServiceAsync {
	void getSyncList(AsyncCallback<List<MusicDirectoryModel>> callback) throws IllegalArgumentException;

	void addSyncPath(String dir, AsyncCallback<Boolean> asyncCallback);
}
