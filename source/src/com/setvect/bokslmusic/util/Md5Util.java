package com.setvect.bokslmusic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
	private int arraySize;
	private File file;
	long start, end;

	public Md5Util(File path, int arraySize) {
		this.arraySize = arraySize;
		this.file = path;
	}

	public Md5Util(File path) {
		this.arraySize = (int) path.length();
		this.file = path;
	}

	public String getCheckSum() {
		long start = System.currentTimeMillis();
		MessageDigest digest = null;

		try {
			digest = MessageDigest.getInstance("MD5");
			long file_size = file.length();
			FileInputStream in;

			in = new FileInputStream(file);

			int cnt = 0;
			byte arr[];

			while (true) {
				long temp = (file_size - cnt);

				if (temp < arraySize) { // 맨 마지막 버퍼
					arr = new byte[(int) temp];
					int i = in.read(arr);
					digest.update(arr);
					cnt += temp;
					break;
				}
				else {
					arr = new byte[arraySize];
				}

				int i = in.read(arr);
				digest.update(arr);
				cnt += arraySize;
			}
			end = System.currentTimeMillis();
			System.out.println("걸린시간 : " + (end - start) / 1000.0);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashByte2MD5(digest.digest());
	}

	private static String hashByte2MD5(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			}
			else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}
		return hexString.toString();
	}

}
