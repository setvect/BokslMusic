package com.setvect.bokslmusic.ui.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.setvect.bokslmusic.ui.client.SyncService;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

@SuppressWarnings("serial")
public class SyncServiceImpl extends RemoteServiceServlet implements SyncService {
	public List<MusicDirectoryModel> getSyncList() throws IllegalArgumentException {
		List<MusicDirectoryModel> list = new ArrayList<MusicDirectoryModel>();
		list.add(new MusicDirectoryModel("복슬이", new Date()));
		list.add(new MusicDirectoryModel("몽실이", new Date()));
		return list;
	}
}
