package com.setvect.bokslmusic.vo.music;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.setvect.bokslmusic.boot.EnvirmentInit;
import com.setvect.bokslmusic.config.BokslMusicConstant;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.common.util.GenericPage;

/**
 * 앨범 정보
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBAC_ALBUM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Album {
	/** 앨범일련번호 */
	@Id
	@Column(name = "ALBUM_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int albumSeq;

	/** 앨범 이름 */
	@Column(name = "NAME")
	private String name;

	@Transient
	private List<MusicArticle> musicArticleList;

	/**
	 * 앨범일련번호
	 * 
	 * @return 앨범일련번호
	 */
	public int getAlbumSeq() {
		return albumSeq;
	}

	/**
	 * 앨범일련번호
	 * 
	 * @param albumSeq
	 *            앨범일련번호
	 */
	public void setAlbumSeq(int albumSeq) {
		this.albumSeq = albumSeq;
	}

	/**
	 * 앨범 이름
	 * 
	 * @return 앨범 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * 앨범 이름
	 * 
	 * @param name
	 *            앨범 이름
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 삭제 가능 여부, 임시저장 용으로 사용된느 앨범은 삭제 하지 못함
	 * 
	 * @return 삭제 가능하면 true
	 */
	public boolean deletePassible() {
		return BokslMusicConstant.ALBUM_TEMP != albumSeq;
	}

	/**
	 * @return 앨범에 포함된 음악 목록
	 */
	public List<MusicArticle> getMusicArticleList() {
		if (musicArticleList != null) {
			return musicArticleList;
		}
		MusicService musicService = (MusicService) EnvirmentInit.getConfigSpring().getBean("MusicService");

		GenericPage<PlayItem> temp = musicService.getPlayItemList(albumSeq);
		Collection<PlayItem> list = temp.getList();

		musicArticleList = new ArrayList<MusicArticle>();
		for (PlayItem l : list) {
			musicArticleList.add(l.getMusicArticle());
		}
		return musicArticleList;
	}
}
