package com.setvect.bokslmusic.ui.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.ui.client.SyncService;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

@Service("SyncService")
public class SyncServiceImpl implements SyncService {
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
