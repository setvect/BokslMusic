package com.setvect.bokslmusic.service.music;

import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.TestSystem;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;

public class MusicArticleServiceTestCase extends TestSystem {
	@Autowired
	private MusicService service;

	@Test
	public void testArticle() {
		MusicArticle saveItem = new MusicArticle();
		saveItem.setMusicId("abcdef");
		saveItem.setName("a.mp3");
		saveItem.setPath("c:\\");
		saveItem.setLyrics("¾È³ç");
		saveItem.setFileSize(100);
		saveItem.setSamplingRate(100);
		saveItem.setBitRate(200);
		saveItem.setRunningTime(100);

		service.createMusicArticle(saveItem);

		MusicArticle loadItem = service.getMusicArticle(saveItem.getMusicId());
		Assert.assertThat(saveItem, is(loadItem));
	}

	
	@Test
	public void testPath() {
		MusicDirectory saveItem = new MusicDirectory();
		saveItem.setBasePath("D:\\90.¸ÖÆ¼¹Ìµð¾î\\01_À½¾Ç");
		saveItem.setSyncDate(new Date());
		service.createMusicPath(saveItem);

		MusicDirectory loadItem = service.getMusicPath(saveItem.getBasePath());
		Assert.assertThat(saveItem, is(loadItem));
	}
}
