package com.setvect.bokslmusic.util;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import junit.framework.Assert;

import org.junit.Test;

import com.zuch.music.util.Md5Util;

public class Md5UtilTestCase {
	@Test
	public void testMd5Check() throws NoSuchAlgorithmException, IOException {
		File file = new File("sample_data/youlightup.mp3");
		String md5Str = Md5Util.getMD5Checksum(file);
		Assert.assertEquals("9262eb75ded5a946a1115b797f0a6355", md5Str);

		file = new File("sample_data/notfound.aaaa");
		md5Str = Md5Util.getMD5Checksum(file);
		Assert.assertNull(md5Str);
	}
}
