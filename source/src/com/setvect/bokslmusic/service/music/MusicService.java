package com.setvect.bokslmusic.service.music;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.db.MusicDao;
import com.setvect.bokslmusic.extract.MusicExtractor;
import com.setvect.bokslmusic.extract.MusicMetadata;
import com.setvect.bokslmusic.log.SyncLogPrinter;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.common.date.DateUtil;
import com.setvect.common.util.GenericPage;

@Service("MusicService")
public class MusicService {
	@Autowired
	private MusicDao musicArticleDao;

	/**
	 * 음악 파일과 저장된 DB와 싱크<br>
	 * DB 정보에는 수정/등록이 일어남.<br>
	 * 삭제 작업은 일어나지 않음
	 * 
	 * @param baseDir
	 *            싱크할 음악 파일이 있는 디렉토리
	 */
	public void syncDiretory(File baseDir) {
		SyncLogPrinter.log(DateUtil.getSysDateTime() + " 디렉토리 기준 싱크 시작: " + baseDir.getAbsolutePath());
		List<MusicMetadata> musicMetes = MusicExtractor.listForDir(baseDir);
		int modifyCount = 0;
		int newCount = 0;
		int errorCount = 0;
		int nothingCount = 0;

		for (MusicMetadata music : musicMetes) {
			File sourceFile = music.getSourceFile();
			String headerCode = music.getHeaderCode();
			MusicArticle art = getMusicArticle(headerCode);
			if (art != null) {
				boolean noChange = art.getPath().equals(sourceFile.getAbsoluteFile());

				if (noChange) {
					String msg = String.format("[통과] [%s] %s ", headerCode, art.getPath());
					SyncLogPrinter.log(msg);
					nothingCount++;
					continue;
				}

				updatePath(sourceFile, art);
				String msg = String.format("[변경] [%s] %s -> %s", headerCode, art.getPath(),
						sourceFile.getAbsoluteFile());
				SyncLogPrinter.log(msg);
				modifyCount++;
				continue;
			}

			MusicArticle marticle = null;
			try {
				marticle = music.getMusicArticle();
			} catch (Exception e) {
				SyncLogPrinter.log("[에러]" + e.getMessage());
				errorCount++;
				continue;
			}
			createMusicArticle(marticle);

			String msg = String.format("[신규] [%s] %s ", headerCode, marticle.getPath());
			newCount++;
			SyncLogPrinter.log(msg);
		}

		String msg = String.format("총:%,d : 변화없음:%,d : 신규등록:%,d : 경로수정:%,d : 에러:%,d ", musicMetes.size(), nothingCount,
				newCount, modifyCount, errorCount);
		SyncLogPrinter.log(msg);
		SyncLogPrinter.log(DateUtil.getSysDateTime() + " 디렉토리 기준 싱크 종료: " + baseDir.getAbsolutePath() + "\n\n");
	}

	/**
	 * 경로와 이름정보만 변경
	 * 
	 * @param sourceFile
	 * @param art
	 */
	private void updatePath(File sourceFile, MusicArticle art) {
		art.setPath(sourceFile.getAbsolutePath());
		art.setName(sourceFile.getName());
		updateMusicArticle(art);
	}

	// ------ 음악 경로 관리
	public MusicDirectory getMusicPath(String basePath) {
		return musicArticleDao.getMusicPath(basePath);
	}

	public List<MusicDirectory> getMusicPathList() {
		return musicArticleDao.getMusicPathList();
	}

	public void createMusicPath(MusicDirectory item) {
		musicArticleDao.createMusicPath(item);
	}

	public void updateMusicPath(MusicDirectory item) {
		musicArticleDao.updateMusicPath(item);
	}

	public void removeMusicPath(String basePath) {
		musicArticleDao.removeMusicPath(basePath);
	}

	// ------ 음악 목록 관리
	public MusicArticle getMusicArticle(String musicArticleId) {
		return musicArticleDao.getMusicArticle(musicArticleId);
	}

	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition) {
		return musicArticleDao.getMusicArticlePagingList(pageCondition);
	}

	public void createMusicArticle(MusicArticle item) {
		musicArticleDao.createMusicArticle(item);
	}

	public void updateMusicArticle(MusicArticle item) {
		musicArticleDao.updateMusicArticle(item);
	}

	public void removeMusicArticle(String musicArticleId) {
		musicArticleDao.removeMusicArticle(musicArticleId);
	}
}
