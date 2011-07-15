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
import com.setvect.bokslmusic.ui.shared.model.SyncDirectoryModel;
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

	public List<SyncDirectoryModel> getSyncList() throws IllegalArgumentException {
		List<SyncDirectoryModel> list = new ArrayList<SyncDirectoryModel>();
		// ServletRequestAttributes sra = ((ServletRequestAttributes)
		// RequestContextHolder.currentRequestAttributes());
		// HttpServletRequest req = sra.getRequest();

		List<MusicDirectory> dir = musicService.getMusicDirectory();
		for (MusicDirectory d : dir) {
			SyncDirectoryModel a = new SyncDirectoryModel();
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
		musicService.createMusicDirectory(item);
		return true;
	}

	public boolean removeMusicPath(String dir) {
		musicService.removeMusicDirectory(dir);
		return true;
	}

	public boolean syncDirectory(String dir) {
		File syncDir = new File(dir);
		serviceSync.syncDirectory(syncDir);
		// DB에 저장된 자료와 로컬에 저장된 파일과의 비교, 로컬에 해당하는 파일이 없으면 DB에서 정보삭제
		serviceSync.syncDb();
		return true;
	}

	public String getSyncLog() {
		return SyncLogPrinter.getLogAndClear();
	}
}
