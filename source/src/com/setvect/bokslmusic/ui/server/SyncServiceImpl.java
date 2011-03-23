package com.setvect.bokslmusic.ui.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.setvect.bokslmusic.ui.client.SyncService;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

@SuppressWarnings("serial")
public class SyncServiceImpl extends RemoteServiceServlet implements SyncService {
	public List<MusicDirectoryModel> getSyncList() throws IllegalArgumentException {
		List<MusicDirectoryModel> list = new ArrayList<MusicDirectoryModel>();
		MusicDirectoryModel a = new MusicDirectoryModel();
		a.setBasePath("몽실이");
		list.add(a);
		return list;
	}
}
