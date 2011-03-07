package com.setvect.bokslmusic.etc;

import org.junit.Test;

public class EtcTestCase {
	@Test
	public void testFormat() {
		System.out.println(String.format("%,d", 3223));

		String msg = String.format("총:%,d, 변화없음:%,d, 신규등록:%,d, 경로수정:%,d, 에러:%,d ", 13200, 100, 2220, 10, 2);
		System.out.println(msg);
	}
}
