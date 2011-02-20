package com.setvect.bokslmusic.extract;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.service.music.MusicArticleService;

public class Mp3AudioMetadataTestCase {

	@Test
	public void test() {
		File source = new File("sample_data/a.mp3");
		AudioMetadata audio = new Mp3AudioMetadata(source);
		Assert.assertThat(audio.getSamplingRate(), is(44100));
		Assert.assertThat(audio.getBatRate(), is(192));
		Assert.assertThat(audio.getTitle(), is("02. 언젠가는 (Someday)"));
		Assert.assertThat(audio.getArtist(), is("이상은"));
		Assert.assertThat(audio.getYear(), is("1993"));
		Assert.assertThat(audio.getGenre(), is("Other"));
	}
}
