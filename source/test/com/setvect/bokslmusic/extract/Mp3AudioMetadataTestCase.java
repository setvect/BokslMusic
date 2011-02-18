package com.setvect.bokslmusic.extract;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class Mp3AudioMetadataTestCase {
	@Test
	public void test() {
		File source = new File("sample_data/a.mp3");
		AudioMetadata audio = new Mp3AudioMetadata(source);
		Assert.assertThat(audio.getSamplingRate(), is(44100));
		Assert.assertThat(audio.getBatRate(), is(192));
		Assert.assertThat(audio.getTitle(), is("[6] 언젠가는"));
		Assert.assertThat(audio.getArtist(), is("이상은"));
		Assert.assertThat(audio.getYear(), is("1993"));
	}
}
