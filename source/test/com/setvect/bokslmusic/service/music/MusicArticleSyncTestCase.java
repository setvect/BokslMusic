package com.setvect.bokslmusic.service.music;

import java.io.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.TestSystem;

/**
 * DB와 실 저장된 자료간의 동기화
 * 
 * @version $Id$
 */
public class MusicArticleSyncTestCase extends TestSystem {
	@Autowired
	private MusicSyncService service;

	// @Test
	public void testSyncDirectory() {
		File baseDir = new File("D:\\90.멀티미디어");
		service.syncDirectory(baseDir);
	}

	@Test
	public void testSyncDb() {
		service.syncDb();
	}
}
