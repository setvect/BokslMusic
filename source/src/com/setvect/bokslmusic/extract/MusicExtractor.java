package com.setvect.bokslmusic.extract;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.setvect.common.util.FileUtil;

public class MusicExtractor {

	private static final String[] EXT = new String[] { ".mp3", ".ogg", ".wma" };

	/**
	 * @param baseDir
	 * @return
	 */
	public static List<MusicMetadata> listForDir(final File baseDir) {
		FileFinder finder = new FileFinder(baseDir, EXT);
		List<File> musicFiles = finder.getFiles();
		List<MusicMetadata> musicMeta = new ArrayList<MusicMetadata>();
		for (File f : musicFiles) {
			musicMeta.add(new MusicMetadata(f));
		}
		return musicMeta;
	}

	/**
	 * ���� ������ Ž���Ͽ� Ư�� Ȯ���ڸ� ���� ���� ����� �˻� <br>
	 * TODO ���� ���̺귯���� �̵�
	 * 
	 * @version $Id$
	 */
	private static class FileFinder {
		private List<File> files = new ArrayList<File>();
		private File baseDir;
		private Set<String> includeExt;

		/**
		 * @param baseDir
		 *            �˻� ���� ���丮
		 * @param includeExt
		 *            �˻� ��� Ȯ����. Ȯ���ڴ� .(��)�� ���� <br>
		 *            ex) .hwp, .mp3, ...
		 */
		FileFinder(File baseDir, Set<String> includeExt) {
			this.baseDir = baseDir;
			this.includeExt = includeExt;
			setFile(this.baseDir);
		}

		/**
		 * @param baseDir
		 *            �˻� ���� ���丮
		 * @param ext
		 *            �˻� ��� Ȯ����. Ȯ���ڴ� .(��)�� ���� <br>
		 *            ex) .hwp, .mp3, ...
		 */
		FileFinder(File baseDir, String[] ext) {
			Set<String> extSet = new HashSet<String>();
			for (String e : ext) {
				extSet.add(e);
			}
			this.baseDir = baseDir;
			this.includeExt = extSet;
			setFile(this.baseDir);

		}

		/**
		 * @return ���� ���
		 */
		public List<File> getFiles() {
			return Collections.unmodifiableList(files);
		}

		/**
		 * @return �ش� ���ǿ� �´� ����
		 */
		public List<File> list() {
			return files;
		}

		private void setFile(File dir) {
			File[] fileList = dir.listFiles();
			for (File f : fileList) {
				if (f.isDirectory() == true) {
					this.setFile(f);
				}
				String ext = FileUtil.getExt(f.getName());
				if (includeExt.contains(ext)) {
					files.add(f);
				}
			}
		}
	}
}
