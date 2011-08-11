package com.setvect.bokslmusic.ui.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 응용 어플 제어 관련
 * 
 * @see ControlService
 */
@RemoteServiceRelativePath("service/ControlService")
public interface ControlService extends RemoteService {

	/**
	 * 서버에게 신호를 보냄고 응답을 받음 <br>
	 * 목적은 comet 스레드 신호를 위해 만든 것임
	 * 
	 * @param message
	 *            문자열 보냄
	 * @return 보낸 문자열 그대로
	 */
	public String echo(String message);

}
