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
	public void test() throws NoSuchAlgorithmException, IOException {
		File file = new File("sample_data/a.mp3");
		String md5Str = Md5Util.getMD5Checksum(file);
		Assert.assertThat(md5Str, is("d66bb7b17f31ef54ae3f82efde839bc7"));

		file = new File("sample_data/notfound.aaaa");
		md5Str = Md5Util.getMD5Checksum(file);
		Assert.assertThat(md5Str, nullValue());
	}
}
