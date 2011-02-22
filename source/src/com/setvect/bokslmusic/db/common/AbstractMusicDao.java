package com.setvect.bokslmusic.db.common;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.db.MusicDao;
import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.common.util.GenericPage;

/**
 * 捞酱包府 包府 DAO
 * 
 * @version $Id$
 */
public abstract class AbstractMusicDao implements MusicDao {
	// ------ 澜厩 版肺 包府
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

	// ------ 澜厩 格废 包府
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

		q = " from MusicArticle " + whereClause + " order by name ";
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

	private String getMusicArticleWhereClause(MusicArticleSearch pageCondition) {
		// TODO Auto-generated method stub
		return null;
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
