package com.setvect.bokslmusic.ui.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.setvect.bokslmusic.ui.client.MusicDirectory;
import com.setvect.bokslmusic.ui.client.SyncService;

@SuppressWarnings("serial")
public class SyncServiceImpl extends RemoteServiceServlet implements SyncService {
	public List<MusicDirectory> getSyncList() throws IllegalArgumentException {
		List<MusicDirectory> list = new ArrayList<MusicDirectory>();
		MusicDirectory a = new MusicDirectory();
		a.setBasePath("몽실이");
		list.add(a);
		return list;
	}
}
