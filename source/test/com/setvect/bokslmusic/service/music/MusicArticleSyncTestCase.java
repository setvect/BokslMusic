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
	private MusicService service;

	@Test
	public void testArticle() {
		File baseDir = new File("D:\\90.��Ƽ�̵��");
		service.syncDiretory(baseDir);
	}
}
