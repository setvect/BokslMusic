package com.setvect.bokslmusic.web.play;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 전체 음악 목록 조회 회면
 */
@Controller
public class MusicListController {

	/**
	 * 서브 명령어 정의
	 */
	public static enum Mode {
		ALL_LIST_FORM, PLAY_LIST_FORM, ALBUM_FORM, SETTING_FORM
	}

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
			mav.setViewName("play/all_list");
		}
		else if (mode == Mode.PLAY_LIST_FORM) {
			mav.setViewName("play/play_list");
		}
		else if (mode == Mode.ALBUM_FORM) {
			mav.setViewName("play/album");
		}
		else if (mode == Mode.SETTING_FORM) {
			mav.setViewName("play/setting");
		}

		return mav;
	}
}