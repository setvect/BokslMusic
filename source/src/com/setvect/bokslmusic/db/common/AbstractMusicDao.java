package com.setvect.bokslmusic.db.common;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.db.MusicDao;
import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.service.music.MusicArticleSearch.Order;
import com.setvect.bokslmusic.service.music.MusicArticleSearch.UnionCondition;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
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

	public MusicDirectory getMusicPath(String basePath) {
		Session session = sessionFactory.getCurrentSession();
		return (MusicDirectory) session.get(MusicDirectory.class, basePath);
	}

	public List<MusicDirectory> getMusicPathList() {
		Session session = sessionFactory.getCurrentSession();
		String q = " from MusicDirectory order by basePath ";
		Query query = session.createQuery(q);

		@SuppressWarnings("unchecked")
		List<MusicDirectory> resultList = query.list();

		return resultList;
	}

	public void createMusicPath(MusicDirectory item) {
		Session session = sessionFactory.getCurrentSession();
		session.save(item);
		session.flush();

	}

	public void updateMusicPath(MusicDirectory item) {
		Session session = sessionFactory.getCurrentSession();
		session.update(item);
		session.flush();
	}

	public void removeMusicPath(String basePath) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getMusicPath(basePath));
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

	/**
	 * @param pageCondition
	 * @return
	 */
	private String getMusicArticleWhereClause(MusicArticleSearch pageCondition) {
		String artist = pageCondition.getSearchArtist();
		String fileName = pageCondition.getSearchFileName();
		String lyrics = pageCondition.getSearchLyrics();
		String title = pageCondition.getSearchTitle();

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
			where += cnd + " name like " + StringUtilAd.getSqlStringLike(fileName);
		}

		if (StringUtilAd.isNotEmpty(lyrics)) {
			where += cnd + " lyrics like " + StringUtilAd.getSqlStringLike(lyrics);
		}

		if (StringUtilAd.isNotEmpty(title)) {
			where += cnd + " ( titleTag like " + StringUtilAd.getSqlStringLike(title) + " OR titleExt like "
					+ StringUtilAd.getSqlStringLike(title) + ") ";
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
}
