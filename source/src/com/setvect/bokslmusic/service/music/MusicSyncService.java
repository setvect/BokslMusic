package com.setvect.bokslmusic.service.music;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * ��ü ��ũ�� �ϱ� ���ؼ��� <br>
 * syncDirectory() -> syncDb() ������ ��
 * 
 * @version $Id$
 */
@Service("MusicSyncService")
public class MusicSyncService {
	@Autowired
	private MusicService service;

	/**
	 * ���� ������ �������� ����� DB�� ��ũ<br>
	 * DB �������� ����/����� �Ͼ.<br>
	 * ���� �۾��� �Ͼ�� ����
	 * 
	 * @param baseDir
	 *            ��ũ�� ���� ������ �ִ� ���丮
	 */
	public void syncDirectory(File baseDir) {
		LapTimeChecker ck = new LapTimeChecker("Sync Directory");
		SyncLogPrinter.log("=====" + DateUtil.getSysDateTime() + " ���丮 ���� ��ũ ����: " + baseDir.getAbsolutePath());
		List<MusicMetadata> musicMetes = MusicExtractor.listForDir(baseDir);
		int modifyCount = 0;
		int newCount = 0;
		int errorCount = 0;
		int nothingCount = 0;
		Map<String, List<File>> overlapCheck = new HashMap<String, List<File>>();

		for (MusicMetadata music : musicMetes) {
			File sourceFile = music.getSourceFile();
			String headerCode = music.getHeaderCode();
			if (headerCode == null) {
				SyncLogPrinter.log("[����]" + sourceFile.getAbsolutePath());
				errorCount++;
				continue;
			}
			fileOverlapCheck(overlapCheck, sourceFile, headerCode);

			MusicArticle art = service.getMusicArticle(headerCode);
			if (art != null) {
				File dbPath = new File(art.getPath());
				boolean noChange = dbPath.equals(sourceFile);

				if (noChange) {
					String msg = String.format("[���] [%s] %s ", headerCode, art.getPath());
					SyncLogPrinter.log(msg);
					nothingCount++;
					continue;
				}

				updatePath(sourceFile, art);
				String msg = String.format("[����] [%s] %s -> %s", headerCode, dbPath.getPath(),
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
				LogPrinter.out.warn(e);
				errorCount++;
				continue;
			}
			service.createMusicArticle(marticle);

			String msg = String.format("[�ű�] [%s] %s ", headerCode, marticle.getPath());
			newCount++;
			SyncLogPrinter.log(msg);
		}
		SyncLogPrinter.log("\n���� ���������� ���� ��ΰ� �ٸ� ����(�� �ߺ��� ����) ���");
		fileOverlapPrint(overlapCheck);

		String msg = String.format("��:%,d : ��ȭ����:%,d : �űԵ��:%,d : ��μ���:%,d : ����:%,d ", musicMetes.size(), nothingCount,
				newCount, modifyCount, errorCount);
		SyncLogPrinter.log(msg);
		SyncLogPrinter.log("-----" + DateUtil.getSysDateTime() + " ���丮 ���� ��ũ ����: " + baseDir.getAbsolutePath()
				+ "\n\n");
		ck.check("���� �ð�");
	}

	/**
	 * ���� ���ϰ� ����� DB�� ��ũ<br>
	 * DB �������� ���� �۾��� �Ͼ<br>
	 * 
	 * @param baseDir
	 *            ��ũ�� ���� ������ �ִ� ���丮
	 */
	public void syncDb() {
		LapTimeChecker ck = new LapTimeChecker("Sync DB");
		SyncLogPrinter.log("=====" + DateUtil.getSysDateTime() + " DB ���� ��ũ ����: ");

		Collection<MusicArticle> listAll = service.getMusicArticleAllList();
		int deleteCount = 0;
		int nothingCount = 0;

		for (MusicArticle article : listAll) {
			File musicFile = new File(article.getPath());
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
				service.removeMusicArticle(article.getMusicId());
				deleteCount++;
				SyncLogPrinter.log("[����]" + musicFile.getAbsolutePath());
			}
			else {
				nothingCount++;
				SyncLogPrinter.log("[���]" + musicFile.getAbsolutePath());
			}
		}
		String msg = String.format("��:%,d : ��ȭ����:%,d : ����:%,d ", listAll.size(), nothingCount, deleteCount);
		SyncLogPrinter.log(msg);
		SyncLogPrinter.log("-----" + DateUtil.getSysDateTime() + " DB ���� ��ũ ����\n\n");
		ck.check("���� �ð�");
	}

	/**
	 * �ߺ��� ���� ǥ��
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
			SyncLogPrinter.log("������ ���� : " + headerCode);
			for (File f : list) {
				SyncLogPrinter.log(" - " + f.getAbsolutePath());
			}
			overLapCount++;
		}
		SyncLogPrinter.log("��: " + overLapCount + "��\n");
	}

	/**
	 * ���� Ű(md5)�� ���� ������ ���� <br>
	 * ���� ���� ��δ� �ٸ����� ���� ����(md5�ڵ尪�� ����)�� ã�Ƴ�
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
	 * ��ο� �̸������� ����
	 * 
	 * @param sourceFile
	 * @param art
	 */
	private void updatePath(File sourceFile, MusicArticle art) {
		art.setPath(sourceFile.getAbsolutePath());
		art.setName(sourceFile.getName());
		service.updateMusicArticle(art);
	}
}
