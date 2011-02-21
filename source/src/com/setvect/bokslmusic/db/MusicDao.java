package com.setvect.bokslmusic.db;

import java.util.List;

import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicPath;
import com.setvect.common.util.GenericPage;

/**
 * ���� ���� DAO
 * 
 * @version $Id$
 */
public interface MusicDao {
	// ------ ���� ��� ����
	public MusicPath getMusicPath(String basePath);

	public List<MusicPath> getMusicPathList();

	public void createMusicPath(MusicPath item);

	public void updateMusicPath(MusicPath item);

	public void removeMusicPath(String basePath);

	// ------ ���� ��� ����
	/**
	 * @param musicArticleSeq
	 * @return ��������
	 */
	public MusicArticle getMusicArticle(String musicArticleId);

	/**
	 * @param pageCondition
	 * @return ��������
	 */
	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition);

	/**
	 * ���� ���
	 * 
	 * @param item
	 */
	public void createMusicArticle(MusicArticle item);

	/**
	 * ���� ����
	 * 
	 * @param item
	 */
	public void updateMusicArticle(MusicArticle item);

	/**
	 * ���� ����
	 * 
	 * @param musicArticleId
	 */
	public void removeMusicArticle(String musicArticleId);
}
