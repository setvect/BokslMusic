package com.setvect.bokslmusic.db.common;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.db.MusicDao;
import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicPath;
import com.setvect.common.util.GenericPage;

/**
 * 捞酱包府 包府 DAO
 * 
 * @version $Id$
 */
public abstract class AbstractMusicDao implements MusicDao {
	@Autowired
	private SessionFactory sessionFactory;

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

	public MusicPath getMusicPath(String basePath) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MusicPath> getMusicPathList() {
		// TODO Auto-generated method stub
		return null;
	}

	public void createMusicPath(MusicPath item) {
		// TODO Auto-generated method stub

	}

	public void updateMusicPath(MusicPath item) {
		// TODO Auto-generated method stub

	}

	public void removeMusicPath(String basePath) {
		// TODO Auto-generated method stub

	}
}
