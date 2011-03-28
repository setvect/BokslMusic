package com.setvect.bokslmusic.ui.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.ui.client.SyncService;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;
import com.setvect.bokslmusic.vo.music.MusicDirectory;

/**
 * 
 */
@Service("SyncService")
public class SyncServiceImpl implements SyncService {
	@Autowired
	private MusicService musicService;

	public List<MusicDirectoryModel> getSyncList() throws IllegalArgumentException {
		List<MusicDirectoryModel> list = new ArrayList<MusicDirectoryModel>();
		// ServletRequestAttributes sra = ((ServletRequestAttributes)
		// RequestContextHolder.currentRequestAttributes());
		// HttpServletRequest req = sra.getRequest();

		List<MusicDirectory> dir = musicService.getMusicPathList();
		for (MusicDirectory d : dir) {
			MusicDirectoryModel a = new MusicDirectoryModel();
			a.setBasePath(d.getBasePath());
			a.setSyncDate(d.getSyncDate());
			list.add(a);
		}
		return list;
	}

	public boolean addSyncPath(String dir) {
		MusicDirectory item = new MusicDirectory();
		item.setBasePath(dir);
		musicService.createMusicPath(item);
		return true;
	}
}
