package com.setvect.bokslmusic.ui.client.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zschech.gwt.comet.client.CometListener;

/**
 * Comet 이벤트를 총괄 해서 받음
 */
public class BokslCometListener implements CometListener {
	private Map<Class<?>, CometMessageListener> eventList = new HashMap<Class<?>, CometMessageListener>();

	public void onConnected(int heartbeat) {
	}

	public void onDisconnected() {
	}

	public void onHeartbeat() {
	}

	public void onRefresh() {
	}

	public void onError(Throwable exception, boolean connected) {
	}

	public void onMessage(List<? extends Serializable> messages) {
		for (Serializable message : messages) {
			CometMessageListener processor = eventList.get(message.getClass());
			if (processor == null) {
				System.out.println("해당 메시지를 처리할 처리자가 없음: " + message);
				continue;
			}
			processor.processMessage(message);
		}
	}

	/**
	 * 받는 메시지 타입을 기준으로 처리자를 지정 함<br>
	 * 한 타입에 하나의 이벤트 처리자만 등록 할 수 있음
	 * 
	 * @param type
	 * @param listener
	 */
	public void addEventListener(Class<?> type, CometMessageListener listener) {
		eventList.put(type, listener);
	}

}
