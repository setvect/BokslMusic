package com.setvect.bokslmusic.web.play;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.setvect.bokslmusic.player.GlobalPlayerInfo;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.vo.music.MusicArticle;

/**
 * 전체 음악 목록 조회 회면
 */
@Controller
public class MusicListController {

	/**
	 * 서브 명령어 정의
	 */
	public static enum Mode {
		ALL_LIST_FORM, PLAY_LIST_FORM, SETTING_FORM
	}

	/**
	 * 뷰에 전달할 객체를 가르키는 키
	 */
	public static enum AttributeKey {
		/** 음악 경로 */
		FOLDER,
		/** 재생 목록 */
		PLAY_LIST,
	}

	@Autowired
	private MusicService musicService;

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/play/music_list.do")
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		String m = request.getParameter("mode");
		Mode mode = Mode.valueOf(m);
		if (mode == Mode.ALL_LIST_FORM) {
			List<String> allPath = musicService.getMusicArticlePath();
			mav.addObject(AttributeKey.FOLDER.name(), allPath);
			mav.setViewName("play/all_list");
		}
		else if (mode == Mode.PLAY_LIST_FORM) {
			List<MusicArticle> playList = GlobalPlayerInfo.getPlayArticle();
			mav.addObject(AttributeKey.PLAY_LIST.name(), playList);
			mav.setViewName("play/play_list");
		}
		else if (mode == Mode.SETTING_FORM) {
			mav.setViewName("play/setting");
		}

		return mav;
	}
}