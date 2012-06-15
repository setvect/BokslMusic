package com.setvect.bokslmusic.service.music;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.extract.MusicExtractor;
import com.setvect.bokslmusic.extract.MusicMetadata;
import com.setvect.bokslmusic.log.SyncLogPrinter;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.common.date.DateUtil;
import com.setvect.common.log.LogPrinter;
import com.setvect.common.util.LapTimeChecker;

/**
 * 전체 싱크를 하기 위해서는 <br>
 * syncDirectory() -> syncDb() 순서로 함
 * 
 * @version $Id$
 */
@Service("MusicSyncService")
public class MusicSyncService {
	@Autowired
	private MusicService service;

	/**
	 * 음악 파일을 기준으로 저장된 DB와 싱크<br>
	 * DB 정보에는 수정/등록이 일어남.<br>
	 * 삭제 작업은 일어나지 않음
	 * 
	 * @param baseDir
	 *            싱크할 음악 파일이 있는 디렉토리
	 */
	public void syncDirectory(File baseDir) {
		LapTimeChecker ck = new LapTimeChecker("Sync Directory");
		SyncLogPrinter.log("=====" + DateUtil.getSysDateTime() + " 디렉토리 기준 싱크 시작: " + baseDir.getAbsolutePath());
		List<MusicMetadata> musicMetes = MusicExtractor.listForDir(baseDir);
		int modifyCount = 0;
		int newCount = 0;
		int errorCount = 0;
		int nothingCount = 0;
		int count = 0;
		Map<String, List<File>> overlapCheck = new HashMap<String, List<File>>();

		Logger.getLogger("org.jaudiotagger.tag").setLevel(Level.WARNING);

		ck.check("전체: " + musicMetes.size());
		for (MusicMetadata music : musicMetes) {
			count++;
			if (count % 10 == 0) {
				ck.check(count + "/" + musicMetes.size() + " 진행");
			}

			File sourceFile = music.getSourceFile();
			String headerCode = music.getHeaderCode();
			if (headerCode == null) {
				String msg = String.format("[에러][%,d/%,d] %s", count, musicMetes.size(), sourceFile.getAbsolutePath());
				SyncLogPrinter.log(msg);
				errorCount++;
				continue;
			}
			fileOverlapCheck(overlapCheck, sourceFile, headerCode);

			MusicArticle art = service.getMusicArticle(headerCode);
			if (art != null) {
				File dbPath = art.getFile();
				boolean noChange = dbPath.equals(sourceFile);

				if (noChange) {
					String msg = String.format("[통과][%,d/%,d] [%s] %s ", count, musicMetes.size(), headerCode,
							art.getFile());
					SyncLogPrinter.log(msg);
					nothingCount++;
					continue;
				}

				updatePath(sourceFile, art);
				String msg = String.format("[변경][%,d/%,d] [%s] %s -> %s", count, musicMetes.size(), headerCode,
						dbPath.getPath(), sourceFile.getAbsoluteFile());
				SyncLogPrinter.log(msg);
				modifyCount++;
				continue;
			}

			MusicArticle marticle = null;
			try {
				marticle = music.getMusicArticle();
				service.createMusicArticle(marticle);
			} catch (Exception e) {
				String msg = String.format("[에러][%,d/%,d] %s", count, musicMetes.size(), e.getMessage());
				SyncLogPrinter.log(msg);
				LogPrinter.out.warn(e);
				errorCount++;
				continue;
			}
			String msg = String.format("[신규][%,d/%,d] [%s] %s ", count, musicMetes.size(), headerCode,
					marticle.getPath());
			newCount++;
			SyncLogPrinter.log(msg);

		}
		ck.check(count + "/" + musicMetes.size() + " 완료");
		SyncLogPrinter.log("\n같은 파일이지만 서로 경로가 다른 파일(즉 중복된 파일) 목록");
		fileOverlapPrint(overlapCheck);

		String msg = String.format("총:%,d : 변화없음:%,d : 신규등록:%,d : 경로수정:%,d : 에러:%,d ", musicMetes.size(), nothingCount,
				newCount, modifyCount, errorCount);
		SyncLogPrinter.log(msg);
		SyncLogPrinter.log("-----" + DateUtil.getSysDateTime() + " 디렉토리 기준 싱크 종료: " + baseDir.getAbsolutePath()
				+ "\n\n");
		ck.check("종료 시간");
	}

	/**
	 * 음악 파일과 저장된 DB와 싱크<br>
	 * DB 정보에는 삭제 작업이 일어남<br>
	 * 
	 * @param baseDir
	 *            싱크할 음악 파일이 있는 디렉토리
	 */
	public void syncDb() {
		LapTimeChecker ck = new LapTimeChecker("Sync DB");
		SyncLogPrinter.log("=====" + DateUtil.getSysDateTime() + " DB 기준 싱크 시작: ");

		Collection<MusicArticle> listAll = service.getMusicArticleAllList();
		int deleteCount = 0;
		int nothingCount = 0;
		int count = 0;

		for (MusicArticle article : listAll) {
			count++;
			if (count % 10 == 0) {
				ck.check(count + "/" + listAll.size() + " 진행");
			}

			File musicFile = new File(article.getPath(), article.getName());
			boolean delete = false;
			if (!musicFile.exists()) {
				delete = true;
			}
			else {
				String headerCode = MusicMetadata.getHeaderMd5(musicFile);
				if (!article.getMusicId().equals(headerCode)) {
					delete = true;
				}
			}

			if (delete) {
				service.removePlayItem(article.getMusicId());
				service.removeMusicArticle(article.getMusicId());
				deleteCount++;

				String msg = String.format("[삭제][%,d/%,d] %s ", count, listAll.size(), musicFile.getAbsolutePath());
				SyncLogPrinter.log(msg);
			}
			else {
				nothingCount++;
				String msg = String.format("[통과][%,d/%,d] %s ", count, listAll.size(), musicFile.getAbsolutePath());
				SyncLogPrinter.log(msg);
			}
		}
		ck.check(count + "/" + listAll.size() + " 완료");

		String msg = String.format("총:%,d : 변화없음:%,d : 삭제:%,d ", listAll.size(), nothingCount, deleteCount);
		SyncLogPrinter.log(msg);
		SyncLogPrinter.log("-----" + DateUtil.getSysDateTime() + " DB 기준 싱크 종료\n\n");
		ck.check("종료 시간");
	}

	/**
	 * 중복된 파일 표시
	 * 
	 * @param overlapCheck
	 */
	private void fileOverlapPrint(Map<String, List<File>> overlapCheck) {
		Set<String> headerCodes = overlapCheck.keySet();
		int overLapCount = 0;
		for (String headerCode : headerCodes) {
			List<File> list = overlapCheck.get(headerCode);
			if (list.size() < 2) {
				continue;
			}
			SyncLogPrinter.log("동일한 파일 : " + headerCode);
			for (File f : list) {
				SyncLogPrinter.log(" - " + f.getAbsolutePath());
			}
			overLapCount++;
		}
		SyncLogPrinter.log("총: " + overLapCount + "건\n");
	}

	/**
	 * 음원 키(md5)가 같은 파일을 저장 <br>
	 * 향후 파일 경로는 다르지만 같은 파일(md5코드값이 같은)을 찾아냄
	 * 
	 * @param overlapCheck
	 * @param sourceFile
	 * @param headerCode
	 */
	private void fileOverlapCheck(Map<String, List<File>> overlapCheck, File sourceFile, String headerCode) {
		List<File> list = overlapCheck.get(headerCode);
		if (list == null) {
			list = new ArrayList<File>();
			overlapCheck.put(headerCode, list);
		}
		list.add(sourceFile);
	}

	/**
	 * 경로와 이름정보만 변경
	 * 
	 * @param sourceFile
	 * @param art
	 */
	private void updatePath(File sourceFile, MusicArticle art) {
		art.setPath(sourceFile.getParent());
		art.setName(sourceFile.getName());
		service.updateMusicArticle(art);
	}
}
