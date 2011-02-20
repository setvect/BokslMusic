package com.setvect.bokslmusic.extract;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.TestSystem;
import com.setvect.bokslmusic.service.music.MusicArticleService;
import com.setvect.bokslmusic.vo.music.MusicArticle;

public class MusicExtractorTestCase extends TestSystem {
	@Autowired
	private MusicArticleService musicArticleService;

	@Test
	public void test() {
		File baseDir = new File("sample_data");
		List<MusicMetadata> musicMetes = MusicExtractor.listForDir(baseDir);
		Assert.assertThat(musicMetes.size(), is(1));

		MusicMetadata musicMetadata = musicMetes.get(0);
		File sf = musicMetadata.getSourceFile();
		Assert.assertThat(sf.getName(), is("a.mp3"));

		String title = musicMetadata.getAlSongMetadata().getTitle();
		Assert.assertThat(title, is("¾ðÁ¨°¡´Â"));

		MusicArticle marticle = musicMetadata.getMusicArticle();
		Assert.assertThat(marticle.getMusicId(), is("ce7681520effc58d30dd1cc3beb3d5f9"));
		Assert.assertThat(marticle.getFileSize(), is(6099615));
		Assert.assertThat(marticle.getTitleExt(), is("¾ðÁ¨°¡´Â"));
		Assert.assertThat(marticle.getTitleTag(), is("02. ¾ðÁ¨°¡´Â (Someday)"));
		Assert.assertThat(marticle.getSamplingRate(), is(44100));
		Assert.assertThat(marticle.getBitRate(), is(192));
		musicArticleService.createMusicArticle(marticle);
	}
}
