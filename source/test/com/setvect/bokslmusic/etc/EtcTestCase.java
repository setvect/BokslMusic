package com.setvect.bokslmusic.etc;

import org.junit.Test;

public class EtcTestCase {
	@Test
	public void testFormat() {
		System.out.println(String.format("%,d", 3223));

		String msg = String.format("��:%,d, ��ȭ����:%,d, �űԵ��:%,d, ��μ���:%,d, ����:%,d ", 13200, 100, 2220, 10, 2);
		System.out.println(msg);
	}
}
