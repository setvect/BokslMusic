package com.setvect.bokslmusic.extract;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.setvect.bokslmusic.TestSystem;

public class MusicExtractorTestCase extends TestSystem {
	@Test
	public void test() {
		File baseDir = new File("sample_data");
		List<MusicMetadata> musicMetes = MusicExtractor.listForDir(baseDir);
		Assert.assertThat(musicMetes.size(), is(1));

		MusicMetadata musicMetadata = musicMetes.get(0);
		File sf = musicMetadata.getSourceFile();
		Assert.assertThat(sf.getName(), is("a.mp3"));

		String title = musicMetadata.getExtInfor().getTitle();
		Assert.assertThat(title, is("¾ðÁ¨°¡´Â"));

	}
}
