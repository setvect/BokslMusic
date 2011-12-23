package com.setvect.bokslmusic.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.setvect.bokslmusic.player.AudioPlayer.PlayerStatus;
import com.setvect.bokslmusic.vo.music.MusicArticle;

/**
 * 전역적인 재생 정보<br>
 * 단일 사용자를 대상으로 하기 때문에 모든 속성 값은 static이다.<br>
 * ※ 동기화쪽 문제가 되는 부분이 있지만,여기서 고려하지 않는다.
 * 
 * @version $Id$
 */
public class GlobalPlayerInfo {
	/** 재생 리스트 */
	private static List<MusicArticle> playList = new ArrayList<MusicArticle>();

	/** 재생 정보 */
	private static PlayerStat playerStat = new PlayerStat();

	/**
	 * 재상 항목 추가
	 * 
	 * @param items
	 *            재생 항목
	 */
	public static void addPlayArticle(List<MusicArticle> items) {
		playList.addAll(items);
	}

	/**
	 * 재생 목록 조회
	 * 
	 * @return 재생 목록
	 */
	public static List<MusicArticle> getPlayArticle() {
		return Collections.unmodifiableList(playList);
	}

	/**
	 * 재생 항목 삭제
	 * 
	 * @param idx
	 *            삭재 할 목록 인덱스
	 */
	public static void removePlayList(int idx) {
		playList.remove(idx);
	}

	/**
	 * 재생 목록 추가
	 * 
	 * @param article
	 *            재생 목록 추가
	 */
	public static void addPlayArticle(MusicArticle article) {
		playList.add(article);
	}

	/**
	 * 재생 전체 항목 제거
	 */
	public static void clearPlayList() {
		playList.clear();
	}

	/**
	 * 중복 제거
	 */
	public static void deduplication() {
		List<MusicArticle> temp = new ArrayList<MusicArticle>();
		for (MusicArticle a : playList) {
			if (temp.contains(a)) {
				continue;
			}
			temp.add(a);
		}
		playList = temp;
	}

	/**
	 * 재생 목록 섞이
	 */
	public static void shuffle() {
		Collections.shuffle(playList);
	}

	/**
	 * 현재 커서의 음악 재생
	 * 
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public static int play() {
		if (playerStat.getPlayIndex() >= playList.size()) {
			return -1;
		}

		if (AudioPlayer.getStatus() == PlayerStatus.PAUSE) {
			AudioPlayer.resume();
		}
		else {
			MusicArticle playMusic = playList.get(playerStat.getPlayIndex());
			AudioPlayer.open(playMusic.getFile());
			AudioPlayer.play();

			playerStat.setPlayArticle(playMusic);
		}
		return playerStat.getPlayIndex();
	}

	/**
	 * 일시멈춤
	 */
	public static void pause() {
		if (AudioPlayer.getStatus() == PlayerStatus.PLAY) {
			AudioPlayer.pause();
		}
	}

	/**
	 * 멈춤
	 */
	public static void stop() {
		AudioPlayer.stop();
	}

	/**
	 * 이전 곡 재생<br>
	 * 
	 * 
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public static int previous() {
		stop();
		int playIndex = playerStat.getPlayIndex() == 0 ? playerStat.getPlayIndex() : playerStat.getPlayIndex() - 1;
		playerStat.setPlayIndex(playIndex);
		return play();
	}

	/**
	 * 다음 곡 재생
	 * 
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public static int next() {
		stop();
		int playIndex = playerStat.getPlayIndex() + 1 == playList.size() ? playerStat.getPlayIndex() : playerStat
				.getPlayIndex() + 1;
		playerStat.setPlayIndex(playIndex);
		return play();
	}

	/**
	 * 반복 재생 여부
	 * 
	 * @param repeat
	 *            true면 반복 재생
	 */
	public static void repeat(boolean repeat) {
		playerStat.setRepeat(repeat);
	}

	/**
	 * 컨트롤러와 관련된 상태 정보
	 * 
	 * @return 상태 정보
	 */
	public static PlayerStat getPlayerStat() {
		playerStat.setPlayStatus(AudioPlayer.getStatus().name());
		return playerStat;
	}
}
