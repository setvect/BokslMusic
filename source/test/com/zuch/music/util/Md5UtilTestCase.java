package com.zuch.music.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;

import com.zuch.music.util.Md5Util;

public class Md5UtilTestCase {

	@Test
	public void testMd5Check() throws NoSuchAlgorithmException, IOException {
		File file = new File("sample_data/youlightup.mp3");
		String md5Str = Md5Util.getMD5Checksum(file);

		Assert.assertThat(md5Str, is("81721b89ce1f183b38ea04201c6821fa"));

		md5Str = Md5Util.getMD5Checksum(file, 0, 163840);
		Assert.assertThat(md5Str, is("3f0511a0c3eef50b898a4ed132cbd8d3"));

		file = new File("sample_data/notfound.aaaa");
		md5Str = Md5Util.getMD5Checksum(file);
		Assert.assertThat(md5Str, nullValue());
	}
}
