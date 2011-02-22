package com.setvect.bokslmusic.service.music;

import java.io.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.TestSystem;

/**
 * DB�� �� ����� �ڷᰣ�� ����ȭ
 * 
 * @version $Id$
 */
public class MusicArticleSyncTestCase extends TestSystem {
	@Autowired
	private MusicSyncService service;

	// @Test
	public void testSyncDirectory() {
		File baseDir = new File("D:\\90.��Ƽ�̵��");
		service.syncDirectory(baseDir);
	}

	@Test
	public void testSyncDb() {
		service.syncDb();
	}
}
