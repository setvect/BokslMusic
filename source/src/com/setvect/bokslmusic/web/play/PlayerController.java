package com.setvect.bokslmusic.web.play;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.setvect.bokslmusic.web.ConstraintWeb;
import com.setvect.common.util.StringUtilAd;

/**
 * 검색 페이지
 */
@Controller
public class PlayerController {

	/**
	 * 서브 명령어 정의
	 */
	public static enum Mode {
		LIST_FORM
	}

	/**
	 * 뷰에 전달할 객체를 가르키는 키
	 */
	public static enum AttributeKey {
		/** 리스트 */
		LIST,
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/search/main.do")
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String mode = request.getParameter("mode");

		Mode m;
		if (StringUtilAd.isEmpty(mode)) {
			m = Mode.LIST_FORM;
		}
		else {
			m = Mode.valueOf(mode);
		}

		if (m == Mode.LIST_FORM) {
			mav.setViewName(ConstraintWeb.SEARCH_LAYOUT);
			mav.addObject(ConstraintWeb.AttributeKey.INCLUDE_PAGE.name(), "/play/music_list.jsp");
		}

		return mav;
	}
}