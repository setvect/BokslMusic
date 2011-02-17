package com.zuch.music.util;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class LyricUtilTestCase {
	@Test
	public void test가사_불러오기() {
		File file = new File("sample_data/youlightup.mp3");
		String md5Str = Md5Util.getMD5Checksum(file, 0, 163840);
		String s = LyricUtil.downloadLyric(md5Str);
		Assert.assertThat(s, is("you light up my life"));

		System.out.println(s);
	}
}
