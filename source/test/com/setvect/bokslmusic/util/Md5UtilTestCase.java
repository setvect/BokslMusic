package com.setvect.bokslmusic.util;

import java.io.File;

import org.junit.Test;

public class Md5UtilTestCase {
	@Test
	public void testMd5Check() {
		File file = new File("C:\\j2sdk1.4.2_19\\src.zip");
		Md5Util md5 = new Md5Util(file, 7888);
		String md5Str = md5.getCheckSum();

		System.out.println(md5Str);
	}
}
