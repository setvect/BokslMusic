package com.setvect.bokslmusic;

import java.util.List;

import com.setvect.bokslmusic.boot.EnvirmentInit;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.vo.music.MusicDirectory;

/**
 * 동기화 디렉토리를 삭제/추가 함
 */
public class SyncDirectoryManager {
	private static final String[] addPath = { "D:\\90.멀티미디어" };

	public static void main(String[] args) {
		EnvirmentInit.bootUp();

		MusicService service = (MusicService) EnvirmentInit.getConfigSpring().getBean("MusicService");
		List<MusicDirectory> path = service.getMusicDirectory();
		for (MusicDirectory m : path) {
			service.removeMusicDirectory(m.getBasePath());
			System.out.printf("\"%s\" 삭제\n", m.getBasePath());
		}

		for (String p : addPath) {
			MusicDirectory dir = new MusicDirectory();
			dir.setBasePath(p);
			service.createMusicDirectory(dir);
			System.out.printf("\"%s\" 등록\n", p);
		}

		System.out.println("완료");
	}
}
