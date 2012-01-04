package com.setvect.bokslmusic.db.common;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.config.BokslMusicConstant;
import com.setvect.bokslmusic.db.MusicDao;
import com.setvect.bokslmusic.service.music.AlbumSearch;
import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.service.music.PlayItemSearch;
import com.setvect.bokslmusic.service.music.PlayTimeSearch;
import com.setvect.bokslmusic.service.music.MusicArticleSearch.Order;
import com.setvect.bokslmusic.service.music.MusicArticleSearch.UnionCondition;
import com.setvect.bokslmusic.vo.music.Album;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.bokslmusic.vo.music.PlayItem;
import com.setvect.bokslmusic.vo.music.PlayTime;
import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;

/**
 * 이슈관리 관리 DAO
 * 
 * @version $Id$
 */
public abstract class AbstractMusicDao implements MusicDao {
	// ------ 음악 경로 관리
	@Autowired
	private SessionFactory sessionFactory;

	public MusicDirectory getMusicDirectory(String basePath) {
		Session session = sessionFactory.getCurrentSession();
		return (MusicDirectory) session.get(MusicDirectory.class, basePath);
	}

	public List<MusicDirectory> getMusicDirectory() {
		Session session = sessionFactory.getCurrentSession();
		String q = " from MusicDirectory order by basePath ";
		Query query = session.createQuery(q);

		@SuppressWarnings("unchecked")
		List<MusicDirectory> resultList = query.list();

		return resultList;
	}

	public void createMusicDirectory(MusicDirectory item) {
		Session session = sessionFactory.getCurrentSession();
		session.save(item);
		session.flush();

	}

	public void updateMusicDirectory(MusicDirectory item) {
		Session session = sessionFactory.getCurrentSession();
		session.update(item);
		session.flush();
	}

	public void removeMusicDirectory(String basePath) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getMusicDirectory(basePath));
		session.flush();
	}

	// ------ 음악 목록 관리
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.setvect.bokslmusic.db.MusicDao#getMusicArticle(int)
	 */
	public MusicArticle getMusicArticle(String musicArticleId) {
		Session session = sessionFactory.getCurrentSession();
		return (MusicArticle) session.get(MusicArticle.class, musicArticleId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.setvect.bokslmusic.db.MusicDao#getMusicArticlePagingList(com.setvect
	 * .bokslmusic.service.music. MusicArticleSearch)
	 */
	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition) {
		Session session = sessionFactory.getCurrentSession();
		String whereClause = getMusicArticleWhereClause(pageCondition);

		String q = "select count(*) from MusicArticle " + whereClause;
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		String order = orderClause(pageCondition);

		q = " from MusicArticle " + whereClause + order;
		query = session.createQuery(q);
		query.setFirstResult(pageCondition.getStartNumber());
		query.setMaxResults(pageCondition.getPagePerItemCount());

		@SuppressWarnings("unchecked")
		List<MusicArticle> resultList = query.list();

		GenericPage<MusicArticle> resultPage = new GenericPage<MusicArticle>(resultList,
				pageCondition.getCurrentPage(), totalCount, pageCondition.getPageUnit(),
				pageCondition.getPagePerItemCount());
		return resultPage;
	}

	/**
	 * @param pageCondition
	 * @return 정렬 조건
	 */
	private String orderClause(MusicArticleSearch pageCondition) {
		String order = " order by name ";
		if (pageCondition.getOrder() == Order.FILE_NAME) {
			order = " order by name ";
		}
		else if (pageCondition.getOrder() == Order.RUNNING_TIME) {
			order = " order by runningTime ";
		}
		else if (pageCondition.getOrder() == Order.TITLE) {
			order = " order by titleExt ";
		}
		return order;
	}

	public List<String> getMusicArticlePath() {
		Session session = sessionFactory.getCurrentSession();
		String q = "SELECT DISTINCT path  from MusicArticle order by path";
		Query query = session.createQuery(q);

		query = session.createQuery(q);

		@SuppressWarnings("unchecked")
		List<String> resultList = query.list();

		return resultList;
	}

	/**
	 * @param pageCondition
	 * @return
	 */
	private String getMusicArticleWhereClause(MusicArticleSearch pageCondition) {
		String artist = pageCondition.getSearchArtist();
		String fileName = pageCondition.getSearchFileName();
		String lyrics = pageCondition.getSearchLyrics();
		String title = pageCondition.getSearchTitle();
		String path = pageCondition.getSearchPath();
		String pathParent = pageCondition.getSearchPathParent();

		String where;
		UnionCondition cnd = pageCondition.getUnionCondition();

		if (cnd == UnionCondition.AND) {
			where = " where 1 = 1 ";
		}
		else {
			where = " where 1 = 0 ";
		}
		if (StringUtilAd.isNotEmpty(artist)) {
			where += cnd + " ( artistTag like " + StringUtilAd.getSqlStringLike(artist) + " OR artistExt like "
					+ StringUtilAd.getSqlStringLike(artist) + ") ";
		}

		if (StringUtilAd.isNotEmpty(fileName)) {
			where += cnd + " name like " + StringUtilAd.getSqlStringLike(fileName) + " ";
		}

		if (StringUtilAd.isNotEmpty(lyrics)) {
			where += cnd + " lyrics like " + StringUtilAd.getSqlStringLike(lyrics) + " ";
		}

		if (StringUtilAd.isNotEmpty(title)) {
			where += cnd + " ( titleTag like " + StringUtilAd.getSqlStringLike(title) + " OR titleExt like "
					+ StringUtilAd.getSqlStringLike(title) + ") ";
		}

		if (StringUtilAd.isNotEmpty(path)) {
			where += cnd + " path =" + StringUtilAd.getSqlString(path) + " ";
		}

		if (StringUtilAd.isNotEmpty(pathParent)) {
			where += cnd + " path like " + StringUtilAd.getSqlStringLikeRight(pathParent.replace("\\", "\\\\")) + " ";
		}
		return where;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.setvect.bokslmusic.db.MusicDao#createMusicArticle(com.setvect.bokslmusic
	 * .vo.music.MusicArticle)
	 */
	public void createMusicArticle(MusicArticle item) {
		Session session = sessionFactory.getCurrentSession();
		session.save(item);
		session.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.setvect.bokslmusic.db.MusicDao#updateMusicArticle(com.setvect.bokslmusic
	 * .vo.music.MusicArticle)
	 */
	public void updateMusicArticle(MusicArticle item) {
		Session session = sessionFactory.getCurrentSession();
		session.update(item);
		session.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.setvect.bokslmusic.db.MusicDao#removeMusicArticle(int)
	 */
	public void removeMusicArticle(String musicArticleId) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getMusicArticle(musicArticleId));
		session.flush();
	}

	// ------ 앨범 정보
	public Album getAlbum(int albumSeq) {
		Session session = sessionFactory.getCurrentSession();
		return (Album) session.get(Album.class, albumSeq);
	}

	public GenericPage<Album> getAlbumList(AlbumSearch pageCondition) {
		Session session = sessionFactory.getCurrentSession();
		String whereClause = getAlbumWhereClause(pageCondition);

		String q = "select count(*) from Album " + whereClause;
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from Album " + whereClause + " order by name";
		query = session.createQuery(q);
		query.setFirstResult(pageCondition.getStartNumber());
		query.setMaxResults(pageCondition.getPagePerItemCount());

		@SuppressWarnings("unchecked")
		List<Album> resultList = query.list();

		GenericPage<Album> resultPage = new GenericPage<Album>(resultList, pageCondition.getCurrentPage(), totalCount,
				pageCondition.getPageUnit(), pageCondition.getPagePerItemCount());
		return resultPage;
	}

	private String getAlbumWhereClause(AlbumSearch pageCondition) {
		// 임시저장 앨범은 목록에서 조회 하지 않음
		String where = " where albumSeq <>  " + BokslMusicConstant.ALBUM_TEMP;
		String name = pageCondition.getSearchName();
		if (StringUtilAd.isNotEmpty(name)) {
			where += " and name = " + StringUtilAd.getSqlStringLike(name);
		}
		return where;
	}

	public void createAlbum(Album item) {
		Session session = sessionFactory.getCurrentSession();
		session.save(item);
		session.flush();
	}

	public void updateAlbum(Album item) {
		Session session = sessionFactory.getCurrentSession();
		session.update(item);
		session.flush();
	}

	public void removeAlbum(int albumSeq) {
		Session session = sessionFactory.getCurrentSession();
		String q = "delete  from PlayItem where albumSeq = ? ";
		Query query = session.createQuery(q);
		query.setParameter(0, albumSeq);
		query.executeUpdate();

		session.delete(getAlbum(albumSeq));
		session.flush();
	}

	// ------ Play Item 정보
	public PlayItem getPlayItem(int playItemSeq) {
		Session session = sessionFactory.getCurrentSession();
		return (PlayItem) session.get(PlayItem.class, playItemSeq);
	}

	public GenericPage<PlayItem> getPlayItemList(PlayItemSearch pageCondition) {
		Session session = sessionFactory.getCurrentSession();
		String whereClause = getPlayItemWhereClause(pageCondition);

		String q = "select count(*) from PlayItem " + whereClause;
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from PlayItem " + whereClause + " order by orderNo";
		query = session.createQuery(q);
		query.setFirstResult(pageCondition.getStartNumber());
		query.setMaxResults(pageCondition.getPagePerItemCount());

		@SuppressWarnings("unchecked")
		List<PlayItem> resultList = query.list();

		GenericPage<PlayItem> resultPage = new GenericPage<PlayItem>(resultList, pageCondition.getCurrentPage(),
				totalCount, pageCondition.getPageUnit(), pageCondition.getPagePerItemCount());
		return resultPage;
	}

	private String getPlayItemWhereClause(PlayItemSearch pageCondition) {
		String where = " where 1 = 1 ";
		int album = pageCondition.getSearchAlbumSeq();
		if (album != 0) {
			where += "and albumSeq = " + album;
		}
		return where;
	}

	public int getAlbumMaxOrderNo(int albumSeq) {
		Session session = sessionFactory.getCurrentSession();
		String q = "select COALESCE(max(orderNo), 0) from PlayItem where albumSeq = ? ";
		Query query = session.createQuery(q);
		query.setParameter(0, albumSeq);
		int orderNo = ((Integer) query.uniqueResult()).intValue();
		return orderNo;
	}

	public boolean isAlbumExistMusic(int albumSeq, String musicId) {
		Session session = sessionFactory.getCurrentSession();
		String q = "select count(*) from PlayItem where albumSeq = ? and musicId =?";
		Query query = session.createQuery(q);
		query.setParameter(0, albumSeq);
		query.setParameter(1, musicId);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		return totalCount >= 1;
	}

	public void createPlayItem(PlayItem item) {
		Session session = sessionFactory.getCurrentSession();
		session.save(item);
		session.flush();
	}

	public void updatePlayItem(PlayItem item) {
		Session session = sessionFactory.getCurrentSession();
		session.update(item);
		session.flush();
	}

	public void removePlayItem(int playItemSeq) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getPlayItem(playItemSeq));
		session.flush();
	}

	public void removePlayItemForAlbumSeq(int albumSeq) {
		Session session = sessionFactory.getCurrentSession();
		String q = "delete  from PlayItem where albumSeq = ? ";
		Query query = session.createQuery(q);
		query.setParameter(0, albumSeq);
		query.executeUpdate();
	}

	public void removePlayItem(int albumSeq, String musicId) {
		Session session = sessionFactory.getCurrentSession();
		String q = "delete  from PlayItem where albumSeq = ? and musicId =?";
		Query query = session.createQuery(q);
		query.setParameter(0, albumSeq);
		query.setParameter(1, musicId);
		query.executeUpdate();
	}

	// ------ Play Time 정보
	public PlayTime getPlayTime(int playTimeSeq) {
		Session session = sessionFactory.getCurrentSession();
		return (PlayTime) session.get(PlayTime.class, playTimeSeq);
	}

	public GenericPage<PlayTime> getPlayTimeList(PlayTimeSearch pageCondition) {
		Session session = sessionFactory.getCurrentSession();
		String whereClause = getPlayTimeWhereClause(pageCondition);

		String q = "select count(*) from PlayTime " + whereClause;
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from PlayTime " + whereClause + " order playTimeSeq";
		query = session.createQuery(q);
		query.setFirstResult(pageCondition.getStartNumber());
		query.setMaxResults(pageCondition.getPagePerItemCount());

		@SuppressWarnings("unchecked")
		List<PlayTime> resultList = query.list();

		GenericPage<PlayTime> resultPage = new GenericPage<PlayTime>(resultList, pageCondition.getCurrentPage(),
				totalCount, pageCondition.getPageUnit(), pageCondition.getPagePerItemCount());
		return resultPage;
	}

	private String getPlayTimeWhereClause(PlayTimeSearch pageCondition) {
		String where = " where 1 = 1 ";
		// 아직은 특별히 검색 조건을 넣을게 없음
		return where;
	}

	public void createPlayTime(PlayTime item) {
		Session session = sessionFactory.getCurrentSession();
		session.save(item);
		session.flush();
	}

	public void updatePlayTime(PlayTime item) {
		Session session = sessionFactory.getCurrentSession();
		session.update(item);
		session.flush();
	}

	public void removePlayTime(int playTimeSeq) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getPlayTime(playTimeSeq));
		session.flush();
	}

}
