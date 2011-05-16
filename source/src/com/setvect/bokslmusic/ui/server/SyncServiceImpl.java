package com.setvect.bokslmusic.ui.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.log.SyncLogPrinter;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.service.music.MusicSyncService;
import com.setvect.bokslmusic.ui.client.service.SyncService;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.common.log.LogPrinter;

/**
 * 
 */
@Service("SyncService")
public class SyncServiceImpl implements SyncService {
	@Autowired
	private MusicService musicService;

	@Autowired
	private MusicSyncService serviceSync;

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
		String path = dir.trim();
		File syncDir = new File(path);
		if (!syncDir.exists() || !syncDir.isDirectory()) {
			LogPrinter.out.warn(dir + "은(는) 올바른 경로가 아닙니다.");
			return false;
		}
		MusicDirectory item = new MusicDirectory();

		item.setBasePath(path);
		musicService.createMusicPath(item);
		return true;
	}

	public boolean removeMusicPath(String dir) {
		musicService.removeMusicPath(dir);
		return true;
	}

	public boolean syncDirectory(String dir) {
		File syncDir = new File(dir);
		serviceSync.syncDirectory(syncDir);
		return true;
	}

	public String getSyncLog() {
		return SyncLogPrinter.getLogAndClear();
	}
}
