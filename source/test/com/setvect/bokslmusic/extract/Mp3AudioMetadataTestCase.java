package com.setvect.bokslmusic.extract;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class Mp3AudioMetadataTestCase {

	@Test
	public void testMp3() {
		File source = new File("sample_data/a.mp3");
		Mp3AudioMetadata audio = new Mp3AudioMetadata(source);
		Assert.assertThat(audio.getSamplingRate(), is(44100));
		Assert.assertThat(audio.getBatRate(), is(192));
		Assert.assertThat(audio.getTitle(), is("02. 언젠가는 (Someday)"));
		Assert.assertThat(audio.getArtist(), is("이상은"));
		Assert.assertThat(audio.getYear(), is("1993"));
		Assert.assertThat(audio.getGenre(), is("Other"));
	}

	// @Test
	public void testOgg() {
		File source = new File("sample_data/a.ogg");
		MusicMetadata meta = new MusicMetadata(source);
		System.out.println("AAAAAAAAAAAAAAAAAAAAA" + meta.getAlSongMetadata().getLyric());
		Assert.assertThat(meta.getAudioMetadata().getSamplingRate(), is(44100));
		Assert.assertThat(meta.getAudioMetadata().getBatRate(), is(499));
	}

	// @Test
	public void testFlac() {
		File source = new File("sample_data/a.flac");
		MusicMetadata meta = new MusicMetadata(source);
		System.out.println("AAAAAAAAAAAAAAAAAAAAA" + meta.getAlSongMetadata().getLyric());
		Assert.assertThat(meta.getAudioMetadata().getSamplingRate(), is(44100));
		Assert.assertThat(meta.getAudioMetadata().getBatRate(), is(804));
	}
}
