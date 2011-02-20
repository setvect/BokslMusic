package com.setvect.bokslmusic.service.music;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.TestSystem;
import com.setvect.bokslmusic.vo.music.MusicArticle;

public class MusicArticleServiceTestCase extends TestSystem {
	@Autowired
	private MusicArticleService service;

	@Test
	public void test() {
		MusicArticle saveItem = new MusicArticle();
		saveItem.setMusicId("abcdef");
		saveItem.setName("a.mp3");
		saveItem.setPath("c:\\a.mp3");
		saveItem.setLyrics("¾È³ç");
		saveItem.setFileSize(100);
		saveItem.setSamplingRate(100);
		saveItem.setBitRate(200);
		saveItem.setRunningTime(100);

		service.createMusicArticle(saveItem);

		MusicArticle loadItem = service.getMusicArticle(saveItem.getMusicId());

		Assert.assertThat(saveItem, is(loadItem));

	}
}
