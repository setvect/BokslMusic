package com.setvect.bokslmusic.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.boot.EnvirmentInit;
import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.common.util.GenericPage;

/**
 * 프로젝트 목록 제공
 * 
 * @version $Id$
 */
@Service
public class MusicDwrService {

	/**
	 * 음악 파일 목록을 조회 함
	 * 
	 * @param folder
	 *            폴더명
	 * @return 음악 파일 목록
	 */
	public List<MusicArticle> getMusicArticle(String folder) {

		// // TODO 스프링 시작시점에서 Bind가 되지 않는다.
		MusicService musicService = (MusicService) EnvirmentInit.getConfigSpring().getBean("MusicService");

		// 검색조건: 디렉토리 이름 매칭
		MusicArticleSearch pageCondition = new MusicArticleSearch(1);
		pageCondition.setPagePerItemCount(Integer.MAX_VALUE);
		pageCondition.setSearchPath(folder);

		GenericPage<MusicArticle> pageSearch = musicService.getMusicArticlePagingList(pageCondition);
		Collection<MusicArticle> temp = pageSearch.getList();
		List<MusicArticle> result = new ArrayList<MusicArticle>();
		result.addAll(temp);
		return result;
	}
}
