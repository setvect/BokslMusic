package com.setvect.bokslmusic.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;

import com.setvect.bokslmusic.boot.EnvirmentInit;
import com.setvect.bokslmusic.config.BokslMusicConstant;
import com.setvect.bokslmusic.player.AudioPlayer.PlayerStatus;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.PlayItem;
import com.setvect.common.log.LogPrinter;

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

	static {
		AudioPlayer.playerListener.setStateListener(new StateEventListener() {
			public void event(BasicPlayerEvent event) {
				LogPrinter.out.debug(event);
				if (event.getCode() == BasicPlayerEvent.STOPPED && AudioPlayer.getProgressRate() == 1.0) {
					playerStat.setPlayArticle(null);
					GlobalPlayerInfo.next();
				}
			}
		});
	}

	/**
	 * 재상 항목 추가
	 * 
	 * @param items
	 *            재생 항목
	 */
	public static void addPlayArticle(List<MusicArticle> items) {
		playList.addAll(items);
		savePlayList();
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
		savePlayList();
	}

	/**
	 * 재생 목록 추가
	 * 
	 * @param article
	 *            재생 목록 추가
	 */
	public static void addPlayArticle(MusicArticle article) {
		playList.add(article);
		savePlayList();
	}

	/**
	 * 재생 전체 항목 제거
	 */
	public static void clearPlayList() {
		playList.clear();
		savePlayList();
	}

	/**
	 * 재생 목록을 DB저장함. 향후 WAS가 종료되어도 마지막 재생 목록을 기억하기 위함
	 */
	private static void savePlayList() {
		MusicService musicService = (MusicService) EnvirmentInit.getConfigSpring().getBean("MusicService");
		musicService.removePlayItemForAlbumSeq(BokslMusicConstant.ALBUM_TEMP);
		int idx = 0;

		for (MusicArticle music : playList) {
			PlayItem item = new PlayItem();
			item.setAlbumSeq(BokslMusicConstant.ALBUM_TEMP);
			item.setMusicId(music.getMusicId());
			item.setOrderNo(idx);
			musicService.createPlayItem(item);
			idx++;
		}
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
	 * 현재 재생 목록에 해당 하는 음악 아이디가 없으면 작업 요청을 무시한다.
	 * 
	 * @param musicId
	 *            음악 아이디
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public static int play(String musicId) {
		for (int i = 0; i < playList.size(); i++) {
			MusicArticle a = playList.get(i);
			if (a.getMusicId().equals(musicId)) {
				playerStat.setPlayIndex(i);
				break;
			}
		}
		return play();
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
		if (AudioPlayer.getPlayerStatus() != BasicPlayer.STOPPED) {
			stop();
		}

		int playIndex = 0;
		if (playerStat.getPlayIndex() + 1 == playList.size()) {
			if (playerStat.isRepeat()) {
				playIndex = 0;
			}
			else {
				return -1;
			}
		}
		else {
			playIndex = playerStat.getPlayIndex() + 1;
		}
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
	 * 볼륨 조정
	 * 
	 * @param volume
	 *            볼륨 : 범위 100~0
	 */
	public static void setVolume(int volume) {
		AudioPlayer.setVolume((double) volume / 100);
	}

	/**
	 * 컨트롤러와 관련된 상태 정보
	 * 
	 * @return 상태 정보
	 */
	public static PlayerStat getPlayerStat() {
		playerStat.setPlayStatus(AudioPlayer.getStatus());
		int volume = (int) (AudioPlayer.getVolume() * 100);
		playerStat.setVolume(volume);
		playerStat.setProgressRate(AudioPlayer.getProgressRate());
		return playerStat;
	}

	/**
	 * 재생 Progress 이동
	 * 
	 * @param seek
	 *            이동 값 0~1 사이
	 */
	public static void setProgressRate(double seek) {
		AudioPlayer.seek(seek);
	}
}
