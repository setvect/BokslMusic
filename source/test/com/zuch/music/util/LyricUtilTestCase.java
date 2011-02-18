package com.zuch.music.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class LyricUtilTestCase {
	@Test
	public void test����_�ҷ�����() {
		File file = new File("sample_data/a.mp3");
		LyricGetter lyric = new LyricGetter(file);
		Assert.assertThat(lyric.getMd5(), is("ce7681520effc58d30dd1cc3beb3d5f9"));
		Assert.assertThat(lyric.getTitle(), is("��������"));
		Assert.assertThat(lyric.getArtist(), is("�̻���"));
		Assert.assertThat(lyric.getLyric(), notNullValue());
	}
}
