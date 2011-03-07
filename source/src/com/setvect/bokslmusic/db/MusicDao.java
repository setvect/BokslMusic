package com.setvect.bokslmusic.db;

import java.util.List;

import com.setvect.bokslmusic.service.music.AlbumSearch;
import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.service.music.PlayItemSearch;
import com.setvect.bokslmusic.service.music.PlayTimeSearch;
import com.setvect.bokslmusic.vo.music.Album;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.bokslmusic.vo.music.PlayItem;
import com.setvect.bokslmusic.vo.music.PlayTime;
import com.setvect.common.util.GenericPage;

/**
 * 음악 관리 DAO
 * 
 * @version $Id$
 */
public interface MusicDao {
	// ------ 음악 경로 관리
	/**
	 * @param basePath
	 *            경로(키)
	 * @return 음원 저장 경로
	 */
	public MusicDirectory getMusicPath(String basePath);

	/**
	 * @return 등록된 모든 음원 저장 경로
	 */
	public List<MusicDirectory> getMusicPathList();

	/**
	 * 등록
	 * 
	 * @param item
	 *            음원 저장 경로
	 */
	public void createMusicPath(MusicDirectory item);

	/**
	 * 수정
	 * 
	 * @param item
	 *            음원 저장 경로
	 */
	public void updateMusicPath(MusicDirectory item);

	/**
	 * 삭제
	 * 
	 * @param basePath
	 *            음원 경로(키)
	 */
	public void removeMusicPath(String basePath);

	// ------ 음악 목록 관리
	/**
	 * @param musicArticleSeq
	 * @return 음악정보
	 */
	public MusicArticle getMusicArticle(String musicArticleId);

	/**
	 * @param pageCondition
	 * @return 음악정보
	 */
	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition);

	/**
	 * @return 중복제거된 파일 저장 경로
	 */
	public List<String> getMusicArticlePath();

	/**
	 * 음악 등록
	 * 
	 * @param item
	 */
	public void createMusicArticle(MusicArticle item);

	/**
	 * 음악 수정
	 * 
	 * @param item
	 */
	public void updateMusicArticle(MusicArticle item);

	/**
	 * 음악 삭제
	 * 
	 * @param musicArticleId
	 */
	public void removeMusicArticle(String musicArticleId);

	// ------ 앨범 정보
	/**
	 * @param albumSeq
	 *            앨범 일련번호
	 * @return 앨범 정보
	 */
	public Album getAlbum(int albumSeq);

	/**
	 * @param pageCondition
	 *            검색 조건
	 * @return 앨범 정보 목록
	 */
	public GenericPage<Album> getAlbumList(AlbumSearch pageCondition);

	/**
	 * 등록
	 * 
	 * @param item
	 *            앨범 정보
	 */
	public void createAlbum(Album item);

	/**
	 * 수정
	 * 
	 * @param item
	 *            앨범 정보
	 */
	public void updateAlbum(Album item);

	/**
	 * 삭제
	 * 
	 * @param albumSeq
	 *            앨범 일련번호
	 */
	public void removeAlbum(int albumSeq);

	// ------ Play Item 정보
	/**
	 * @param playItemSeq
	 *            재생 파일 일련번호
	 * @return 앨범 정보
	 */
	public PlayItem getPlayItem(int playItemSeq);

	/**
	 * @param pageCondition
	 *            검색 조건
	 * @return 앨범 정보 목록
	 */
	public GenericPage<PlayItem> getPlayItemList(PlayItemSearch pageCondition);

	/**
	 * 등록
	 * 
	 * @param item
	 *            재생 파일
	 */
	public void createPlayItem(PlayItem item);

	/**
	 * 수정
	 * 
	 * @param item
	 *            재생 파일
	 */
	public void updatePlayItem(PlayItem item);

	/**
	 * 삭제
	 * 
	 * @param playItemSeq
	 *            재생 파일 일련번호
	 */
	public void removePlayItem(int playItemSeq);

	// ------ Play Time 정보

	/**
	 * @param playTimeSeq
	 * 
	 * @return 재생 시간 정보
	 */
	public PlayTime getPlayTime(int playTimeSeq);

	/**
	 * @param pageCondition
	 *            검색 조건
	 * @return 앨범 정보 목록
	 */
	public GenericPage<PlayTime> getPlayTimeList(PlayTimeSearch pageCondition);

	/**
	 * 등록
	 * 
	 * @param item
	 *            재생 파일
	 */
	public void createPlayTime(PlayTime item);

	/**
	 * 수정
	 * 
	 * @param item
	 *            재생 파일
	 */
	public void updatePlayTime(PlayTime item);

	/**
	 * 삭제
	 * 
	 * @param playTimeSeq
	 *            재생 파일 일련번호
	 */
	public void removePlayTime(int playTimeSeq);

}
