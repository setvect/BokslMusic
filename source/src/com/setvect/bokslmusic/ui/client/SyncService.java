package com.setvect.bokslmusic.ui.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

/**
 * 음악 파일 경로 동기화
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
	 * @return 등록 성공 여부
	 */
	boolean addSyncPath(String dir);
}
