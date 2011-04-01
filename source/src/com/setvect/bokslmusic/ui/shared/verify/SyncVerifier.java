package com.setvect.bokslmusic.ui.shared.verify;

/**
 * 동기화 관련된 vaildation check
 */
public class SyncVerifier {

	/**
	 * @param dir
	 *            동기화되는 디렉토리
	 * @return 맞는 입력 값이면 true, 아니면 false
	 */
	public static boolean isVaildSyncDir(String dir) {
		if (dir == null) {
			return false;
		}
		return dir.trim().length() != 0;
	}
}
