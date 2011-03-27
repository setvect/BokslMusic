package com.setvect.bokslmusic.ui.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service/SyncService")
public interface SyncService extends RemoteService {
	List<MusicDirectoryModel> getSyncList() throws IllegalArgumentException;
}
