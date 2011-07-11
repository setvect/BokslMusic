package com.setvect.bokslmusic.ui.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;
import com.setvect.bokslmusic.ui.shared.model.AlbumModel;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;
import com.setvect.bokslmusic.ui.shared.model.PlayArticleModel;

/**
 * 앨범 관리
 * 
 * @see MusicService
 */
@RemoteServiceRelativePath("service/MusicManagerService")
public interface MusicManagerService extends RemoteService {
	// ------------------------- 앨범관련
	/**
	 * 앨범 정보 및 앨범 수록 항목 정보
	 * 
	 * @param model
	 * @return
	 */
	public List<AlbumArticleModel> listAlbum(AlbumModel model);

	/**
	 * 앨범 등록
	 * 
	 * @param albumName
	 *            앨범 이름
	 * @param asyncCallback
	 */
	public void addAlbum(String albumName);

	/**
	 * @param albumSeq
	 *            앨범 번호
	 * @param musicId
	 *            음악 아이디
	 */
	public void addMusicForAlbum(int albumSeq, List<String> musicId);

	/**
	 * @param articleList
	 *            삭제 대상 목록
	 */
	public void removeAlbumArticle(List<AlbumArticleModel> articleList);

	// ------------------------- 음악 목록 관련

	/**
	 * @return 등록된 음악 전체 목록
	 */
	public List<MusicArticleModel> listMusicArticleAll();

	/**
	 * @param directory
	 *            디렉토리
	 * @return 해당 경로 하위 디렉토리 및 파일 목록
	 */
	public List<MusicArticleModel> listMusic(MusicDirectoryModel directory);

	/**
	 * @param id
	 *            음악 아이디
	 * @return 음악 정보
	 */
	public PlayArticleModel getPlayArticle(String id);
}
