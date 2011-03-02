package com.setvect.bokslmusic.service.music;

import java.io.File;
import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.TestSystem;
import com.setvect.bokslmusic.vo.music.MusicArticle;

/**
 * DB�� �� ����� �ڷᰣ�� ����ȭ
 * 
 * @version $Id$
 */
public class MusicArticleSyncTestCase extends TestSystem {
	@Autowired
	private MusicSyncService serviceSync;

	@Autowired
	private MusicService service;

	@Test
	public void testSyncDirectory() {
		File baseDir = new File("E:\\10.��Ƽ�̵��\\03.����__ZZZ");
		serviceSync.syncDirectory(baseDir);
	}

	// @Test
	public void testDirPath() {
		Collection<MusicArticle> list = service.getMusicArticleAllList();
		for (MusicArticle art : list) {
			System.out.println(art.getFile());
		}
	}

	// @Test
	public void testSyncDb() {
		serviceSync.syncDb();
	}
}
