package com.setvect.bokslmusic.ui.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.setvect.bokslmusic.ui.shared.model.SyncDirectoryModel;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SyncServiceAsync {
	void getSyncList(AsyncCallback<List<SyncDirectoryModel>> callback) throws IllegalArgumentException;

	void addSyncPath(String dir, AsyncCallback<Boolean> asyncCallback);

	void removeMusicPath(String dir, AsyncCallback<Boolean> asyncCallback);

	void syncDirectory(String dir, AsyncCallback<Boolean> asyncCallback);

	void getSyncLog(AsyncCallback<String> asyncCallback);
}
