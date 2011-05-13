package com.setvect.bokslmusic.ui.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

/**
 * 음악 파일 경로 동기화
 * 
 * @see MusicService
 */
@RemoteServiceRelativePath("service/SyncService")
public interface SyncService extends RemoteService {
	/**
	 * @return 동기화할 경로
	 * @throws IllegalArgumentException
	 */
	List<MusicDirectoryModel> getSyncList() throws IllegalArgumentException;

	/**
	 * @param dir
	 *            등록할 경로
	 * @return 성공 여부
	 */
	boolean addSyncPath(String dir);

	/**
	 * @param dir
	 *            삭제할 경로
	 * @return 성공 여부
	 */
	boolean removeMusicPath(String dir);

	/**
	 * @param dir
	 *            동기화 할 경로
	 * @return 성공 여부
	 */
	boolean syncDirectory(String dir);

	/**
	 * @return 동기화 로그
	 */
	String getSyncLog();
}
