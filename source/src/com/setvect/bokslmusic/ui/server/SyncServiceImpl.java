package com.setvect.bokslmusic.ui.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.ui.client.SyncService;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

@SuppressWarnings("serial")
@Controller
public class SyncServiceImpl extends RemoteServiceServlet implements SyncService {
	@Autowired
	private MusicService musicService;

	public List<MusicDirectoryModel> getSyncList() throws IllegalArgumentException {
		List<MusicDirectoryModel> list = new ArrayList<MusicDirectoryModel>();
		MusicDirectoryModel a = new MusicDirectoryModel();
		a.setBasePath("몽실이");
		list.add(a);
		return list;
	}
}
