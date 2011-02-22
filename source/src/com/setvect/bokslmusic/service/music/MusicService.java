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
	 * ���� ���ϰ� ����� DB�� ��ũ<br>
	 * DB �������� ����/����� �Ͼ.<br>
	 * ���� �۾��� �Ͼ�� ����
	 * 
	 * @param baseDir
	 *            ��ũ�� ���� ������ �ִ� ���丮
	 */
	public void syncDiretory(File baseDir) {
		SyncLogPrinter.log(DateUtil.getSysDateTime() + " ���丮 ���� ��ũ ����: " + baseDir.getAbsolutePath());
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
					String msg = String.format("[���] [%s] %s ", headerCode, art.getPath());
					SyncLogPrinter.log(msg);
					nothingCount++;
					continue;
				}

				updatePath(sourceFile, art);
				String msg = String.format("[����] [%s] %s -> %s", headerCode, art.getPath(),
						sourceFile.getAbsoluteFile());
				SyncLogPrinter.log(msg);
				modifyCount++;
				continue;
			}

			MusicArticle marticle = null;
			try {
				marticle = music.getMusicArticle();
			} catch (Exception e) {
				SyncLogPrinter.log("[����]" + e.getMessage());
				errorCount++;
				continue;
			}
			createMusicArticle(marticle);

			String msg = String.format("[�ű�] [%s] %s ", headerCode, marticle.getPath());
			newCount++;
			SyncLogPrinter.log(msg);
		}

		String msg = String.format("��:%,d : ��ȭ����:%,d : �űԵ��:%,d : ��μ���:%,d : ����:%,d ", musicMetes.size(), nothingCount,
				newCount, modifyCount, errorCount);
		SyncLogPrinter.log(msg);
		SyncLogPrinter.log(DateUtil.getSysDateTime() + " ���丮 ���� ��ũ ����: " + baseDir.getAbsolutePath() + "\n\n");
	}

	/**
	 * ��ο� �̸������� ����
	 * 
	 * @param sourceFile
	 * @param art
	 */
	private void updatePath(File sourceFile, MusicArticle art) {
		art.setPath(sourceFile.getAbsolutePath());
		art.setName(sourceFile.getName());
		updateMusicArticle(art);
	}

	// ------ ���� ��� ����
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

	// ------ ���� ��� ����
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
