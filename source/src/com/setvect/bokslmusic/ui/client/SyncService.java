package com.setvect.bokslmusic.ui.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("syncDirectory")
public interface SyncService extends RemoteService {
	List<MusicDirectory> getSyncList() throws IllegalArgumentException;
}
