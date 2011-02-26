package com.setvect.bokslmusic.service.music;

import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.TestSystem;
import com.setvect.bokslmusic.vo.music.Album;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.bokslmusic.vo.music.PlayItem;
import com.setvect.bokslmusic.vo.music.PlayTime;

public class MusicArticleServiceTestCase extends TestSystem {
	@Autowired
	private MusicService service;

	@Test
	public void testPath() {
		MusicDirectory saveItem = new MusicDirectory();
		saveItem.setBasePath("D:\\90.¸ÖÆ¼¹Ìµð¾î\\01_À½¾Ç");
		saveItem.setSyncDate(new Date());
		service.createMusicPath(saveItem);

		MusicDirectory loadItem = service.getMusicPath(saveItem.getBasePath());
		Assert.assertThat(saveItem, is(loadItem));
	}

	@Test
	public void testPlayItem() {
		MusicArticle musicArticle = new MusicArticle();
		musicArticle.setMusicId("abcdef");
		musicArticle.setName("a.mp3");
		musicArticle.setPath("c:\\");
		musicArticle.setLyrics("¾È³ç");
		musicArticle.setFileSize(100);
		musicArticle.setSamplingRate(100);
		musicArticle.setBitRate(200);
		musicArticle.setRunningTime(100);

		service.createMusicArticle(musicArticle);

		MusicArticle musicArticleLoad = service.getMusicArticle(musicArticle.getMusicId());
		Assert.assertThat(musicArticle, is(musicArticleLoad));

		Album album = new Album();
		album.setName("¿ì¿ïÇÒ¶§ µè´Â À½¾Ç");
		service.createAlbum(album);
		Album albumLoad = service.getAlbum(album.getAlbumSeq());
		Assert.assertThat(album, is(albumLoad));

		PlayItem playItem = new PlayItem();
		playItem.setAlbumSeq(album.getAlbumSeq());
		playItem.setMusicId(musicArticle.getMusicId());
		playItem.setOrderNo(1);

		service.createPlayItem(playItem);
		PlayItem playItemLoad = service.getPlayItem(playItem.getPlayItemSeq());
		Assert.assertThat(playItem, is(playItemLoad));

		PlayTime playTime = new PlayTime();
		playTime.setMusicId(musicArticle.getMusicId());
		playTime.setPlayDate(new Date());

		service.createPlayTime(playTime);
		PlayTime playTimeLoad = service.getPlayTime(playTime.getPlayTimeSeq());
		Assert.assertThat(playTime, is(playTimeLoad));
	}
}
