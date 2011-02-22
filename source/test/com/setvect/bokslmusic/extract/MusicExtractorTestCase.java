package com.setvect.bokslmusic.extract;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.TestSystem;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.common.log.LogPrinter;

public class MusicExtractorTestCase extends TestSystem {
	@Autowired
	private MusicService musicArticleService;

	// @Test
	public void testSimple() {
		File baseDir = new File("sample_data");
		List<MusicMetadata> musicMetes = MusicExtractor.listForDir(baseDir);
		Assert.assertThat(musicMetes.size(), is(1));

		MusicMetadata musicMetadata = musicMetes.get(0);
		File sf = musicMetadata.getSourceFile();
		Assert.assertThat(sf.getName(), is("a.mp3"));

		String title = musicMetadata.getAlSongMetadata().getTitle();
		Assert.assertThat(title, is("언젠가는"));

		MusicArticle marticle = musicMetadata.getMusicArticle();
		Assert.assertThat(marticle.getMusicId(), is("ce7681520effc58d30dd1cc3beb3d5f9"));
		Assert.assertThat(marticle.getFileSize(), is(6099615));
		Assert.assertThat(marticle.getTitleExt(), is("언젠가는"));
		Assert.assertThat(marticle.getTitleTag(), is("02. 언젠가는 (Someday)"));
		Assert.assertThat(marticle.getSamplingRate(), is(44100));
		Assert.assertThat(marticle.getBitRate(), is(192));
		musicArticleService.createMusicArticle(marticle);
	}

	@Test
	public void testPlural() {
		File baseDir = new File("D:\\90.멀티미디어");
		List<MusicMetadata> musicMetes = MusicExtractor.listForDir(baseDir);
		System.out.println(musicMetes.size());

		for (MusicMetadata music : musicMetes) {
			System.out.println(music.getSourceFile());
			MusicArticle marticle = null;
			try {
				marticle = music.getMusicArticle();
			} catch (Exception e) {
				LogPrinter.out.error("파일 변환 안됨: " + e.getMessage());
				continue;
			}
			System.out.println(marticle.toString());
			System.out.println("========================================");
		}
	}

}
